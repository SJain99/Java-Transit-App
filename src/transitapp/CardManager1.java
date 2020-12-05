package transitapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * a class that manage Cards
 * add or delet Cards from the system
 * 
 */
public class CardManager1 extends FileManager {
	
	private static CardManager1 cminstance;
	private HashMap<String, Card> cards;
	
	CardManager1() {	
		HashMap<String, Card> cd = new HashMap<String, Card>();
		this.cards = cd;
	}
	
	public static CardManager1 getInstance() {
		if(cminstance == null) {
			cminstance = new CardManager1();
		}
		return cminstance;
	}
	
	/**
	 * check if the card is in the system
	 * @param ID a String that represent the ID of a card
	 * @return the Card that has to the ID
	 */
	public Card getCard(String ID) {
		if (this.cards.containsKey(ID)) {
			return this.cards.get(ID);
		}
		return null;
	}
	
	public HashMap<String, Card> getCards(){
		return this.cards;
	}
	
	public ArrayList<Card> allCards() {
		ArrayList<Card> allCards = new ArrayList<Card>();
		for(String key: this.cards.keySet()) {
			allCards.add(this.cards.get(key));
		}
		return allCards;
	}
	
	/**
	 * add a card into the system
	 * @param ID a String that represent ID of the card
	 * @return true if the card is successfully added into the system; 
	 * 		   false if the card is alrady in the system
	 * @throws IOException
	 */
//	public boolean addCard(String ID) throws IOException {
//		if (this.cards.get(ID) == null) {
////			Card card = new Card(ID);
////			this.cards.put(ID, card);
////			this.write();
//			return false;
//		}
//		Card card = this.cards.get(ID);
//		this.cards.put(ID, card);
//		this.write();
//		return true;
//	}
	
	/**
	 * remove a card from the system
	 * @param ID a String that represent ID of the card
	 * @return true if the card was sucessfully removed into the system; 
	 *         false if the card is not in the system
	 * @throws IOException
	 */
	public boolean removeCard(String ID) throws IOException {
			Card card = this.cards.remove(ID);
			if (card == null) return false;
			this.write();
			return true;
	}
	
	/**
	 * write the cards into file
	 * @throws IOException
	 */
	public void write() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("src/cards.txt"));
		if(this.cards == null) {
			return;
		}
		for(Entry<String, Card> entry: this.cards.entrySet()) {
			out.append(entry.getValue().toString());
		}
		out.close();
//		this.cards.forEach((k, v) -> {
//			try {
//				out.append(v.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//		out.close();
	}
	
	/**
	 * update every Card into the system from a file
	 * by extracting ID, user email,strBalance, and strIsSuspended of card from the file
	 * then add the Admin into the system by those elements
	 * @param a file of String that contains all the Cards
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void initialize(String file) throws FileNotFoundException, IOException {
		String ID = "";
		String userEmail = "";
		String strBalance = "";
		double balance = 0;
		String strIsSuspended = "";
		boolean isSuspended = false;
		BufferedReader in = new BufferedReader(new FileReader("src/" + file));
		Scanner scanner = new Scanner(in);
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains("ID")) {
				ID = line.replace("ID: ", "").replace("\n", "");
			}
			if(line.contains("User")) {
				userEmail = line.replace("User: ", "").replace("\n", "");
			}
			if(line.contains("Balance")) {
				strBalance = line.replace("Balance: ", "").replace("\n", "");
				balance = Double.parseDouble(strBalance);
			}
			if(line.contains("Suspended")) {
				strIsSuspended = line.replace("Suspended?: ", "").replace("\n", "");
				isSuspended = Boolean.parseBoolean(strIsSuspended);
			}
			if(ID != "" && userEmail != "" && strBalance != "" && strIsSuspended != "") {
				Card card = new Card(ID);
				UserManager1 um = UserManager1.getInstance();
				User user = um.getUser(userEmail);
				user.addCard(card);
				card.setBalance(balance);
				card.setIsSuspended(isSuspended);
				this.cards.put(ID, card);
				ID = "";
				userEmail = "";
				strBalance = "";
				strIsSuspended = "";
			}
		}
		scanner.close();
		in.close();
	}
}

