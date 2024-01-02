package Network.Response.data;

import Network.Response.NetworkResponse;

public class LogInResponse extends NetworkResponse {

    private int id, wins, loses, draws, status;
    private String userName, email;

    public LogInResponse(String ip) {
        this.ip = ip;
        setMode(ResponseMode.LOGIN);
    }

    public LogInResponse(String[] values) {
        this.id = Integer.parseInt(values[0]);
        this.userName = values[1].replace("\"", "");
        this.email = values[2].replace("\"", "");
        this.wins = Integer.parseInt(values[3].replace("\"", ""));
        this.loses = Integer.parseInt(values[4].replace("\"", ""));
        this.draws = Integer.parseInt(values[5].replace("\"", ""));
        this.status = Integer.parseInt(values[6].replace("\"", ""));
        setIp(values[12].replace("\"", ""));
        setResponseStatus(ResponseStatus.fromString(values[13].replace("\"", "")));
        setMode(ResponseMode.fromString(values[14].replace("\"", "")));
    }

    public String[] toArray() {
        String[] values = new String[15];
        for (int i = 0; i < 15; i++) {
            values[i] = "lkdsmfks ";
        }
        values[0] = String.valueOf(id);
        values[1] = userName;
        values[2] = email;
        values[3] = String.valueOf(wins);
        values[4] = String.valueOf(loses);
        values[5] = String.valueOf(draws);
        values[6] = String.valueOf(status);
        values[12] = getIp();
        values[13] = String.valueOf(getResponseStatus());
        values[14] = String.valueOf(getMode());
        return values;
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
                "PlayerInfo{id=%d, userName='%s', email='%s', wins=%d, loses=%d, draws=%d, status=%d , mode = %s, responseStatus=%s, ip=%s}",
                id, userName, email, wins, loses, draws, status, getMode(), getResponseStatus(), ip
        );
    }

}
