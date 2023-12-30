package gameserver;


import request.RegisterRequest;

import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import request.RequestHandler;
import response.NetworkResponse;
import response.RegisterResponse;
import response.ResponseHandler;

public class GameHandler extends Thread {

    BufferedReader ear;
    PrintStream mouth;
    static Vector<GameHandler> clients = new Vector<GameHandler>();

    public GameHandler(Socket cs) {
        try {
            ear = new BufferedReader(new InputStreamReader(new DataInputStream(cs.getInputStream())));
            mouth = new PrintStream(cs.getOutputStream());
            GameHandler.clients.add(this);
            start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                
                String str = ear.readLine();
                System.out.println("Recieved From User " + str);
                if (!str.isEmpty()) {
                    System.out.println("Recieved From User " + str);
                    //sendMessageToAll(str);
//                    handleClientRequest(str);
                }
            } catch (IOException ex) {
                System.out.println("input closed");
                this.stop();
            }
        }
    }

    void sendMessageToAll(String msg) {

        for (GameHandler s : clients) {
            s.mouth.println("Sent From server " + msg);
        }
    }

    void handleClientRequest(String rawRequest) {
       
          try {
                String rawResponse="";
                 RegisterRequest data =(RegisterRequest)RequestHandler.handleRequest(rawRequest);
                System.out.println(data.getUserName()); 
                int id = insertIntoDB(data);
                if (id > 0) {
                    NetworkResponse response = new NetworkResponse<RegisterResponse>();
                    response.setStatus(NetworkResponse.ResponseStatus.SUCCESS);
                    RegisterResponse resgisetResponse=new RegisterResponse();
                    resgisetResponse.setId(id);
                    resgisetResponse.setUserName(data.getUserName());
                    resgisetResponse.setEmail(data.getEmail());
                    response.setResponseInfo(resgisetResponse);
                    rawResponse=ResponseHandler.createJsonResponse(response);
                }else{
                
                   NetworkResponse response = new NetworkResponse<RegisterResponse>();
                    response.setStatus(NetworkResponse.ResponseStatus.FAILURE);
                    response.setResponseInfo(null);
                    rawResponse=ResponseHandler.createJsonResponse(response);
                }
            mouth.println(rawResponse);
        } catch (JsonSyntaxException ex) {
        }
    
}
    int insertIntoDB(RegisterRequest data) {
        return 1;
    }
    
    public  void onClose(){
        try {
            ear.close();
            mouth.close();
        } catch (IOException ex) {
            Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
