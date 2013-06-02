package ua.vntu.amon.json.entity;

public class BaseResult<ResultType> {
    private String jsonrpc;
    private ResultType result;
    private int id;

    public BaseResult() {}

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public ResultType getResult() {
        return result;
    }

    public void setResult(ResultType result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
