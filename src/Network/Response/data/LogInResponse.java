package Network.Response.data;

import Network.Response.NetworkResponse;

public class LogInResponse extends NetworkResponse {

    private int id, wins, loses, draws, status;
    private String userName, email;

    public LogInResponse(String ip) {
        this.ip = ip;
        setMode(ResponseMode.LOGIN);
    }

    public LogInResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    @Override
    public String toString() {
        return String.format(
                "PlayerInfo{id=%d, userName='%s', email='%s', wins=%d, loses=%d, draws=%d, status=%d}",
                id, userName, email, wins, loses, draws, status
        );
    }

}
