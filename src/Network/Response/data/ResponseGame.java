package response;

public class ResponseGame extends NetworkResponse{
    String userName , ip;


    public ResponseGame() {
        this.responseType = ResponseType.GAME;
    }
    
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }   
}
