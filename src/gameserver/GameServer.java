package gameserver;

import DataBase.DaoCallback;
import DataBase.DataBaseDao;
import DataBase.UserDB;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import response.LoggedInUser;

public class GameServer extends Application {

    static ServerSocket serversocket;

    public GameServer() {
        initializeServerSocket();
    }
    Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        root = new GameServerBase();
        Scene scene = new Scene(root, 1343, 858);
        stage.setScene(scene);
        stage.setResizable(false);
        DataBaseDao d = new DataBaseDao();
        LoggedInUser lr = new LoggedInUser();

        lr.setEmail("sasasasa@sda");
        lr.setUserName("mohaned");
        lr.setDraws(0);
        d.updateScore(lr, 0, new DaoCallback<Integer>() {
            @Override
            public void onSuccess(Integer results) {
                System.out.println(results);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throw new UnsupportedOperationException("Not supported yet."); 
            }
        });
        stage.show();

    }

    public static void main(String[] args) throws IOException {

        launch(args);

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        serversocket.close();
    }

    private void initializeServerSocket() {
        new Thread(() -> {
            try {
                serversocket = new ServerSocket(5000);

                while (!serversocket.isClosed()) {
                    System.out.println("running");
                    Socket socket = serversocket.accept();
                    if (socket.isConnected()) {
                        System.out.println("Client #" + (GameHandler.clients.size() + 1) + " has Connected...");
                    }
                    new GameHandler(socket);

                }
            } catch (IOException ex) {
                if (!serversocket.isClosed()) {
                    ex.printStackTrace();
                }
            }

        }).start();
        

    }

}
