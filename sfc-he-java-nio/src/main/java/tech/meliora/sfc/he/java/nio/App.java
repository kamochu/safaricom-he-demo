package tech.meliora.sfc.he.java.nio;

import java.time.LocalDateTime;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Starting server.");
        ServerSocket socketAccepter;
        int port = 8080;

        //get the port from -Dport=8080

        if (System.getProperty("port") != null) {
            try {
                port = Integer.parseInt(System.getProperty("port"));
            } catch (NumberFormatException ex) {
                System.err.println(LocalDateTime.now() + "|port: "
                        + System.getProperty("port") + "|unable to parse int - " + ex.getMessage());
            }
        }

        try {
              System.out.println(LocalDateTime.now() + "|port: " + port + "|initialized port ");

            System.out.println(LocalDateTime.now() + "|port: " + port + "|starting web server");
            socketAccepter = new ServerSocket(port);
            socketAccepter.start();
            System.out.println(LocalDateTime.now() + "|port: " + port + "|started web server");

        }catch (Exception ex){
            System.out.println("Error "+ ex.getMessage());
        }




    }
}
