package ClienteFTP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by sergi on 8/03/16.
 */
public class Hilo {

    Socket socket;  // Socket que recibimos para saber a que direccion apuntar el hilo

    // String con la ruta y Path, este ultimo es necesario para la funcion readAllBytes
     String ruta = "/home/46465442z/IdeaProjects/Example_Sockets/src/ServidorWeb/pruebaServeis.html";
     Path pathRuta = Paths.get(ruta);

    // Constructor con del hilo con el socket que se pasa por referencia
    public Hilo(Socket socket) {
        this.socket = socket;
    }

    public void run()  {

        try {
            // Declaramos y acoplamos nuestros InputStream y OutputStream al socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Array de bytes en el que almacenamos el mensaje
            byte[] mensaje = new byte[1000000000];
            inputStream.read(mensaje);

            int contador = 0;

            for (contador = 0; contador < mensaje.length; contador++) {

                if (mensaje[contador] == 0) {
                    break;
                }
                else {
                    contador++;
                }
            }

            // Creamos el array de bytes con el tamaño necesario
            byte[] mensajeLimpio = new byte[contador];

            // Pasamos nuetro mensaje al array del tamaño preciso
            for (int iterador = 0; iterador < mensajeLimpio.length; iterador++) {
                mensajeLimpio[iterador] = mensaje[iterador];
            }

            // Lo pasamos a string
            String msg = new String(mensajeLimpio);
            System.out.println("\nDEVOLVIENDO ARCHIVO -> " + msg);

                switch(msg) {
                    case "1": {
                        pathRuta = Paths.get("/home/sergi/IdeaProjects/Example_Sockets/src/ClienteFTP/Prueba archivos/Controller.java");
                        break;
                    }
                    case "2": {
                        pathRuta = Paths.get("/home/sergi/IdeaProjects/Example_Sockets/src/ClienteFTP/Prueba archivos/Hilo.java");
                        break;
                    }
                    case "3": {
                        pathRuta = Paths.get("/home/sergi/IdeaProjects/Example_Sockets/src/ClienteFTP/Prueba archivos/pruebaServeis.html");
                        break;
                    }
                }

            outputStream.write(Files.readAllBytes(pathRuta));

            // Cerramos todas las conexiones
            inputStream.close();
            outputStream.close();
            socket.close();
        }
        catch (IOException one){}
    }
}