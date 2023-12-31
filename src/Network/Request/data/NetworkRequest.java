package Network.Request.data;

public class NetworkRequest<T> {

    String ip;
    RequestType requestType;
    T requestData;

    public NetworkRequest() {
    }

    public T getRequestData() {
        return requestData;
    }

    public NetworkRequest(RequestType requestType, T requestData) {
        this.requestType = requestType;
        this.requestData = requestData;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }

    public enum RequestType {
        LOGIN,
        LOGOUT,
        REGISTER,
        AVAILABLE_PLAYERS;
    }

    @Override
    public String toString() {
        return "NetworkRequest{"
                + "ip='" + ip + '\''
                + ", requestType=" + requestType
                + ", requestData=" + requestData
                + '}';
    }
}
