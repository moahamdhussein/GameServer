package gameserver;

import api.ApiHandler;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameHandler extends Thread {

    BufferedReader ear;
    private PrintStream mouth;
    private String ip = "";
    public static Vector<GameHandler> clients = new Vector<GameHandler>();

    public GameHandler(Socket cs) {
        try {
            ear = new BufferedReader(new InputStreamReader(new DataInputStream(cs.getInputStream())));
            mouth = new PrintStream(cs.getOutputStream());
            ip = cs.getLocalAddress().getHostAddress();
            System.out.println(ip);
            clients.add(this);
            this.start();
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
                    System.out.println("Request + " + str);
                    ApiHandler.getInstance().addRequest(str);
                }
            } catch (IOException ex) {
                System.out.println("Client Disconnected");
                onClose();
                clients.remove(this);
                this.stop();
            }
        }
    }

    public void onClose() {
        try {
            ear.close();
            mouth.close();
        } catch (IOException ex) {
            Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getIp() {
        return ip;
    }

    public PrintStream getSender() {
        return mouth;
    }

}
