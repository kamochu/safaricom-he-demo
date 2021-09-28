package tech.meliora.sfc.he.java.jetty;

import java.time.LocalDateTime;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
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

        System.out.println(LocalDateTime.now() + "|port: " + port + "|initialized port ");

        System.out.println(LocalDateTime.now() + "|port: " + port + "|starting web server");
        WebServer webServer = new WebServer(port);
        System.out.println(LocalDateTime.now() + "|port: " + port + "|started web server");

    }
}
