/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameServer extends Application {
    static ServerSocket serversocket;
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = new GameServerBase();
        Scene scene = new Scene(root, 1343, 858);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
        serversocket = new ServerSocket(5000);
        while (true) {
            Socket socket = serversocket.accept();
            if (socket.isConnected()) {
                System.out.println(socket.getLocalPort());
                new GameHandler(socket);
            }
        }
    }

}
