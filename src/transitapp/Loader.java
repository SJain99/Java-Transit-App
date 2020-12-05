package transitapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Loader {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		AdminManager1 adminManager  = AdminManager1.getInstance();
		CardManager1 cardManager  = CardManager1.getInstance();
		UserManager1 userManager  = UserManager1.getInstance();
		TripManager tripManager  = TripManager.getInstance();
		RouteManager routeManager = RouteManager.getInstance();
		Date date = Date.getInstance();
		adminManager.initialize("admins.txt");
		userManager.initialize("users.txt");
		cardManager.initialize("cards.txt");
		routeManager.initialize("newRoute.txt");
		date.initialize("date.txt");
		Trip.setDate(date);
		TextView view = new TextView();
		Events events = new Events();
		for(String item : events.getEvents()) {
			String[] translatedEvent = new String[9];
			translatedEvent = item.split(" ");
			String cardID = translatedEvent[0];
			Card card = cardManager.getCard(cardID);
			String direction = translatedEvent[1];
			String pointName = translatedEvent[2];
			String pointType = translatedEvent[3];
			String time = translatedEvent[5];
			String strDayOfYear = translatedEvent[8];
			int dayOfYear = Integer.parseInt(strDayOfYear);
			
			String routType = "BUS";
			if(pointType.equals("station")) {
				routType = "TRAIN";
			}
			for(Entry<String, Route> entry : routeManager.routes.entrySet()) {
				if(entry.getValue().getRouteType().equals(routType)){
					Point point = entry.getValue().route.get(pointName);
					if(point != null) {
						point.tap(direction, time, card, dayOfYear);
						break;
					}
				}
			}
		}
		tripManager.fillUserTrips();
	}
}
