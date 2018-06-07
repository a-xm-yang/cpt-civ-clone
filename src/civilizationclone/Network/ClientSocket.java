package civilizationclone.Network;

import civilizationclone.GUI.ClientPane;
import civilizationclone.GUI.MultiplayerPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import javafx.application.Platform;

public class ClientSocket implements Runnable {

    private Socket socket;

    private PrintStream ps;
    private BufferedReader br;

    private String ip;
    private String localName;
    private String hostIp;

    private ClientPane pane;
    private CommandListener listener;

    public ClientSocket() {
        //for now
        listener = gameListener;

    }

    public boolean connect(String hostInfo, int port) {
        try {

            socket = new Socket(hostIp, port);
            ip = InetAddress.getLocalHost().getHostAddress();
            localName = InetAddress.getLocalHost().getHostName();
            hostIp = socket.getInetAddress().getHostAddress();

            ps = new PrintStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(this).start();

            return true;
        } catch (IOException ex) {
            System.out.println("Connection error!");
            return false;
        }
    }

    public void sendMessage(String s) {
        ps.println(s);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String s = br.readLine();
                System.out.println("Client received: " + s);
                if (s != null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.handle(s);
                        }
                    });
                } else {
                    System.out.println("Disconnected!");
                    return;
                }
            } catch (IOException ex) {
                System.out.println("Connection lost with server!");
                return;
            }
        }
    }

    private CommandListener gameListener = new CommandListener() {
        @Override
        public void handle(String message) {
            pane.receiveAction(message);
        }
    };

    public void setPane(ClientPane pane) {
        this.pane = pane;
    }

}
