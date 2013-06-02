package ua.vntu.amon.json.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraphicsGetObjectRequest extends BaseRequest{

	
	private Map<String, List<String>> params = new HashMap<String, List<String>>();
	private String auth;
	private int id;
	
	public Map<String, List<String>> getParams() {
		return params;
	}

	public void setParams(Map<String, List<String>> params) {
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

	public GraphicsGetObjectRequest(List<String> host) {
		super("graph.getobjects");
		params.put("host", host);
		// TODO Auto-generated constructor stub
	}

}
