package gameserver;

import Network.Request.data.RegisterRequest;
import api.ApiHandler;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

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

      if (!isExist(cs)) {
        GameHandler.clients.add(this);
      }
      this.start();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private boolean isExist(Socket s) {
    for (GameHandler gh : clients) {
      if (s.getInetAddress().getHostAddress().equals(gh.ip)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void run() {
    while (!exit) {
      try {

        String str = ear.readLine();
        if (!str.isEmpty()) {
          ApiHandler.getInstance().addRequest(str);
        }
      } catch (IOException ex) {
        System.out.println("Client Disconnected");
        clients.remove(this);
        this.stop();
      }
    }
  }

  void sendMessageToAll(String msg) {
    for (GameHandler s : clients) {

      s.mouth.println("Sent From server " + msg);
    }
  }

  int insertIntoDB(RegisterRequest data) {
    return 1;
  }

  public void onClose() {
    try {

      ear.close();
      mouth.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String getIp() {
    return ip;
  }

  public PrintStream getSender() {
    return mouth;
  }

}
