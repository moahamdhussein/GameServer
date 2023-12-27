/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 *
 * @author esraa
 */
public class RequestHandler {
   static Gson gson = new Gson();
    public static Object handleRequest(String rawRequest) {
            NetworkRequest request = gson.fromJson(rawRequest, NetworkRequest.class);
            switch(request.getRequestType()){
                case REGISTER:
                   return handleRegisterRequest(request);
                 case LOGIN:
                    return handleRegisterRequest(request);
                default:
                    return null;
            }
}

    private static RegisterRequest handleRegisterRequest(NetworkRequest request) {
        return gson.fromJson(gson.toJson(request.getRequestData()), RegisterRequest.class);
    }
            
        
}
