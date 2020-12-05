package transitapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Controller {
	
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	static Controller c;
	static String RegCode = "0000";
	
	public static void main(String[] args) throws IOException {
		Loader.main(args);
		while(true) {
			System.out.println("Are you an Admin or a User: ");
			String choice = Controller.stdin.readLine();
			if(choice.equals("Admin")) {
				AdminController ac = new AdminController();
				ac.adminMain();
			}else if(choice.equals("User")) {
				UserController uc = new UserController();
				uc.userMain();
			}
		}	
	}
}
	
	
