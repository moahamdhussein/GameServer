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
    Socket socket;
    GameHandler gameHandler;
    Thread thread;

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
        d.updateScore(lr, 1, new DaoCallback<Integer>() {
            @Override
            public void onSuccess(Integer results) {
                System.out.println(results);
            }

            @Override
            public void onFailure(Throwable throwable) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
       

    }

    private void initializeServerSocket() {
        thread = new Thread(() -> {
            try {
                serversocket = new ServerSocket(5050);

                while (true) {
                    socket = serversocket.accept();
                    if (socket.isConnected()) {
                        System.out.println("Client #" + (GameHandler.clients.size() + 1) + " has Connected...");
                    }
                    gameHandler = new GameHandler(socket);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });
        thread.start();

    }

}
