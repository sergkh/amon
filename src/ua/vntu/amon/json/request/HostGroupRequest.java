package ua.vntu.amon.json.request;

import java.util.HashMap;

import java.util.Map;

public class HostGroupRequest {

	/*
	 * { "jsonrpc": "2.0", "method": "hostgroup.get", "params": { "output": "extend" , "sortfiels":"name"
	 * }, "auth": "13f28ca608a4b12c83a32d749229da71", "id": 2 }
	 */
	private String jsonrpc = "2.0";
	private String method = "hostgroup.get";// hostgroup.get
	private Map<String, String> params = new HashMap<String, String>();
	
	private String auth;
	private int id = 1;

	public HostGroupRequest(String outputting, String sortfilding) {
		params.put("output", outputting);
		params.put("sortfiels", sortfilding);
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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
