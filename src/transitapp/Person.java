package transitapp;

import java.io.IOException;

/**
 * It is a person class, storing person's name and email address.
 * It can set up the name and email address and it also return these two information.
 *  
 * @author 
 *
 */
public class Person {
	String name;
	private String email;
	
	/**
	 * Construct a person with information of name and email address.
	 * @param name string representing name of the person, can be a user or admin.
	 * @param email string representing email address of the person.
	 */
	public Person(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	/**
	 * Return the name of person.
	 * @return the name of user/admin
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets the name for person
	 * @param name string representing the name of user/admin
	 */
	public void setName(String name) throws IOException {
		this.name = name;
	}
	
	/**
	 * Return the email address of person
	 * @return the email address of user/admin
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Sets the email for person
	 * @param email string representing the email of user/admin
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Printing "Name: " + this.name + "\nEmail: " + this.email .
	 */
	public String toString() {
		return "Name: " + this.name + "\nEmail: " + this.email + "\n\n";
	}
}
