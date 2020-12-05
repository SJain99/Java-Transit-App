package transitapp;

import java.io.IOException;
import java.util.Observable;

/**

 * A class represents a tapping machine in a bus stop.  
 * @author ibrab
 */

public class Stop extends Observable implements Point {
	// Remember review what is private public default or protected 
	static double Fare = 2.0;
	private String name;
	private int id;
	
	public Stop(String name) {
		this.name = name;
		this.id = -1;
	}
	
	/**
	 * @throws IOException 
	 * 
	 * this function will call tapEnter or tapExit regarding of the user input from direction;
	 * @param direction  this is a user input string with either "enters" or "exits"
	 * @param time		this is a string representing the time tap happens
	 * @param card		this is the card used to tap
	 * 
	 */
	@Override
	public void tap(String direction, String time, Card card, int dayOfYear) throws IOException {
		if(direction.equals("enters")) {
			this.tapEnter(time, card, dayOfYear);
		} else if(direction.equals("exits")){
			this.tapExit(time, card, dayOfYear);
		}
	}
	
	/**
	 * this function will do a series of event when people entering a stop:
	 * create a trip 
	 * check if the last trip on the card is fullfilled
	 * if its fullfilled add a new trip to the card
	 * otherwise replace the last trip with new trip
	 * charge the user
	 * 
	 *  
	 * @param time \\ the string representing  the time of the tap
	 * @param card	\\ the card used to tap
	 * @throws IOException 
	 */
	public void tapEnter(String time, Card card, int dayOfYear) throws IOException {
		if (card.getIsSuspended()) {
			System.out.println("This card is suspended!");
			return;
		}
		Trip trip = new Trip(card, this, time, dayOfYear);
		Trip lastTrip = card.getLastTrip();
		if(lastTrip == null) {
			card.addTrip(trip);
		}else if(lastTrip.getEndTime() == "" || lastTrip.getEnd() == null) {// if no End_point/End_time for last hop
			lastTrip.setEnd(this);
			lastTrip.setEndTime(time);
			lastTrip.setIsActive(false);
			card.deduct(lastTrip, Trip.CostCap);
			card.addTrip(trip);
		}else {
			Boolean timeCondition = new Time(time).time < new Time(lastTrip.getEndTime()).time + Trip.TimeCap;
			Boolean locationCondition = this.getName().equals(lastTrip.getEnd().getName());
			if(lastTrip.getIsActive() && timeCondition && locationCondition){// why testing for lastTrip.getIsActive()?
				trip = lastTrip;
				trip.getHops().add(new Hop (this, time));
				trip.setEnd(null);
				trip.setEndTime("");
			}else {
				card.addTrip(trip);
			}		
		}
		card.deduct(trip, Fare);
		Trip lt = card.getLastTrip();
		if(lt != null) {
			Hop lastHop = trip.getHops().get(lt.getHops().size() - 1);
			this.setChanged();
			this.notifyObservers("User " + card.getUser().getName() + " has departed from " 
				+ lastHop.getHopStartPoint().getName() + " at: "+ lastHop.getStartTime());
		}
	}
	
	
	/**
	 * this class perform an exit tap which will do the following:
	 * close the trip
	 * notify Observers
	 * 
	 * 
	 * 
	 * 
	 * @param time
	 * @param card
	 * @throws IOException
	 */
	

	public void tapExit(String time, Card card, int dayOfYear) throws IOException{
		if (card.getIsSuspended()) {
			System.out.println("This card is suspended!");
			return;
		}
		Trip lastTrip = card.getLastTrip();
		if(lastTrip != null ){
			Hop lastHop = lastTrip.getHops().get(lastTrip.getHops().size() - 1);
			//check last trip has no end point
			if(lastHop.getEndTime() == "" && lastHop.getHopStartPoint().getClass() == this.getClass()) {
				lastTrip.close(this, time);
				this.setChanged();
				this.notifyObservers("User " + card.getUser().getName() + 
					" has reached  " +  lastTrip.getEnd().getName() + " at: " + lastTrip.getEndTime());
				return;
			}
		}
		Trip trip = new Trip(card, new Station("Missed entrance tapping", -1), "2359",
				dayOfYear);
		card.deduct(trip, Trip.CostCap);
		trip.setIsActive(false);
		trip.close(this, time);
		this.setChanged();
		this.notifyObservers("User " + card.getUser().getName() + 
				" has reached  " +  lastTrip.getEnd().getName() + " at: " + lastTrip.getEndTime());
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getId() {
		return this.id;
	}
}