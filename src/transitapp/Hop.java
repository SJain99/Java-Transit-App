package transitapp;

/**
 * a class that stores little trips which have start point, end point, start time ,end time, 
 * and the period of the hop which contains how long the hop is
 * the point of hop is a trip might not only have one start point and end point
 * it is easier to seperate a trip into small trips in order to calculate fees
 */
public class Hop {
	
	private Point hopStartPoint;
	private Point hopEndPoint;
	private String startTime;
	private String endTime;
	//private int id;
	private int period;

	/**
	 * initialize a hope by input the start location and start time
	 * @param startPoint a Point that a location the hop starting at
	 * @param startTime a String that represent when is the hop happened
	 * 					the starttime contains 4 digit which are 2 for hours and 2 for minutes 
	 */
	public Hop(Point startPoint, String startTime) {
		this.hopStartPoint = startPoint;
		this.startTime = startTime;
		this.endTime = "";
		this.period = 0;
	}
	
	/**
	 * a function calculates the overall time of a hop
	 * @return int  a period of the hop
	 */
	public int calculatePeriod() {
		int start = new Time(this.endTime).time;
		int end = new Time(this.startTime).time;
		if(end > start) {
			return end - start;
		} else {
			// TO-DO: Handle this case
		}
		return 0;	
	}
	
	public Point getHopStartPoint() {
		return hopStartPoint;
	}
	
	public void setHopStartPoint(Point hopStartPoint) {
		this.hopStartPoint = hopStartPoint;
	}
	
	public Point getHopEndPoint() {
		return hopEndPoint;
	}
	
	public void setHopEndPoint(Point hopEndPoint) {
		this.hopEndPoint = hopEndPoint;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	} 
}
