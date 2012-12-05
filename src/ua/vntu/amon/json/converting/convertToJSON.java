package ua.vntu.amon.json.converting;

import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class convertToJSON {
		
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		AuthRequest user = new AuthRequest(null, null);
		ObjectMapper mapper  = new ObjectMapper ();
		
		try {
			// convert User java-objects to JSON format
			mapper.writeValue(new File ("d:\\user.json"), user );
			
			//System.out.println(mapper.writeValueAsString(user));
			System.out.println(mapper.defaultPrettyPrintingWriter().writeValueAsString(user));
			
		}
		
		catch (JsonGenerationException e){
			e.printStackTrace();
			
		}
		
		catch (JsonMappingException e){
			e.printStackTrace();
		}
		
		catch(IOException e){
			e.printStackTrace();
		}
	
	

	}

}
