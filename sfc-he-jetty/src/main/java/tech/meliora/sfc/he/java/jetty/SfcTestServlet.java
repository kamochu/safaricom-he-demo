package tech.meliora.sfc.he.java.jetty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class SfcTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String clientIpAddress = request.getRemoteAddr();
        int clientPort = request.getRemotePort();

        long startTime = System.currentTimeMillis();

        System.out.println(LocalDateTime.now() + "|ip: " + clientIpAddress + "|port: " + clientPort
                + "|received a connection");

        Enumeration<String> headers = request.getHeaderNames();

        Map<String, String> headersMap = new HashMap<>();


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>");
        while (headers.hasMoreElements()) {
            String headerKey = headers.nextElement();
            String header = request.getHeader(headerKey);
            headersMap.put(headerKey, header);

            System.out.println(LocalDateTime.now() + "|ip: " + clientIpAddress + "|port: " + clientPort
                    + "|key: " + headerKey + "|header: " + header + "|received a connection");

            stringBuilder.append("<p>").append(headerKey).append(":").append(header).append("</p>");
        }
        stringBuilder.append("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(stringBuilder.toString());


        long endTime = System.currentTimeMillis();

        System.out.println(LocalDateTime.now() + "|ip: " + clientIpAddress + "|port: " + clientPort
                + "|processing_time: " + (endTime - startTime) + "|served the connection");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Get operation not supported. Use POST</h1>");

    }
}
