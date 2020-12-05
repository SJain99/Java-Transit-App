package transitapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * a class stores the date
 * config the date into correct format
 * put the dates into the system by reading the file
 * 
 */
public class Date extends FileManager {
	
	private static Date date = null;
	public static HashMap<Integer, Integer> monthsAndDays = new HashMap<Integer, Integer>();
	public int currentMonth = 0;
	public int currentDay = 0;
	public int dayofYear = 0;
	
	private Date() { }
	
	public static Date getInstance() {
		if(date == null) {
			date = new Date();
			monthsAndDays = new HashMap<Integer, Integer>();
		}
		return date;
	}
	
	/**
	 * correct the date into the correct format
	 * by checking if the day of date is in the range of the days of the month
	 * if the month of date is in the range of 1-12
	 * if not, correct them
	 * @throws IOException
	 */
	public void increment() throws IOException {
		monthsAndDays.put(1, 31);
		monthsAndDays.put(2, 28);
		monthsAndDays.put(3, 31);
		monthsAndDays.put(4, 30);
		monthsAndDays.put(5, 31);
		monthsAndDays.put(6, 30);
		monthsAndDays.put(7, 31);
		monthsAndDays.put(8, 31);
		monthsAndDays.put(9, 30);
		monthsAndDays.put(10, 31);
		monthsAndDays.put(11, 30);
		monthsAndDays.put(12, 31);
		
		this.currentDay += 1;
		this.dayofYear += 1;
		if(this.currentDay > this.monthsAndDays.get(currentMonth)) {
			this.currentDay = 1;
			this.currentMonth += 1;
		}
		if(this.currentMonth > 12) {
			this.currentMonth = 1;
		}
		if(this.dayofYear > 365) {
			this.dayofYear = 1;
		}
		this.write();
	}
	
	/**
	 * use the dayofmonth to check which month the day is at
	 * @param dayOfYear a int that represent the dayofYear
	 * @return a int that represent the month that the dayofYear is at
	 */
	public int generateMonthFromDOY(int dayOfYear) {
		int month = 1;
		int count = 0;
		for(int key: this.monthsAndDays.keySet()) {
			if(dayOfYear > this.monthsAndDays.get(key) + count) {
				month += 1;
				count += this.monthsAndDays.get(key);
			} else {
				return month;
			}
		}
		return month;
	}
	
	/**
	 * wirte the Date into a file
	 * @throws IOException
	 */
	public void write() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("src/date.txt"));
		out.append(this.toString());
		out.close();
	}
	
	public String toString() {
		return "Current Month: " + this.currentMonth + 
				"\nCurrent Day: " + this.currentDay + 
				"\nDay of Year: " + this.dayofYear;
	}
	
	/**
	 * update every Dates into the system
	 * by extracting month, day, and dayofyear of Date from the file
	 * then add the Date into the system by the month, day, and dayofyear
	 * @param a file of String that contains all the Date
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void initialize(String file) throws FileNotFoundException, IOException {
		String strMonth = "";
		String strDay = "";
		String strDayOfYear = "";
		BufferedReader in = new BufferedReader(new FileReader("src/" + file));
		Scanner scanner = new Scanner(in);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains("Current Month")) {
				strMonth = line.replace("Current Month: ", "").replace("\n", "");
			}
			if(line.contains("Current Day")) {
				strDay = line.replace("Current Day: ", "").replace("\n", "");
			}
			if(line.contains("Day of Year")) {
				strDayOfYear = line.replace("Day of Year: ", "").replace("\n", "");
			}
		}
		scanner.close();
		in.close();
		if(strMonth == "" && strDay == "") {
			this.currentMonth = 1;
			this.currentDay = 1;
			this.dayofYear = 1;
			this.write();
		} else {
			this.currentMonth = Integer.parseInt(strMonth);
			this.currentDay = Integer.parseInt(strDay);
			this.dayofYear = Integer.parseInt(strDayOfYear);
			this.increment();
		}
	}
}
