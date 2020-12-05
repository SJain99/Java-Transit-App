package transitapp;

import java.io.IOException;
import java.util.Observable;
/**
 * this class represents a station in a subway system
 * @author hongweiwen
 *
 */
public class Station extends Observable implements Point {
	
	static double Fare = 0.5;
	private String name;
	private int id;

	public Station(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	@Override
	public void tap(String direction, String time, Card card, int dayOfYear) throws IOException {
		if(direction.equals("enters")) {
			this.tapEnter(time, card, dayOfYear);
		} else if(direction.equals("exits")){
			this.tapExit(time, card, dayOfYear);
		}
		
	}
	
	/**
	 * this function performs a tap when entering the station
	 * 
	 * 
	 * @param time	the time when the tap happens
	 * @param card	the card being tapped
	 * @throws IOException 
	 */
	public void tapEnter(String time, Card card, int dayOfYear) throws IOException {
		if (card.getIsSuspended()) {
			System.out.println("This card is suspended!");
		}else {
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
				// Maybe some output ... 
			}else {
				Boolean timeCondition = new Time(time).time < new Time(lastTrip.getEndTime()).time + Trip.TimeCap;
				Boolean locationCondition = this.getName().equals(lastTrip.getEnd().getName());
				if(lastTrip.getIsActive() && timeCondition && locationCondition){
					trip = lastTrip;
					trip.getHops().add(new Hop (this, time));
					trip.setEnd(null);
					trip.setEndTime("");
				}else {
					card.addTrip(trip);
				}	
			}
			Trip lt = card.getLastTrip();
			if(lt != null) {
				Hop lastHop = trip.getHops().get(lt.getHops().size() - 1);
				this.setChanged();
				this.notifyObservers("User " + card.getUser().getName() + " has departed from " 
					+ lastHop.getHopStartPoint().getName() + " at: "+ lastHop.getStartTime());
			}
		}
		
	}

	/**
	 * this function performs a tap when exitting the station
	 * @param time	the time tap happens
	 * @param card	the card being tapped
	 * @throws IOException
	 */
	public void tapExit(String time, Card card, int dayOfYear) throws IOException {
		if (card.getIsSuspended()) {
			System.out.println("This card is suspended!");
		}else {
			Trip lastTrip = card.getLastTrip();
			if(lastTrip != null) {
				Hop lastHop = lastTrip.getHops().get(lastTrip.getHops().size() - 1);
				if (card.getIsSuspended()) {
					System.out.println("This card is suspended!"); //later we can create a method to show something on GUI
					return;
				}
				//check last trip has no end point
				if(lastHop.getEndTime() == "" && lastHop.getHopStartPoint().getClass() == this.getClass() ) {
					// check if last trip begins with a stop instead of a train station
					double fee = calculate(lastHop); 
					card.deduct(lastTrip, fee);
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
		
	}
	
	/**
	 * this function calculate the fares and returns it
	 * @param lastHop represents the last point the card tapped
	 * @return	  this function returns the fare
	 */
	
	private double calculate(Hop lastHop) {
		int numberOfStations = 0;
		numberOfStations = Math.abs(this.getId() - lastHop.getHopStartPoint().getId());
		return numberOfStations * Fare;
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
	
	public void setId(int id) {
		this.id = id;
		
	}
	
	public static void setFare(double fare) {
		Fare = fare;
		
	}
}
