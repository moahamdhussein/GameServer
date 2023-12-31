package Network.Response.data;

import Network.Response.NetworkResponse;

public class RegisterResposne extends NetworkResponse {

    private int id;
    private String userName;
    private String email;
    private Integer stauts;
    private Integer wins;
    private Integer losses;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    private Integer draws;

    public RegisterResposne(String ip) {
        this.ip = ip;
        this.setMode(ResponseMode.REGISTER);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStauts() {
        return stauts;
    }

    public void setStauts(Integer stauts) {
        this.stauts = stauts;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    @Override
    public String toString() {
        return "RegisterResposne{"
                + "id=" + id
                + "Ip=" + ip
                + ", userName='" + userName + '\''
                + ", email='" + email + '\''
                + ", status=" + getResponseStatus()
                + ", wins=" + wins
                + ", losses=" + losses
                + ", draws=" + draws
                + '}';
    }

}
