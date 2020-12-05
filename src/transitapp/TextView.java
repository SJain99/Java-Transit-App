package transitapp;


import java.util.*;

public class TextView implements Observer{
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
	}
}
