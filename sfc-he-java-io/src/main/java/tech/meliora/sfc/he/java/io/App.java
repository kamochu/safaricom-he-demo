package tech.meliora.sfc.he.java.io;

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
        try {
            socketAccepter = new ServerSocket(8080);
            socketAccepter.start();

        }catch (Exception ex){
            System.out.println("Error "+ ex.getMessage());
        }

    }
}
