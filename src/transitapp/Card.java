package transitapp;

import java.io.IOException;
import java.util.ArrayList;

/**
 * It is a Card class. It sets up all cards for owners.
 * Card has balance starting at 19 dollars.
 * Every card has its owner.
 * 
 * @author 
 *
 */
public class Card {
	
	public static double DEFAULT_BALANCE = 19.0; // to set/get later;
	User user;
	private String ID;
	private double balance;
	private Boolean isSuspended = false;
	private ArrayList<Trip> trips = new ArrayList<Trip>();

	
	
	/**
	 * Construct a new card by given information of ID.
	 * Set the beginning balance at 19 dollars.
	 * @param ID string representing the ID of specify card.
	 */
	public Card(String ID) {
		this.user = null;
		this.ID = ID;
		this.balance = DEFAULT_BALANCE;
	}
	
	
	/**
	 * Return last trip that person travel
	 * @return last trip if the person does travel, null if the person does not.
	 */
	public Trip getLastTrip() {
		if(this.trips.size() == 0) return null;
		return this.trips.get(this.trips.size()-1);

			// we have to account when there is not last trip , maybe not here
			// we can't get null Trip unless someone force it, maybe we should remove this condition
	}
	
	
	/**
	 * Return the total cost in one this trip, given a trip and the fee of point.
	 * @param trip a trip object representing the area that person travels.
	 * @param fee double representing the fee of the point.
	 * @return the total cost in one this trip, maximum should be 6.
	 */
	public double deduct(Trip trip, double fee) throws IOException {
		double charged = fee;
		if(fee + trip.getCost() > Trip.CostCap) {
			charged = Trip.CostCap - trip.getCost();
		}
		this.balance -= charged;
		CardManager1 cm = CardManager1.getInstance();
		cm.write();
		trip.setCost(trip.getCost() + charged);
		if(this.balance <= 0) {
			this.setIsSuspended(true);
			cm.write();
		}
		return charged;
	}
	
	/**
	 * Return the user who is using this app.
	 * @return the user.
	 */
	public User getUser() {// if no user let us return null, and deal with it in the code
		return this.user;
	}
	
	/**
	 * Sets new user.
	 * @param newuser a user object
	 */
	public void setUser(User newuser) {
		this.user = newuser;
	}
	
	/**
	 * Return ID of the card.
	 * @return ID of the card.
	 */
	public String getID() {
		// corrected this , Anyway the ID should be in constructor so we always have an ID
		return this.ID;
	}
	
	/**
	 * Sets ID of the card.
	 * @param ID String representing ID of the card.
	 */
	public void setID(String ID) { // I think we should remove this if we do not want to allow changing the ID 
		this.ID = ID;
	}


	/**
	 * Return current balance of this card.
	 * @return balance of the card.
	 */

	public double getBalance() {
		return this.balance;
	}
	/**
	 * Sets the balance for the card.
	 * @param balance double representing balance of the card.
	 */
	public void setBalance(double balance) {
			this.balance = balance;
	}
	
	/**
	 * Adding balance of the card.
	 * @param balance double representing balance of the card.
	 * @throws IOException Occurs when an IO operations fails.
	 */
	public void addBalance(double balance) throws IOException {
		if(balance == User.BalanceOption1 || balance == User.BalanceOption2 || balance == User.BalanceOption3 ) {
			this.balance += balance;
			CardManager1 cm = CardManager1.getInstance();
			cm.write();
		}
	}
	
	/**
	 * Return status of the card.
	 * @return false if the card is suspended, or true if the card is not suspended.
	 */
	public boolean getIsSuspended(){
		return this.isSuspended;
	}
	
	/**
	 * Sets up the status of the card by given current status.
	 * @param suspend boolean representing status of the card.
	 * @throws IOException Occurs when an IO operations fails.
	 */
	public void setIsSuspended(Boolean suspend) throws IOException {
		this.isSuspended = suspend;
	}

	
	/**
	 * Adding trip object into trip list by given a trip object.
	 * @param trip a Trip object that records the area that this person travels.
	 * @throws IOException Occurs when an IO operations fails.
	 */

	public void addTrip(Trip trip) throws IOException {
		this.trips.add(trip);
		TripManager tm = TripManager.getInstance();
		tm.fillUserTrips();
		for(int key: this.user.getMonthsWithTrips().keySet()) {
			if(key == trip.getMonth()) {
				if(this.user.getMonthsWithTrips().get(key) == 0) {
					this.user.getMonthsWithTrips().put(key, 1);
				}
			}
		}
	}
	
	/**
	 * Clean up the trip list.
	 */
	public void resetTrip() { // thinking about when to use it 
		this.trips.clear();
	}
	
	/**
	 * Return the trips list.
	 * @return the trips list.
	 */
	public ArrayList<Trip> getTrips() {
		return trips;
	}

	
	/**
	 * Printing ID: " + this.ID + "\nUser: " + this.user.getEmail() + "\nBalance: 
	 * " + this.balance + "\nSuspended?: " + this.isSuspended .
				
	 */
	public String toString() {
		return "ID: " + this.ID + "\nUser: " + this.user.getEmail() + "\nBalance: " + this.balance + 
				"\nSuspended?: " + this.isSuspended + "\n\n";
	}
}
