package ua.vntu.amon.json.converting;

import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonToJava {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		 
		try {
	 
			// read from file, convert it to user class
			AuthRequest auth = mapper.readValue(new File("d:\\user.json"), AuthRequest.class);
	 
			// display to console
			System.out.println(auth);
	 
		} catch (JsonGenerationException e) {
	 
			e.printStackTrace();
	 
		} catch (JsonMappingException e) {
	 
			e.printStackTrace();
	 
		} catch (IOException e) {
	 
			e.printStackTrace();
	 
		}
	 
	  }
	 

	}


