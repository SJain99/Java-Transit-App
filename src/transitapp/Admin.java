package transitapp;

import java.io.IOException;

/**
 * a class of person that is able to manage the transit system
 * 
 */
public class Admin extends Person {
	
	private static AdminManager1 adminManager;
	
	/**
	 *  set up a admin by its name and email
	 * @param name a String represent the name of the admin
	 * @param email a String represent the email of the admin
	 */
	public Admin(String name, String email) {
		super(name, email);
	}
	
	
	public static AdminManager1 getAdminManager() {
		return adminManager;
	}
	
	public static void setAdminManager(AdminManager1 adminManager) {
		Admin.adminManager = adminManager;
	}
}
