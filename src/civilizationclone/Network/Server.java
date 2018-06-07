package civilizationclone.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Thread joiningListener;
    private CommandListener listener;

    private ArrayList<ClientHandler> clientList;

    public Server() {

        clientList = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(50001);
            System.out.println("Server constructed! IP: " + serverSocket.getInetAddress().getHostAddress() + " Port: " + serverSocket.getLocalPort());
        } catch (IOException ex) {
            System.out.println("Server construction failed!");
        }

        joiningListener = new Thread(this);
        joiningListener.run();
    }
    
    public synchronized void sendToAll(String s){
        for (ClientHandler c: clientList){
            c.sendMessage(s);
        }
    }

    @Override
    public void run() {
        while (clientList.size() < 5) {
            try {
                Socket s = serverSocket.accept();
                clientList.add(new ClientHandler(s, this));
            } catch (IOException ex) {
                System.out.println("Accepting client error");
            }

        }
    }

    //GETTERS & SETTERS
    //<editor-fold>
    public CommandListener getListener() {
        return listener;
    }

    public Thread getJoiningListener() {
        return joiningListener;
    }

    public ArrayList<ClientHandler> getClientList() {
        return clientList;
    }

    //</editor-fold>
}
