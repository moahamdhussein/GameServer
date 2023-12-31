package Network.Request;

import Network.Request.data.LoginRequest;
import Network.Request.data.NetworkRequest;
import Network.Request.data.RegisterRequest;
import com.google.gson.Gson;

public class RequestHandler {

    public static String getJsonRequest(NetworkRequest request) {

        return new Gson().toJson(request);
    }

    public static NetworkRequest getRequestFromJsom(String json) {
        return new Gson().fromJson(json, NetworkRequest.class);
    }

    public static RegisterRequest getRegisterRequestObj(String requestData) {
        return new Gson().fromJson(requestData, RegisterRequest.class);
    }

    public static LoginRequest getLogInRequestObj(String jsonRequest) {
        return new Gson().fromJson(jsonRequest, LoginRequest.class);
    }

}
