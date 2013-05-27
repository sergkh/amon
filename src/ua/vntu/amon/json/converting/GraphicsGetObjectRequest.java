package ua.vntu.amon.json.converting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicsGetObjectRequest extends BaseRequest{

	
	private Map<String, ArrayList<String> > params = new HashMap<String, ArrayList<String>>();
	private String auth;
	private int id;
	
	public Map<String, ArrayList<String>> getParams() {
		return params;
	}

	public void setParams(Map<String, ArrayList<String>> params) {
		this.params = params;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GraphicsGetObjectRequest(ArrayList<String> host) {
		super("graph.getobjects");
		params.put("host", host);
		// TODO Auto-generated constructor stub
	}

}
