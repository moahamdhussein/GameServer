package gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameServer extends Application {

    static ServerSocket serversocket;
    Socket socket;
    GameHandler gameHandler;
    Thread thread;
    String[] randomValues = {
        "42", // id
        "JohnDoe", // userName
        "john.doe@example.com",// email
        "25", // wins
        "10", // loses
        "5", // draws
        "1", // status
        "", "", "", "", "",
        "192.168.1.100", // ip
        "success", // responseStatus
        "REGISTER" // mode
    };

    public GameServer() {
        initializeServerSocket();

//        LoginRequest lr = new LoginRequest("ali", "xxxxx");
//        String json = new Gson().toJson(lr.toArray());
//        System.out.println("Json : " + json);
//        System.out.println("Obj : " + new LogInResponse(randomValues));
//        System.out.println("Obj after Json : " + new LoginRequest(json));
    }
    Parent root;

    @Override
    public void start(Stage stage) throws Exception {
        root = new GameServerBase();
        Scene scene = new Scene(root, 1343, 858);
        stage.setScene(scene);
        stage.setResizable(false);
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
                serversocket = new ServerSocket(5000);

                while (true) {
                    Socket socket = serversocket.accept();
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
