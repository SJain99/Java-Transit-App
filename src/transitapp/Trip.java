package transitapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
* a class stores a trip 
* a trip has a trip id, point of start and en, time of start and end, the cost, and if the trip is activate or not
* a trip also contains a arraylist of hops which stores little trips
*/
public class Trip {
	
	static int TimeCap = 120;
	static int CostCap = 6;
	private String id;
	private Card card;
	private int month;
	private int dayOfYear;
	private Point start;
	private Point end;
	private String startTime;
	private String endTime;
	private double cost;
	private boolean isActive;
	private ArrayList<Hop> hops;
	/**
	 * initializ a trip with start location and the start time
	 * creat a new hop to store this trip at the same time
	 * @param start  a Point that a location the trip starting at
	 * @param startTime a String that represent when is the trip happened
	 * 					the starttime contains 4 digit which are 2 for hours and 2 for minutes 
	 */
	static TripManager tripManager;
	private static Date date;
	
	public Trip(Card card, Point start, String startTime, int dayOfYear) {
		this.id = (start.getName() + " " + startTime);
		this.card = card;
		this.month = Trip.getDate().generateMonthFromDOY(this.dayOfYear);
		this.dayOfYear = dayOfYear;
		this.startTime = startTime;
		this.start = start;
		this.end = null;
		this.endTime = "";
		this.isActive = true;
		this.setCost(0.0);
		this.hops = new ArrayList<Hop>(); // is this necessary ?
		this.hops.add(new Hop(start, startTime));
	}
	/**
	 * a fucntion to close a trip by input end location and end time
	 * close the hop at the same time by set the end point and end time for the hop only if the hop has no end time
	 * @param endPoint a Point that a location the trip ending at
	 * @param endtime a String that represent when is the trip end
	 * 					the endtime contains 4 digit which are 2 for hours and 2 for minutes 
	 */	
	public void close(Point endPoint, String endtime) throws IOException {
		Hop lastHop = this.hops.get(this.hops.size() - 1);
		if(lastHop.getEndTime() == ""){
			lastHop.setHopEndPoint(endPoint);
			lastHop.setEndTime(endtime);
			this.setEnd(endPoint);
			this.setEndTime(endtime);
			if( new Time(this.endTime).time >= new Time(this.startTime).time + Trip.TimeCap) {
				this.setIsActive(false);
			}
		}else {
			// if last Hop has an end time , we dealt with that in Tap 
		}
	}
	
	//  Remember to delete all not wanted setters to avoid unwanted user manipulation ( security)
//	public void setStartTime(String startTime) {
//		this.startTime = startTime;
//	}
	
	public String getStartTime() {
		return this.startTime;
	}
	
//	public void setStart(Point start) {
//		this.start = start;
//	}
	
	public Point getStart() {
		return this.start;
	}
	
	public void setEnd(Point end) {
		this.end = end;
	}
	
	public Point getEnd() {
			return this.end;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getEndTime() { // end time can be 0
			return this.endTime;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean getIsActive() {
		return this.isActive;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getId() {
		return id;
	}

	public ArrayList<Hop> getHops() {
		return hops;
	}

//	public void setHops(ArrayList<Hop> hops) {
//		this.hops = hops;
//	}

//	public int getPeriod() {
//		
//	}
//	public void setId(String id) {
//		this.id = id;
//	}

	public Card getCard() {
		return this.card;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getDayOfYear() {
		return this.dayOfYear;
	}
	
	public static TripManager getTripManager() {
		return tripManager;
	}
	
	public static void setTripManager(TripManager tripManager) {
		Trip.tripManager = tripManager;
	}
	
	public static Date getDate() {
		return date;
	}
	
	public static void setDate(Date date) {
		Trip.date = date;
	}
	
	public String toString() {
		int tripDuration = Integer.parseInt(endTime) - Integer.parseInt(startTime);
		return "\nStart Point: " + this.start.getName() + "\nEnd Point: " + this.end.getName() 
				+ "\nCost: " + this.cost + "\nDuration: " + tripDuration + " minutes\n\n";
	}
}
