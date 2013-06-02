package ua.vntu.amon.json.request;

public class BaseRequest {
    private final String jsonrpc = "2.0";
    private final String method;

    public BaseRequest(String method) {
        this.method = method;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public String getMethod() {
        return method;
    }
}
