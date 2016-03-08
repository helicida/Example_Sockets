package ServidorWeb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 46465442z on 08/03/16.
 */
public class Controller {

    public static void main(String[] args)  {

        try  {
            // Creamos nuestro ServerSocket y le damos una dirección
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress addrs = new InetSocketAddress("0.0.0.0", 5555);
            serverSocket.bind(addrs);

            // Hilo
            Socket socket = serverSocket.accept();
            Hilo thread = new Hilo(socket);
            thread.start();
        }
        catch (IOException e) {

        }
    }
}
