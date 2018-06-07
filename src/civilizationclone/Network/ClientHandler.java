package civilizationclone.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javafx.application.Platform;

public class ClientHandler implements Runnable {

    private Server server;
    private Socket socket;

    private PrintStream ps;
    private BufferedReader br;

    private String ip;
    private String localName;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;

        try {
            ps = new PrintStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
        }

        ip = socket.getInetAddress().getHostAddress();
        localName = socket.getInetAddress().getHostName();

        new Thread(this).start();

        System.out.println("A new client has connected! " + ip);
    }

    public void sendMessage(String s) {
        ps.println(s);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = br.readLine();

                System.out.println("Host received: " + msg);
                if (msg != null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            server.getListener().handle(msg);
                        }
                    });
                } else {
                    socket.close();
                    server.getClientList().remove(this);
                    return;
                }
            } catch (IOException ex) {
                System.out.println("Connection lost with " + localName + "!");
                try {
                    socket.close();
                } catch (IOException ex1) {
                }
                server.getClientList().remove(this);
                return;
            }
        }
    }

}
