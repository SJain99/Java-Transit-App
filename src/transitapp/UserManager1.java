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
 * a class that manage the Users
 * register or log in a User
 * get a User from the system
 * 
 */
public class UserManager1 extends FileManager {
	
	private static UserManager1 uminstance;
	private  HashMap<String , User> users;
	
	UserManager1() {	
		HashMap<String , User> us = new HashMap<String , User>();
		this.users = us;
	}
	
	public static UserManager1 getInstance() {
		if(uminstance == null) {
			uminstance = new UserManager1();
		}
		
		return uminstance;
	}
	
	public HashMap<String, User> getUsers() {
		return this.users;
	}
	
	public ArrayList<User> allUsers() {
		ArrayList<User> allUsers = new ArrayList<User>();
		for(String key: this.users.keySet()) {
			allUsers.add(this.users.get(key));
		}
		return allUsers;
	}
	
	/**
	 * get a User form its email
	 * @param email a String that represent the email of a User
	 * @return a User that has the email;
	 *         null if the email is not in the system 
	 */
	public User getUser(String email) {
		if (this.users.get(email)!= null) {
			return this.users.get(email);
		}
		return null;
	}
	
	/**
	 * register a user into the system
	 * @param name a String represent the name of a User
	 * @param email a String represent the email of a User
	 * @return true if the User is succesffuly registered into the system;
	 *         false if the User is already in the system
	 * @throws IOException
	 */
	public boolean register(String name, String email) throws IOException {
		if (this.users.containsKey(email)) {
			return false;
		}
		User user = new User(name, email);
		this.users.put(email, user);
		this.write();
		return true;
	}

	/**
	 * write the Users into a file
	 * @throws IOException
	 */
	public void write() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("src/users.txt"));
		this.users.forEach((k, v) -> {
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
	 * login a user into the system
	 * @param email a String that represent the email of a User
	 * @return true if the user successfully logged into the system;
	 *         false if the user is not in the system
	 */
	public boolean login(String email) {
		if (this.users.containsKey(email)) return true;
		return false;
	}
	
	/**
	 * update every User into the system from a file
	 * by extracting name, email, and current email of User from the file
	 * then add the User into the system by those elements
	 * @param a file of String that contains all the Users
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
				User user = new User(name, email);
				this.users.put(email, user);
				name = "";
				email = "";
			}
		}
		scanner.close();
		in.close();
	}
}


