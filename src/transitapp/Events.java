package transitapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * a class that stores events
 * 
 */
public class Events {
	
	private ArrayList<String> events = new ArrayList<String>();
	
	public Events(){

	}
	
	/**
	 * upadte the events into the system
	 * by extracting the line of String in the file, which represent a trip
	 * @return an ArrayList of all the trips
	 * @throws IOException
	 */
	//Reads from the file, and outputs an ArrayList of all of the trips. Ex:
	//[1000 enters A9 station at 1100, 1000 exits A5 station at 1115, 1000 exits C9 stop at 1150]
	//If you'd like to compare first and second trip. Look at events[0] and events[1] and you'll get that output.
	public ArrayList<String> getEvents() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("src/events.txt"));
		Scanner scanner = new Scanner(in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			this.events.add(line);
		}
		scanner.close();
		in.close();
		return this.events; 
	}
	
	/**
	 * write the trip into a file with the elements of the trip
	 * @param direction a String that represent which direction the trip is heading to
	 * @param pointName a String that represent the name of the point of the trip
	 * @param routeType a String that represent the type of route
	 * @param time a String that reprsent the time of the trip
	 * @param dayOfYear a String that represent the date, the day of year, of the trip
	 * @throws IOException
	 */
	// What will be written to the file will be "1000 enters A9 station at 1100"
	// use Notepad++ if you want to view the text file, notepad doesn't show the line breaks.
	public void writeEvent(String cardID, String direction, String pointName, String routeType, String time,
			String dayOfYear) throws IOException{
		BufferedWriter out = new BufferedWriter(new FileWriter("src/events.txt", true));
		out.append("\n" + cardID + " " + direction + " " + pointName + " " + routeType + 
				" at " + time + " on day " + dayOfYear);
		out.close();
	}
}