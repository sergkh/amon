package ua.vntu.amon.json.request;

import java.util.HashMap;
import java.util.Map;


public class HostRequest extends BaseRequest{

private Map<String, String> params = new HashMap<String, String>();
	
	private String auth;
	private int id = 1;
	
	public HostRequest(String output) {
		super("host.get");
		params.put("output", output);		
		// TODO Auto-generated constructor stub
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
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

}
