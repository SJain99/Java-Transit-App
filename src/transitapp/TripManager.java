package transitapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * a class that manage the Trips
 * add trips from the file
 * coute the fares or stops
 * get the trips from the system
 * 
 */
public class TripManager {

	private static TripManager tripManager = null;
	private static HashMap<String, ArrayList<Trip>> userTrips;
	
	private TripManager() {	}
	
	public static TripManager getInstance() {
		if(tripManager == null) {
			tripManager = new TripManager();
			userTrips = new HashMap<String, ArrayList<Trip>>();
		}
		return tripManager;
	}
	
	public void fillUserTrips() {
		CardManager1 cm = CardManager1.getInstance();
		HashMap<String, ArrayList<Trip>> userTrips = new HashMap<String, ArrayList<Trip>>();
		for(Card card: cm.allCards()) {
			User user = card.getUser();
			ArrayList<Trip> trips = new ArrayList<Trip>();
			for(Trip trip: card.getTrips()) {
				trips.add(trip);
			}
			userTrips.put(user.getEmail(), trips);
		}
		TripManager.userTrips = userTrips;
	}
	
	/**
	 * gets the last 3 trips from a user
	 * @param user a User that want to be find the trips
	 * @return a ArrayList of trips that the user had last 3 times
	 */
	public ArrayList<String> lastThreeTrips(User user) {
		ArrayList<String> trips = new ArrayList<String>();
		if(TripManager.userTrips.get(user.getEmail()).size() == 0) {
			return null;
		} else if(TripManager.userTrips.get(user.getEmail()).size() == 1) {
			trips.add(TripManager.userTrips.get(user.getEmail()).get(0).toString());
			return trips;
		} else if(TripManager.userTrips.get(user.getEmail()).size() == 2) {
			trips.add(TripManager.userTrips.get(user.getEmail()).get(1).toString());
			trips.add(TripManager.userTrips.get(user.getEmail()).get(0).toString());
			return trips;
		} else {
			trips.add(TripManager.userTrips.get(user.getEmail()).get(TripManager.userTrips.get(user.getEmail())
					.size() - 1).toString());
			trips.add(TripManager.userTrips.get(user.getEmail()).get(TripManager.userTrips.get(user.getEmail())
					.size() - 2).toString());
			trips.add(TripManager.userTrips.get(user.getEmail()).get(TripManager.userTrips.get(user.getEmail())
					.size() - 3).toString());
			return trips;
		}
	}
	
	/**
	 * count how much stops a trip has
	 * @return a int that represent how much stops the trip has
	 */
	public int countStops() {
		int stopCount = 0;
		for(String key: TripManager.userTrips.keySet()) {
			for(Trip trip: TripManager.userTrips.get(key)) {
				stopCount += trip.getHops().size() - 1;
			}
		}
		return stopCount;
	}
	
	/**
	 * count how much the trip cost in a day
	 * @return a int represent the daily fare
	 */
	public double countDailyFares() {
		double fares = 0;
		for(String key: TripManager.userTrips.keySet()) {
			for(Trip trip: TripManager.userTrips.get(key)) {
				fares += trip.getCost();
			}
		}
		return fares;
	}
	
	/**
	 * count hom much the user cost in total of a user
	 * @param user a User that want to count the fare
	 * @return a double represent the total user fare
	 */
	public double countTotalUserFares(User user) {
		double fares = 0;
		for(Trip trip: TripManager.userTrips.get(user.getEmail())) {
			if(trip.getCard().getUser() == user) {
				fares += trip.getCost();
			}
		}
		return fares;
	}
	
	/**
	 * count the average cost in a month of a user
	 * @param user a User that want to count the fare
	 * @return a double represent the monthly average cost
	 */
	public double monthlyAverageCost(User user) {
		double userFares = this.countTotalUserFares(user);
		int numMonths = 0;
		for(int key: user.getMonthsWithTrips().keySet()) {
			numMonths += user.getMonthsWithTrips().get(key);
		}
		return userFares / numMonths;
	}
	
	/**
	 * get Trip from the id of the trip
	 * @param ID a String that represent the ID of the trip
	 * @return Trip that has the ID
	 */
	public Trip getTrip(String ID) {
		for(String key: TripManager.userTrips.keySet()) {
			for(Trip trip: TripManager.userTrips.get(key)) {
				if(trip.getId() == ID) {
					return trip;
				}
			}
		}
		return null;
	}
	
	public HashMap<String, ArrayList<Trip>> getTrips() {
		return TripManager.userTrips;
	}
	
	/**
	 * add Trip into the system
	 * @param trip a Trip that want to be added
	 * @return true if the trip is successfully added into the system;
	 *  	   false if the trip is already in the system
	 * @throws IOException
	 */
	public boolean addTrip(Trip trip) throws IOException {
		Trip isTrip = this.getTrip(trip.getId());
		if(isTrip != null) {
			return false;
		}
		String userEmail = trip.getCard().getUser().getEmail();
		if(TripManager.userTrips.containsKey(userEmail)) {
			TripManager.userTrips.get(userEmail).add(trip);
		} else {
			ArrayList<Trip> trips = new ArrayList<Trip>();
			trips.add(trip);
			TripManager.userTrips.put(userEmail, trips);
		}
		return true;
	}
}