package civilizationclone.Network;

import civilizationclone.GUI.HostPane;
import civilizationclone.GUI.MultiplayerPane;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Thread joiningListener;
    private CommandListener listener;

    private ArrayList<ClientHandler> clientList;

    private HostPane pane;

    public Server() {

        clientList = new ArrayList<>();

        try {
            serverSocket = new ServerSocket(50001);
            System.out.println("Server constructed! " + InetAddress.getLocalHost());
        } catch (IOException ex) {
        }

        joiningListener = new Thread(this);
        joiningListener.start();
        

        
        //for now replace later
        
        listener = gameListener;
    }

    public synchronized void sendToAll(String s) {
        for (ClientHandler c : clientList) {
            c.sendMessage(s);
        }
    }

    @Override
    public void run() {
        while (clientList.size() < 5) {
            try {
                Socket s = serverSocket.accept();
                System.out.println("New client connected!");
                clientList.add(new ClientHandler(s, this));
            } catch (IOException ex) {
                System.out.println("Accepting client error");
            }

        }
    }

    private CommandListener gameListener = new CommandListener() {
        @Override
        public void handle(String message) {
            pane.receiveAction(message);
        }
    };

    private CommandListener menuLisetner;

    //GETTERS & SETTERS
    //<editor-fold>
    public void setPane(HostPane pane) {
        this.pane = pane;
    }
    
    
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
