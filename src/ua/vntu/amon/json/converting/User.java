package ua.vntu.amon.json.converting;
/**
 * 
 * @author Yaric
 *
 */

import java.util.*;

public class User {
	private String jsonrpc = "2.0";
	private String name = "Yaric";
	private List<String> messages = new ArrayList<String>(){
		
		private static final long serialVersionUID = 8520836882750845542L;
		

		{
		add("message 1 : Hello ");
		add("message 2 : Big");
		add("message 3 : World");
		}
	};

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public List<String> getMessages() {
		return messages;
	}



	public void setMessages(List<String> messages) {
		this.messages = messages;
	}



	/**
	 * @param args
	 */
	public String toString() {
		return "User [jsonrpc = "  + jsonrpc + " name =" + name + "," +
				"messages=" + messages + "]" ;
	}

}
