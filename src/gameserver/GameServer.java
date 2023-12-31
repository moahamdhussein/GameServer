package gameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameServer extends Application {

    static ServerSocket serversocket;
    private ArrayList<String> ips = new ArrayList<>();

    public GameServer() {
        initializeServerSocket();
    }

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

    }

    private void initializeServerSocket() {
        new Thread(() -> {
            try {
                serversocket = new ServerSocket(5000);

                while (true) {
                    Socket socket = serversocket.accept();
                    if (socket.isConnected() && !(ips.contains(socket.getInetAddress().getHostAddress()))) {
                        ips.add(socket.getInetAddress().getHostAddress());
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
