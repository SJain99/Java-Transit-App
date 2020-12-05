package transitapp;

import java.io.IOException;

/**
 * An abstract class for a point that has a tapping machine in a transportation system.
 * @author IB
 *
 */

public interface Point {
	


	
	/**
	 * 
	 * @param direction
	 * @param time
	 * @param card
	 * @throws IOException 
	 */
	public abstract void tap(String direction, String time, Card card, int dayOfYear) throws IOException;
	public void setName(String name);
	
	public String getName();

	public int getId();
	
//	public static void setFARE(double fare) {
//		FARE = fare;
//	}
//	
//	public static double getFARE() {
//		return FARE;
//	}
	

}
