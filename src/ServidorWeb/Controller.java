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
            ServerSocket servidorSocket = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress("0.0.0.0", 5555);

            // Acoplamos el InetSocketAdress al socket y los ponemos a escuchar
            servidorSocket.bind(address);

            // Bucle en el que se arranca un hilo por cada petición de acceso a la web
            // NOTA: MUY MUY MUY recomendable no hacer más de una conexión a la vez
            // Ya que el buffer es de un tamaño pequeño y se puede sobrecargar, provocando
            // excepciones (java.lang.OutOfMemoryError) y fugas de memoria)
            while (true){
                Socket listener = servidorSocket.accept();
                Hilo hilo = new Hilo(listener);
                hilo.start();
            }
        }
        catch (IOException e) {}
    }
}
