package ClienteFTP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;


/**
 * Created by sergi on 8/03/16.
 */
public class Server {

    public static void main(String[] args) {

        try  {
            // Creamos nuestro ServerSocket y le damos una dirección
            ServerSocket servidorSocket = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress("localhost",5555);

            // Acoplamos el InetSocketAdress al socket y los ponemos a escuchar
            servidorSocket.bind(address);

            while (true) {
                Socket listener = servidorSocket.accept();
                Hilo hilo = new Hilo(listener);
                hilo.run();
            }
        }
        catch (IOException e){}
    }
}
