package transitapp;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * a class that manage the Admins
 * stores all Admin from file into the system
 * regester or delete Admin from the system
 * 
 */
public class AdminManager1 extends FileManager {

	private static AdminManager1 adminManager;
	private  HashMap< String, Admin> admins;
	
	AdminManager1() { 
		HashMap< String, Admin> ad = new HashMap< String, Admin>();
		this.admins = ad;
	}
	
	public static AdminManager1 getInstance() {
		if(adminManager == null) {
			adminManager = new AdminManager1();
		}
		return adminManager;
	}
	
	public ArrayList<Admin> allAdmins() {
		ArrayList<Admin> allAdmins = new ArrayList<Admin>();
		for(String key: this.admins.keySet()) {
			allAdmins.add(this.admins.get(key));
		}
		return allAdmins;
	}
	
	/**
	 * register a admin into the system by its name and email
	 * @param name a String represent the name of admin
	 * @param email a String represent the email of admin
	 * @return true if admin not in the system and successfully registeried; 
	 *         flase if the admin is already in the system
	 * @throws IOException
	 */
	public boolean register(String name, String email) throws IOException {
		if(this.admins.get(email) != null) {
			return false;
		}
		Admin admin = new Admin(name, email);
		this.admins.put(email, admin);
		this.write();
		return true;
	}
	
	/**
	 * write the Admins into file
	 * @throws IOException
	 */
	public void write() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("src/admins.txt"));
		this.admins.forEach((k, v) -> {
            try {
				out.append(v.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
		out.close();
	}
	
	/**
	 * check if the admin has already logged in or not
	 * @param email a String represent email of the admin
	 * @return true if the admin in the system; 
	 * 		   flase if the admin is not in the system
	 */
	public boolean login(String email) {
		if(this.admins.get(email) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * update every Admin into the system from a file
	 * by extracting name and email of admin from the file
	 * then add the Admin into the system by name and email
	 * @param a file of String that contains all the Admins
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void initialize(String file) throws FileNotFoundException, IOException {
		String name = "";
		String email = "";
		BufferedReader in = new BufferedReader(new FileReader("src/" + file));
		Scanner scanner = new Scanner(in);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains("Name")) {
				name = line.replace("Name: ", "").replace("\n", "");
			}
			if(line.contains("Email")) {
				email = line.replace("Email: ", "").replace("\n", "");
			}
			if(name != "" && email != "") {
				Admin admin = new Admin(name, email);
				this.admins.put(email, admin);
				name = "";
				email = "";
			}
		}
		scanner.close();
		in.close();
	}

	public HashMap<String, Admin> getAdmins() {
		return this.admins;
	}
	public Admin getAdmin(String email) {
		return this.admins.get(email);
	}
}
