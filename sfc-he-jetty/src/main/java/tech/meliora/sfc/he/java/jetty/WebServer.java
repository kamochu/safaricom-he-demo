package tech.meliora.sfc.he.java.jetty;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.time.LocalDateTime;

public class WebServer {


    private Server server;

    private int port;


    public WebServer(int port) throws Exception {
        this.port = port;
        this.server = new Server();

        server = new Server();

        //http connector - https can be done later
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});

        ServletHandler handler = new ServletHandler();

        server.setHandler(handler);

        //add servlet
        handler.addServletWithMapping(SfcTestServlet.class, "/chk/");

        server.start();

        System.out.println(LocalDateTime.now() + "|port: " + port + "|started jetty web server");

    }
}
