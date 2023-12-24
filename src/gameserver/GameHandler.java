package gameserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

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
                if (!str.isEmpty()) {
                    System.out.println("Recieved From User " + str);
                    sendMessageToAll(str);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    void sendMessageToAll(String msg) {

        for (GameHandler s : clients) {
            s.mouth.println("Sent From server " + msg);
        }
    }
}
