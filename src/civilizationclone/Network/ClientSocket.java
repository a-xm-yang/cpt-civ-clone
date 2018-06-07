package civilizationclone.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocket implements Runnable {

    private Socket socket;

    private PrintStream ps;
    private BufferedReader br;

    private String ip;
    private String localName;
    private String hostIp;

    private CommandListener listener;

    public boolean connect(String hostInfo, int port) {
        try {

            socket = new Socket(hostIp, port);
            ip = InetAddress.getLocalHost().getHostAddress();
            localName = InetAddress.getLocalHost().getHostName();
            hostIp = socket.getInetAddress().getHostAddress();

            ps = new PrintStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            return true;
        } catch (IOException ex) {
            System.out.println("Connection error!");
            return false;
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String s = br.readLine();
                if (s != null) {
                    listener.handle(s);
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

}
