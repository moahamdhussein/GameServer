package DataBase;

import Network.Response.data.LogInResponse;
import Network.Request.data.LoginRequest;
import Network.Request.data.RegisterRequest;
import java.sql.ResultSet;

public interface UserDao {

    void getUserData(DaoCallback<ResultSet> callback);

    void getDataForLogin(LoginRequest lr, DaoCallback<LogInResponse> callback);

    void registerUser(RegisterRequest rr, DaoCallback<LogInResponse> callback);

    void updateScore(LogInResponse user, int isWin, DaoCallback<Integer> callback);

    void getAllPlayer(DaoCallback<ResultSet> callback);
}
