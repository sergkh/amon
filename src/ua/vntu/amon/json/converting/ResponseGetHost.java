package ua.vntu.amon.json.converting;


public class ResponseGetHost {
	private String jsonrpc = "2.0";
	private Result result;
	private int id;

	public ResponseGetHost() {
		// TODO Auto-generated constructor stub
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	public void setJsonrpc(String jsonrpc) {
		this.jsonrpc = jsonrpc;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
