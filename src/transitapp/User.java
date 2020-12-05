package transitapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * It is a User class which is a child class of Person. 
 * It stores all cards for the owners. 
 * And owner can choose which card he/her is gong to use.
 * Also owner can add more cards and delete cards.
 * 
 * @author 
 *
 */
public class User extends Person {
	static int BalanceOption1 = 10;
	static int BalanceOption2 = 20;
	static int BalanceOption3 = 50;
	private ArrayList<Card> cards;
	private HashMap<Integer, Integer> monthsWithTrips;
	CardManager1 cm = CardManager1.getInstance();
	/**
	 * Construct a user with information of name and email address.
	 * @param name string representing name of the user.
	 * @param email string representing email address of the user.
	 */
	public User(String name, String email) {
		super(name, email);
		this.cards = new ArrayList<Card>();
		this.monthsWithTrips = new HashMap<Integer, Integer>();
		this.monthsWithTrips.put(1, 0);
		this.monthsWithTrips.put(2, 0);
		this.monthsWithTrips.put(3, 0);
		this.monthsWithTrips.put(4, 0);
		this.monthsWithTrips.put(5, 0);
		this.monthsWithTrips.put(6, 0);
		this.monthsWithTrips.put(7, 0);
		this.monthsWithTrips.put(8, 0);
		this.monthsWithTrips.put(9, 0);
		this.monthsWithTrips.put(10, 0);
		this.monthsWithTrips.put(11, 0);
		this.monthsWithTrips.put(12, 0);
	}
	
	/**
	 * Return a list of cards
	 * @return all cards of the owner
	 */
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	
	/**
	 * Given card's ID, return specify card.
	 * If ID is invalid, return null.
	 * @param id string representing the ID of specify card.
	 * @return card if the ID is valid or null if ID is invalid.
	 */
	public Card getCard(String id) {
		for(int i = 0; i < this.cards.size(); i++) {
			if(this.cards.get(i).getID() == id) {
				return this.cards.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Given specify card, add this card into owner's account.
	 * @param card a specify card created by Card class.
	 */
	public void addCard(Card card) throws IOException {
		if(this.cards.contains(card)) return;
		this.cards.add(card);
		card.setUser(this);
	}
	
	/**
	 * Given card's ID, remove this card from owner's account.
	 * If the ID does not exist in owner's account, then do nothing.
	 * @param id string representing the ID of specify card.
	 */
	public void deleteCard(String id) throws IOException {
		for(int i = 0; i < this.cards.size(); i++) {
			if(this.cards.get(i).getID() == id) {
				this.cards.remove(i);
			}
		}
	}
	
	public HashMap<Integer, Integer> getMonthsWithTrips() {
		return this.monthsWithTrips;
	}
	
	
	public String toString() {
		return "Name: " + this.getName() + "\nEmail: " + this.getEmail()
		+ "\n\n";
	}
	
	public void setName(String name) throws IOException {
		super.setName(name);
		UserManager1 um = UserManager1.getInstance();
		um.write();
	}
}
