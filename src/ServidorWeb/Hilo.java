package ServidorWeb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 46465442z on 08/03/16.
 */

public class Hilo extends Thread{

    Socket socket;  // Socket que recibimos para saber a que direccion apuntar el hilo

    // String con la ruta y Path, este ultimo es necesario para la funcion readAllBytes
    String ruta = "/home/46465442z/IdeaProjects/Example_Sockets/src/ServidorWeb/pruebaServeis.html";
    Path pathRuta = Paths.get(ruta);

    // Constructor con del hilo con el socket que se pasa por referencia
    public Hilo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try  {
            // Declaramos y acoplamos nuestros InputStream y OutputStream al socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Array de bytes en el que almacenamos el codigo HTML
            byte[] html = new byte[1000000000];
            inputStream.read(html);

            // Pasamos los datos que extraemos del archivo a traves del outputStream
            outputStream.write(Files.readAllBytes(pathRuta));

            // Cerramos los buffers y el socket
            inputStream.close();
            outputStream.close();
            socket.close();

        } catch (IOException a){}
    }
}