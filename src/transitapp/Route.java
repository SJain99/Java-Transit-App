package transitapp;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

/**
 * This class is creating a whole route which contains a lot of stops or stations.
 * The route has its name and route's type(such as stop and station).
 * Every stop and station also has a name.
 * @author 
 *
 */
public class Route {
    HashMap<String, Point> route;
    private String name;
    private String routeType;
    

    //route1 (stop): stop1 stop2 stop3
    
    /**
     * Construct a route with information of name and routeType.
     * And creating a list for storing all points.
     * @param name
     * @param routeType
     */
    public Route(String name, String routeType){
    	this.name = name;
    	this.routeType = routeType;
    	this.route = new HashMap<String, Point>();
    }
    
    /**
     * Sets name for route.
     * @param name String representing name of route.
     */
    public void setName(String name) {
		this.name = name;
	}
	
    /**
     * Return name of the route.
     * @return name of the route.
     */
	public String getName() {
		return this.name;
	}

	
//    public void setStations (ArrayList<Point> stations) {
//    	this.route.put(this.name, stations);
//    }
    
    /**
     * Adding a point object into points list.
     * @param point a object representing a stop or a station.
     */
    public void addPoint(Point point) {
    	String name = point.getName();
    	if(this.route.get(name) == null) {
    		this.route.put(name, point);
    	}
    }
    

//    public boolean containsStation(String stationName) {
//    	for(Point station: this.route.get(this.name)) {
//    		if(station.getName().equals(stationName)) {
//    			return true;
//    		}
//    	}
//    	return false;
//    }
    
    
//    /**
//     * Return the points list.
//     * @return the points list.
//     */
////    public ArrayList<Point> getStations (){
////    	return this.route.get(this.name);
////    }
    
    public HashMap<String, Point> getRoute(){
    	return this.route;
    }
    
    public Point getPoint(String name) {
    	return this.route.get(name);
    }

    
    /**
     * Return the type of the route, such as stop and station.
     * @return stop if the route is for bus, station if the route is for subway.
     */
    public String getRouteType() {
    	return this.routeType;
    }
    
    public String toString() {
    	String base = "Name: " + this.name + ",Type: " + this.routeType;
    	for(HashMap.Entry<String, Point> entry : this.route.entrySet()) {
    		base += ","+ entry.getKey();
    	}
    	base += "\n";
    	return base;
    }
}