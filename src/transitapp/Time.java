package transitapp;

/**
* a class convert string into time which are set of integers
* helps cauculate in other code
*/

public class Time {
	
	public int time;
	public int hour;
	public int minute;
	
	/**
	* convert 4 lenth string which represent time into integers
	* first 2 digit represent hours, last 2 digit represent minute
	* @param time  a string represent time with 4 digit
	* 			   2 digit hours and 2 digit minute
	*         	   e.g 1300 represent 1:00pm
	*/
	public Time(String time) {
		this.hour = Integer.parseInt(time.substring(0,2));
		this.minute = Integer.parseInt(time.substring(2,4));
		this.time = (this.hour * 60 ) + this.minute;
		
	}
}
