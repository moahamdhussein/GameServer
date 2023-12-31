package request;

public class RequestGame extends NetworkRequest{
    String ip , userName;

    public RequestGame() {
        this.requestType = RequestType.GAME;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
}
