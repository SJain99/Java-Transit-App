package transitapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

public class UserController {

	//private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	//private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	private static CardManager1 cm = CardManager1.getInstance();
	private static UserManager1 um = UserManager1.getInstance();;
	private static TripManager tm = TripManager.getInstance();
	
	
	void userMain() throws IOException {
		while(true) {
			System.out.println("Please choose to Sign Up or Login: ");
			String c = UserController.stdin.readLine();
			if(c.equals("Login")) {
				this.userLogin();
			}else if(c.equals("SignUp")){
				System.out.println("Please enter your name: ");
				String name = UserController.stdin.readLine();
				System.out.println("Please enter your email: ");
				String email = UserController.stdin.readLine();
				if(um.register(name, email)) {
					System.out.println("Registration Successful");
				}else {
					System.out.println("This user is already registered");
				}
			}
		}
	}
	
	void userLogin() throws IOException {
		User logedInUser = this.login();
		if(logedInUser != null) {
			while(true) {
				String option = pickOption();
				perform(logedInUser, option);
				System.out.println("Type E to exit or another key to go back to options: ");
				String isexiting = UserController.stdin.readLine();
				if(isexiting.equals("E")) break;
			}
		}
	}
	// login
	public User login() throws IOException {
		System.out.println("Please enter your name: ");
		String name = UserController.stdin.readLine();
		System.out.println("Please enter your email: ");
		String email = UserController.stdin.readLine();
		if(UserController.getUserManager().login(email) && UserController.getUserManager()
				.getUser(email).getName().equals(name)) {
			System.out.println("Login Sucessful!");
			return UserController.getUserManager().getUser(email);
		}else {
			System.out.println("Invalid Login, Please try again!");
			return null;
		}
	}
	
	public String pickOption() throws IOException {
		System.out.println("Kindly select one of the following actions"
				+ " by Typing the Capital Letter in it:");
		System.out.println("view Balance || view last 3 Trips || Add a card || Suspend a card "
				+ "||\n Remove a card || add balanCe || view List of "
				+ "your cards ||\n Get monthly average cost || change Name");
		return UserController.stdin.readLine();
	}
	
	public void perform(User user, String option) throws IOException {
		if( option.equals("B")) {
			for(Card card : user.getCards()) {
				System.out.println("The card with ID " + card.getID() + 
						" has a balance of: " + card.getBalance());
			}
		}
		if(option.equals("T")) {
			while(true) {
				ArrayList<String> trips = UserController.getTripManager().lastThreeTrips(user);
				for(String trip: trips) {
					System.out.println(trip);
				}
				break;
			}
		}
		if(option.equals("A")){
			String cardID;
			while(true) {
				Random rand = new Random();
				cardID = String.format("%04d", rand.nextInt(10000));
				if(!UserController.getCardManager().getCards().containsKey(cardID)) break;
			}
			Card card = new Card(cardID);
			user.addCard(card);
			UserController.getCardManager().getCards().put(cardID, card);
			UserController.getCardManager().write();
			System.out.println("The card with ID: "+ cardID + 
					" has been added to your cards list");  
			// maybe we can add this to the method addCard and use the viewer to automatically push it to viewer

		}
		if(option.equals("S")) {
			String cardID;
			while(true) {
				System.out.println("Please enter one of your cards' ID, or 'exit': ");
				cardID = UserController.stdin.readLine();
				if( cardID.equals("exit")) break;
				Card card = UserController.getCardManager().getCards().get(cardID);
				if(card != null && card.getUser().getEmail().equals(user.getEmail())){
					card.setIsSuspended(true);
					UserController.getCardManager().write();
					System.out.println("The card with ID: " + cardID + " has been suspended!");
					break;
				}
			}
		}
		if(option.equals("R")) {
			while(true) {
				System.out.println("Please enter one of your cards' ID, or 'exit': ");
				String cardID = UserController.stdin.readLine();
				if( cardID.equals("exit")) break;
				Card card = UserController.getCardManager().getCards().get(cardID);
				if(card != null && card.getUser().getEmail().equals(user.getEmail())){
					user.deleteCard(cardID);
					UserController.getCardManager().getCards().remove(cardID, card);
					UserController.getCardManager().write();
					System.out.println("The card with ID: "+ cardID + " has been deleted!");
					break;
				}
			}
		}
		if(option.equals("C")) {
			while(true) {
				System.out.println("Please enter one of your cards' ID, or 'exit': ");
				String cardID = UserController.stdin.readLine();
				if( cardID.equals("exit")) break;
				Card card = UserController.getCardManager().getCards().get(cardID);
				if(card != null && card.getUser().getEmail().equals(user.getEmail())){
					System.out.println(" Please choose how much balance to add: \n" 
				+ User.BalanceOption1 + "$, " + User.BalanceOption2+"$ ,or "
							+ User.BalanceOption3 + "$.");
				int amount =  Integer.parseInt(UserController.stdin.readLine());
				card.addBalance(amount);
				System.out.println("Your Card of ID: " + cardID + " now has a balance of: " + card.getBalance());
				}
					
			}
		}
		if(option.equals("L")) {
			ArrayList<Card >list = user.getCards();
			if (list.size() == 0) {
				System.out.println("You have no cards!...");
			}else {
				for( Card c:list) {
					System.out.println(c);
				}
			}
		}
	
			
		if(option.equals("G")) {

			while(true) {
				System.out.println("Your average monthly cost is: " + UserController
						.getTripManager().monthlyAverageCost(user));
				break;
			}

			//get monthly average
		}

		if(option.equals("N")) {
			//change user Name
			System.out.println("Please enter your new name: ");
			String name = UserController.stdin.readLine();
			user.setName(name);
			System.out.println("Your name has been changed to " + name + "!");
			System.out.println(user.name);

		}
	}
	
	public static CardManager1 getCardManager() {
		return cm;
	}
	
	public static void setCardManager(CardManager1 cm) {
		UserController.cm = cm;
	}
	
	public static UserManager1 getUserManager() {
		return um;
	}
	
	public static void setUserManager(UserManager1 um) {
		UserController.um = um;
	}
	
	public static TripManager getTripManager() {
		return tm;
	}
	
	public static void setTripManager(TripManager tm) {
		UserController.tm = tm;
	}
}
