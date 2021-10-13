package tech.meliora.sfc.he.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ServerSocket implements Runnable {

    Thread socketAcceptorThread;
    private int tcpPort = 0;
    private ServerSocketChannel serverSocket = null;

    public ServerSocket(int tcpPort) {
        this.tcpPort = tcpPort;
        System.out.println("system|port: " + tcpPort + "|starting a socket acceptor");

    }

    public void start() throws IOException {
        this.serverSocket = ServerSocketChannel.open();
        System.out.println("system|port: " + tcpPort + "|opened the server socket channel");
        this.serverSocket.bind(new InetSocketAddress(tcpPort));
        System.out.println("system|port: " + tcpPort + "|bound to the port");

        //start the acceptor thread
        this.socketAcceptorThread = new Thread(this, "socket-acceptor-thread");
        this.socketAcceptorThread.start();
        System.out.println("system|port: " + tcpPort + "|started the sokect acceptor thread");
    }


    @Override
    public void run() {
        ByteBuffer buf = null;

        while (true) {
            try {

                System.out.println("system|port: " + tcpPort + "|waiting for client connections");
                SocketChannel socketChannel = this.serverSocket.accept();
                SocketProcessor serverSocket = new SocketProcessor(tcpPort, socketChannel);
                serverSocket.start();

            } catch (IOException e) {
                System.out.println("system|port: " + tcpPort + "|error accepting a socket connection" + e.getMessage());
            }

        }

    }
}
