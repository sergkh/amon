package ua.vntu.amon.json.converting;
/**
 * 
 * @author Yaric
 *
 */

import java.util.*;

public class User {
	private int age = 20 ;
	private String name = "Yaric";
	private List<String> messages = new ArrayList<String>(){
		
		private static final long serialVersionUID = 8520836882750845542L;
		

		{
		add("message 1 : Hello ");
		add("message 2 : Big");
		add("message 3 : World");
		}
	};
	

	/**
	 * @param args
	 */
	public String toString() {
		return "User [age = "  + age + " name =" + name + "," +
				"messages=" + messages + "]" ;
	}

}
