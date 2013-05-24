package ua.vntu.amon.json.converting;
/**
 * 
 * @author Yaric
 *
 */

import java.util.*;

public class AuthRequest extends BaseRequest {

	private String title ="AuthRequest";
	private Map<String, String> params = new HashMap<String, String>();
	private String auth;
	private int id;
	
	public AuthRequest(String login, String pass) {
        super("user.login");
		params.put("user", login);
		params.put("password", pass);
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
	
	public String getTitle() {
		return title;
	}	
}
