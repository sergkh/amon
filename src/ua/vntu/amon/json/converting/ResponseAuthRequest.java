package ua.vntu.amon.json.converting;
public class ResponseAuthRequest {

	private String jsonrpc = "2.0";
	private String result ;
	private int id;	

	public ResponseAuthRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
