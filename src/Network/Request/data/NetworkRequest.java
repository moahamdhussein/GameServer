package Network.Request.data;

public class NetworkRequest<T> {

    String ip;
    RequestType requestType;

    public NetworkRequest() {
    }

    public NetworkRequest(RequestType requestType, T requestData) {
        this.requestType = requestType;
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

    public enum RequestType {
        LOGIN("LOGIN"),
        REGISTER("REGISTER"),
        AVAILABLE_PLAYERS("AVAILABLE_PLAYERS"),
        NONE("NONE");

        String type = "";

        private RequestType(String s) {
            this.type = s;
        }

        public static RequestType fromString(String typeString) {
            for (RequestType type : RequestType.values()) {
                System.err.println("After removing \"  :" + typeString.replace("\"", ""));
                if (type.type.equals(typeString.replace("\"", ""))) {
                    System.out.println("Type  = " + type + "\nvalue = " + typeString);
                    return type;
                }
            }
            return NONE;
        }
    }

    @Override
    public String toString() {
        return "NetworkRequest{"
                + "ip='" + ip + '\''
                + ", requestType=" + requestType
                + '}';
    }
}
