package DataBase;

import java.sql.ResultSet;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoggedInUser;
import response.NetworkResponse;

public interface UserDao {
    void getUserData(int Id , DaoCallback<ResultSet> callback);
    void getDataForLogin(LoginRequest lr, DaoCallback<LoggedInUser> callback);
    void rgisterUser(RegisterRequest rr,DaoCallback<Integer> callback);
    void updateScore(LoggedInUser user, int isWin , DaoCallback<Integer> callback);
    void getAllPlayer(DaoCallback<ResultSet> callback);
}
