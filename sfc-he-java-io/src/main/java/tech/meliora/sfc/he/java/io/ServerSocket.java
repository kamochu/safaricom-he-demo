package tech.meliora.sfc.he.java.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

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
                buf=ByteBuffer.allocate(4096);
                System.out.println("system|port: " + tcpPort + " connected.");


                Socket socket = socketChannel.socket();
                System.out.println("system|port: " + tcpPort + "|socket: " + socket);


                int clientPort = socket.getPort();
                String clientIpAddress = socket.getInetAddress().getHostAddress();

                String outputContent = "";
                String content = null;
                System.out.println("system|port: " + tcpPort + "|socket: " + socket + "|clientIp: " + clientIpAddress);


                Thread.sleep(100);
                int bytesRead = socketChannel.read(buf); //read into buffer.
                System.out.println("system|port: " + tcpPort + "|socket: " + socket + "|clientIp: " + clientIpAddress + "|byteSize: " + bytesRead);
                while (bytesRead != -1) {

                    buf.flip();  //make buffer ready for read

                    if (content == null) {
                        content = new String(buf.array()); //new
                        outputContent = content;
                        System.out.println("new content: " + content);
                    } else {
                        content = content + new String(buf.array()); //append
                        outputContent += content;
                        System.out.println("adding content: " + content);

                    }
                    buf.clear();
                    bytesRead = -1;

                    System.out.println("clear the buffer. " + buf.array().length + " size: " + bytesRead);
                }


                System.out.println("system|port: " + tcpPort + "|socket: " + socket + "|clientIp: " + clientIpAddress + "|byteSize: " + bytesRead + " done reading.");


                String messageToWrite ="";
                if (content!=null){
                    messageToWrite = "HTTP/1.1 200 OK\r\n"
                            + "Pragma: no-cache\r\n"
                            + "Connection: keep-alive\r\n"
                            + "content-length: " + content.length() + "\r\n"
                            + "Content-Type: application/json;charset=UTF-8\r\n"
                            + "\r\n"
                            + content + "\r\n";
                }else {
                    content = "failed";
                    messageToWrite = "HTTP/1.1 301 OK\r\n"
                            + "Pragma: no-cache\r\n"
                            + "Connection: keep-alive\r\n"
                            + "content-length: " + content.length() + "\r\n"
                            + "Content-Type: application/json;charset=UTF-8\r\n"
                            + "\r\n"
                            + content + "\r\n";
                }


                System.out.println("messageTowrite: " + messageToWrite);
                ByteBuffer buf1 = ByteBuffer.allocate(5120);
                buf1.clear();
                buf1.put(messageToWrite.getBytes());
                buf1.flip();
                System.out.println("done flipping");

                while (buf1.hasRemaining()) {

                    int count = socketChannel.write(buf1);

                }
                System.out.println("done writing");

                socketChannel.close();
                System.out.println("system|port: " + tcpPort + "|socket: " + socket + "|clientIp: " + clientIpAddress + "|byteSize: " + bytesRead + " closed connection.");

            } catch (IOException | InterruptedException e) {
                System.out.println("system|port: " + tcpPort + "|error accepting a socket connection" + e.getMessage());
            }

        }

    }
}
