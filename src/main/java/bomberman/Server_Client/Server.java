package bomberman.Server_Client;

import bomberman.GlobalVariable.LANVariables;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(LANVariables.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}