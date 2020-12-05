package transitapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * a class that stores route
 * addor delete route from the system
 * cofig route by adding, deleting or adding points
 * 
 */
public class RouteManager extends FileManager{

	
	private static RouteManager routeManager = null;

	HashMap<String,Route> routes = new HashMap<String,Route>();
	// route1  	--- s1 , s2...
	// route2  	-- s4, s5...
	// route3 	--- s6, s9...
	
	private RouteManager() {
		HashMap<String, Route> r = new HashMap<String, Route>();
		this.routes = r;
	}
	
	
	public static RouteManager getInstance() {
		if(routeManager == null) {
			routeManager = new RouteManager();
			
		}
		return routeManager;
	}
	
	public Route getRoute(String routeName) {
		
		
		return this.routes.get(routeName);

	}

	
	/**
	 * add a route into the system
	 * @param route a route that want to be added
	 * @return true if the route is successfully added into the system; false otherwise
	 * @throws IOException
	 */
	//route1
	// add------ 
	// route1, route2
	public boolean addRoute(Route route) throws IOException {
		for(String newRouteName: routes.keySet()) {
			if(newRouteName.equals(route.getName())) {
				return false;
			}
		}
		
		this.routes.put(route.getName(),route);
		this.write();
		return true;
		
		
	}
	
	/**
	 * write the routes into a file
	 * @throws IOException
	 */
	public void write() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("src/newRoute.txt"));
		for(Route route: this.routes.values()) {
			out.append(route.toString());	
		}
		out.close();

		
	}
//	
//	/**
//	 * add a point to a route
//	 * @param name a String that represent the name of the route that want to add points
//	 * @param point a Point that want to added to a route
//	 * @return true if the point is successfully added to the route; false otherwise
//	 * @throws IOException
//	 */
//	public boolean addPoint(Route route, Point station) throws IOException{
//		for(String key: this.routes.keySet()) {
//			if(this.routes.get(key).getName() == route.getName() && 
//					this.routes.get(key).getRouteType().equals("TRAIN")
//					 && route.getRouteType().equals("TRAIN")) {
//				for(Point routeStation: this.routes.get(key).getStations()) {
//					if(routeStation.getName().equals(station.getName())) {
//						return false;
//				}
//				this.routes.get(key).getStations().add(station);
//				this.write();
//				return true;
//				}
//			}
//		}
//		return false;
//	}
	
	
//	/**
//	 * add a new route into the system
//	 * @param name a String that represent the name of the route
//	 * @param type a String that represent the type of the route
//	 * @param points a ArrayList of Points that represent the points that the route have
//	 * @return true if the route is successfully added into the system;
//	 * 		   false if the route is already in the system
//	 * @throws IOException
//	 */
//	public boolean addRoute(String name,String type, ArrayList<Point> stations) throws IOException {
//		for (String key: this.routes.keySet()) {
//			if(name.equals(key)) {
//				return false;
//			}
//		}
//		Route route = new Route(name,type);
//		if(route.getRouteType() == "TRAIN") {
//			route.setStations(stations);
//			this.routes.put(name, route);
//			this.write();
//			return true;
//		}
//		return false;
//	}
	

	
	/**
	 * delete a route from the system
	 * @param name a Strong that represent the name of the route
	 * @return true if the route is successfully deleted from the system;
			   false if the route is not in the system
	 * @throws IOException
	 */
	public boolean deleteRoute(String name) throws IOException  {
		for(String key: this.routes.keySet()) {
			if(key.equals(name)) {
				this.routes.remove(name);
				this.write();
				return true;
			}
		}
		return false;
		
		

	}
	
	public boolean addRoute(String name, String type) throws IOException {
		if(this.routes.keySet().contains(name)) {
			return false;
		}
		Route newRoute = new Route(name,type);
		this.routes.put(name,newRoute);
		this.write();
		return true;
	}
	
	// route1
	
	
	//Route 1 -- stop1 stop2 stop3
	//Route 2 -- station1  station2 station3
	

	
	public void addPointToRoute(Point station, String name) throws IOException {
		this.routes.get(name).addPoint(station);
		this.write();
	}
	public void addPointToRoute(String station, String name) throws IOException {
		String type = this.routes.get(name).getRouteType();
		if (type.equals("BUS")){
			Stop newPoint = new Stop(station);
			this.routes.get(name).addPoint(newPoint);
		}else {
			int id= this.routes.get(name).getRoute().size()+1;
			Station newPoint = new Station(station,id);
			this.routes.get(name).addPoint(newPoint);
		}
		this.write();
		
	}

//	public Route getTrainRouteFromStationName(String stationName) {
//		for(String key: this.routes.keySet()) {
//			if(this.routes.get(key).getRouteType() == "TRAIN") {
//				for(Station station: this.routes.get(key).getStations()) {
//					if(station.getName() == stationName) {
//						return this.routes.get(key);
//					}
//				}
//			}
//		}
//		return null;
//	}
//	
//	public Route getBusRouteFromStopName(String stopName) {
//		for(String key: this.routes.keySet()) {
//			if(this.routes.get(key).getRouteType() == "BUS") {
//				for(Stop stop: this.routes.get(key).getStops()) {
//					if(stop.getName() == stopName) {
//						return this.routes.get(key);
//					}
//				}
//			}
//		}
//		return null;
//	}
	
	public HashMap<String,Route> getRoutes(){
		return this.routes;
	}

	/**
	 * update every Route into the system from a file
	 * by extracting name, type, and points of route from the file
	 * then add the Route into the system by those elements
	 * @param a file of String that contains all the Route
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void initialize(String file) throws FileNotFoundException, IOException {
		//each line represents a route
		//each category is separated by comma
		//first is name, second is type, followed by point names
		//e.g.("Route1,TRAIN,station1,station2")
		Route currentRoute;
		String name,line,type;
		String[] listOfStrings;
		BufferedReader in = new BufferedReader(new FileReader("src/" + file));
		Scanner scanner = new Scanner(in);
		int count;
		while(scanner.hasNextLine()) {
			line = scanner.nextLine();
			if(!line.equals("")) {
				listOfStrings = line.split(",");
				name = listOfStrings[0].replace("Name: ", "");
				type = listOfStrings[1].replace("Type: ", "");
				currentRoute = new Route(name,type);
				count = 1;
				for (int i =2; i<listOfStrings.length;i++){
					if(type.equals( "BUS")) {
						Stop stop = new Stop(listOfStrings[i]);
						currentRoute.route.put(listOfStrings[i], stop);
					}else if (type.equals("TRAIN")) {
						Station station = new Station(listOfStrings[i],count);//need to modify
						currentRoute.route.put(listOfStrings[i], station);
					}else {
						throw new RuntimeException("Route Type does not exist:" + type);
					}
					count +=1;
				}
				this.routes.put(name, currentRoute);
			}
		}
	}

	
}
//if(type.equals( "BUS")) {
//	Stop stop = new Stop(listOfStrings[i]);
//	currentRoute.addStop(stop);
//}else if (type.equals("TRAIN")) {
//	Station station = new Station(listOfStrings[i],count);//need to modify
//	currentRoute.addStation(station);
//}else {
//	throw new RuntimeException("Route Type does not exist:" + type);
//}
