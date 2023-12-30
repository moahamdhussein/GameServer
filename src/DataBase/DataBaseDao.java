package DataBase;

import java.sql.PreparedStatement; 
import java.sql.ResultSet;
import java.sql.SQLException;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoggedInUser;


public class DataBaseDao implements UserDao {

    private UserDB db = null;

    public DataBaseDao() {
        db = UserDB.getInstance();
        db.connect();
    }

    @Override
    public void getUserData(DaoCallback<ResultSet> callback) {
        try {
            String query = "select * from player";
            PreparedStatement pst = db.con.prepareStatement(query);
            callback.onSuccess(pst.executeQuery());
            pst.close();
        } catch (SQLException ex) {
            System.out.println("Player not found please register");
            callback.onFailure(ex);
        }
    }

    @Override
    public void registerUser(RegisterRequest rr, DaoCallback<LoggedInUser> callback) {
        try {
            String query = "Insert into player (email,username,password) Values(?,?,?)";
            PreparedStatement pst = db.con.prepareStatement(query);
            pst.setString(1, rr.getEmail());
            pst.setString(2, rr.getUserName());
            pst.setString(3, rr.getPassword());
            int executeUpdate = pst.executeUpdate();
            if(executeUpdate > 0){
              callback.onSuccess(convertFromRegisterRequestToLoggedInUser(rr));
            }
            pst.close();
        } catch (SQLException ex) {
            System.out.println("User already in defined please LogIn");
            callback.onFailure(ex);
        }
    }
    @Override
    public void updateScore(LoggedInUser user, int isWin, DaoCallback<Integer> callback) {
        int win = 0, lose = 0, draw = 0;
        switch (isWin) {
            case 1:
                win = 1;
                break;
            case -1:
                lose = 1;
                break;
            default:
                draw = 1;
                break;
        }
        try {
            String query = "update player set wins = wins + ?  , loses = loses + ? , draws = draws + ? where userName = ?";
            PreparedStatement pst = db.con.prepareStatement(query);
            pst.setInt(1, win);
            pst.setInt(2, lose);
            pst.setInt(3, draw);
            pst.setString(4, user.getUserName());
            callback.onSuccess(pst.executeUpdate());
            pst.close();
        } catch (SQLException ex) {
            System.out.println("Can't add new player");
            callback.onFailure(ex);
        }
    }

    @Override
    public void getDataForLogin(LoginRequest lr, DaoCallback<LoggedInUser> callback) {
        try {
            String query = "select * from player where email = ? and password = ?";
            PreparedStatement pst = db.con.prepareStatement(query);
            pst.setString(1, lr.getEmail());
            pst.setString(2, lr.getPassword());
            callback.onSuccess(convetResultSetToLoggedInUser(pst.executeQuery()));
            pst.close();
        } catch (SQLException ex) {
            System.out.println("Player not found please register");
            callback.onFailure(ex);
        }
    }
    
    @Override
    public void getAllPlayer(DaoCallback<ResultSet> callback){
        
    }
    
    private LoggedInUser convetResultSetToLoggedInUser(ResultSet rs){
        try {
            LoggedInUser user = new LoggedInUser();
            rs.next();
            user.setId(rs.getInt(1));
            user.setEmail(rs.getString(2));
            user.setUserName(rs.getString(3));
            user.setWins(rs.getInt(5));
            user.setLoses(rs.getInt(6));
            user.setDraws(rs.getInt(7));
            user.setStatus(rs.getInt(8));
            return user;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    private LoggedInUser convertFromRegisterRequestToLoggedInUser(RegisterRequest rr){
        LoggedInUser user  = new LoggedInUser();
        user.setUserName(rr.getUserName());
        user.setEmail(rr.getEmail());
        user.setDraws(0);
        user.setLoses(0);
        user.setWins(0);
        user.setStatus(1);
        return user;
    }
}
