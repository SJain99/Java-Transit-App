package transitapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class AdminController {
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	private static AdminManager1 am = AdminManager1.getInstance();
	private static CardManager1 cm = CardManager1.getInstance();
	private static UserManager1 um = UserManager1.getInstance();
	private static TripManager tm = TripManager.getInstance();
  static String RegCode = "0000";
	
	
	void adminMain() throws IOException {
		while(true) {
			System.out.println("Please choose to Sign Up or Login: ");
			String c = AdminController.stdin.readLine();
			if(c.equals("Login")) {
				this.adminLogIn();
			}else{
				System.out.println("Please enter your name: ");
				String name = AdminController.stdin.readLine();
				System.out.println("Please enter your email: ");
				String email = AdminController.stdin.readLine();
				System.out.println("Please enter the registration Code: ");
				String code = AdminController.stdin.readLine();
				if(am.register(name, email) && code.equals(RegCode)) {
					System.out.println("Registration Successful");
				}else if(!code.equals(RegCode)){
					System.out.println("Invalid Registration Code!");
				}else {
					System.out.println("This admin is already registered.");
				}
			}
		}
	}
	
	void adminLogIn() throws IOException {

		
		Admin logedInAdmin = this.login();
		if(logedInAdmin != null) {
			while(true) {
				String option = pickOption();
				perform(logedInAdmin, option);
				System.out.println("Type E to exit or another key to go back to options: ");
				String isexiting = AdminController.stdin.readLine();
				if(isexiting.equals("E")) break;
			}
		}
	}
	// login
	public Admin login() throws IOException {
		System.out.println("Please enter your name: ");
		String name = AdminController.stdin.readLine();
		System.out.println("Please enter your email: ");
		String email = AdminController.stdin.readLine();
		if(AdminController.getAdminManager().login(email) &&
				AdminController.getAdminManager().getAdmins().get(email).getName().equals(name)) {
			System.out.println("Login Sucessful!");
			return AdminController.getAdminManager().getAdmin(email);
			
		}else {
			System.out.println("Invalid Login, Please try again!");
			return null;
		}
	}
	
	public void setVariables() throws NumberFormatException, IOException {
		System.out.println("Please enter the trip price cap: ");
		Trip.CostCap = Integer.parseInt(AdminController.stdin.readLine());
		System.out.println("Please enter the trip time cap: ");
		Trip.TimeCap = Integer.parseInt(AdminController.stdin.readLine());
		System.out.println("Please enter the standard bus fare: ");
		Stop.Fare = Integer.parseInt(AdminController.stdin.readLine());
		System.out.println("Please enter the standard train per-station fare: ");
		Station.Fare = Double.parseDouble(AdminController.stdin.readLine());
		System.out.println("Please enter the new card default balance: ");
		Card.DEFAULT_BALANCE = Double.parseDouble(AdminController.stdin.readLine());
		System.out.println("Please enter the first option for card balance refilling: ");
		User.BalanceOption1 = Integer.parseInt(AdminController.stdin.readLine());
		System.out.println("Please enter the second option for card balance refilling: ");
		User.BalanceOption2 = Integer.parseInt(AdminController.stdin.readLine());
		System.out.println("Please enter the third option for card balance refilling: ");
		User.BalanceOption3 = Integer.parseInt(AdminController.stdin.readLine());
		System.out.println("Please enter the Admin secret registration code: ");
		AdminController.RegCode = AdminController.stdin.readLine();
	}
	
	public String pickOption() throws IOException {
		System.out.println("Kindly select one of the following actions"
				+ "by Typing the Capital Letter in it: ");
		System.out.println("generate daily Report || Add admin ||\n"
				+ "set operation Variables || reMove admin ||\n"
				+ "add User || rEmove user");
		return AdminController.stdin.readLine();
	}
	
	public void perform(Admin logedInAdmin, String option) throws IOException {
		if( option.equals("R")) {
			System.out.println("Revenues for the day: " + AdminController.getTripManager().
					countDailyFares());
			System.out.println("Stops reached: " + AdminController.getTripManager().
					countStops());
		}
		
		if(option.equals("A")) {
			// Add admin
			System.out.println("Please enter the new admin's name: ");
			String name = AdminController.stdin.readLine();
			System.out.println("Please enter the new admin's email: ");
			String email = AdminController.stdin.readLine();
			Admin admin = new Admin(name, email);
			AdminController.getAdminManager().getAdmins().put(email, admin);
			AdminController.getAdminManager().write();
			System.out.println("The admin of name: "+ admin.getName() +
					" has been added to the records"); // aslo we can use use TextView
			
		}
		if(option.equals("V")){
			// set operation Variables
			this.setVariables();
		}
		if(option.equals("M")) {
			//  Remove admin
			System.out.println("Please enter the admin's name: ");
			String name = AdminController.stdin.readLine();
			System.out.println("Please enter the admin's email: ");
			String email = AdminController.stdin.readLine();
			if(AdminController.getAdminManager().getAdmins().get(email) != null &&
					AdminController.getAdminManager().getAdmins().get(email).getName().equals(name)){
				AdminController.getAdminManager().getAdmins().remove(email);
				AdminController.getAdminManager().write();
				System.out.println("The admin of name: "+ name + 
						" has been removed from the records");// implementing textview
			}else {
				System.out.println("The admin of name: "+ name + 
						" does not exist");
			}
		}
//		if(option.equals("S")) {
//			// add Stop/Station to a rout
//		}
//		if(option.equals("D")) {
//			// Delete stop/station
//		}
		if(option.equals("U")) {
			// add User
			System.out.println("Please enter the new user's name: ");
			String name = AdminController.stdin.readLine();
			System.out.println("Please enter the new user's email: ");
			String email = AdminController.stdin.readLine();
			User user = new User(name, email);
			AdminController.getUserManager().getUsers().put(email, user);
			AdminController.getUserManager().write();
			System.out.println("The user of name: "+ user.getName() +
					" has been added to the records"); // aslo we can use use TextView
		}
		
		if(option.equals("E")){
			System.out.println("Please enter the user's name: ");
			String name = AdminController.stdin.readLine();
			System.out.println("Please enter the user's email: ");
			String email = AdminController.stdin.readLine();
			if(AdminController.getUserManager().getUsers().get(email) != null &&
					AdminController.getUserManager().getUsers().get(email).getName().equals(name)){
				AdminController.getUserManager().getUsers().remove(email);
				AdminController.getUserManager().write();
				System.out.println("The user of name: "+ name + 
						" has been removed from the records");// implementing textview
			}else {
				System.out.println("The user of name: "+ name + 
						" does not exist");
			}		
		}
	}
	public static CardManager1 getCardManager() {
		return cm;
	}
	
	public static void setCardManager(CardManager1 cm) {
		AdminController.cm = cm;
	}
	
	public static AdminManager1 getAdminManager() {
		return am;
	}
	
	public static void setAdminManager(AdminManager1 am) {
		AdminController.am = am;
	}
	
	public static UserManager1 getUserManager() {
		return um;
	}
	
	public static void setUserManager(UserManager1 um) {
		AdminController.um = um;
	}
	
	public static TripManager getTripManager() {
		return tm;
	}
	
	public static void setTripManager(TripManager tm) {
		AdminController.tm = tm;
	}
}
