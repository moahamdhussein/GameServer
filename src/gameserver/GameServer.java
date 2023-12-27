package gameserver;

import DataBase.DaoCallback;
import DataBase.DataBaseDao;
import DataBase.UserDB;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameServer extends Application {

    static ServerSocket serversocket;

    public GameServer() {
        initializeServerSocket();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root =new GameServerBase();
        Scene scene = new Scene(root,1343,858);
        stage.setScene(scene);
        stage.setResizable(false);
        DataBaseDao d =new DataBaseDao();
        d.getUserData(new DaoCallback<ResultSet>() {
            @Override
            public void onSuccess(ResultSet results) {
                try {
                    results.next();
                    System.out.println(results.getString(2));//To change body of generated methods, choose Tools | Templates.
                } catch (SQLException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("faill"); //To change body of generated methods, choose Tools | Templates.
            }
        });
        stage.show();
        
    }

    public static void main(String[] args) throws IOException {
        
        launch(args);
        

    }

    private void initializeServerSocket() {
        new Thread(() -> {
            try {
                serversocket = new ServerSocket(5000);

                while (true) {
                    Socket socket = serversocket.accept();
                    if (socket.isConnected()) {
                        System.out.println("Client #" + (GameHandler.clients.size() + 1) + " has Connected...");
                    }
                    new GameHandler(socket);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

}
