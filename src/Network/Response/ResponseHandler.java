package Network.Response;

import Network.Response.data.LogInResponse;
import Network.Response.data.RegisterResposne;
import com.google.gson.Gson;

public class ResponseHandler {

    public static NetworkResponse handleResponse(String jsonString) {

        return new Gson().fromJson(jsonString, NetworkResponse.class);
    }

    public static String getRegisterResponseJson(RegisterResposne registerResposne) {
        String json = new Gson().toJson(registerResposne);
        System.out.println("Register Response Json " + json);
        return json;
    }

    public static RegisterResposne getRegisterResponseObj(String json) {

        return new Gson().fromJson(json, RegisterResposne.class);
    }

    public static String getLoginResponseJson(LogInResponse logInResponse) {
        return new Gson().toJson(logInResponse);
    }

    public static LogInResponse getLoginResponseObj(String json) {
        return new Gson().fromJson(json, LogInResponse.class);
    }

}
