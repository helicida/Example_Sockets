package ServidorWeb;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by 46465442z on 08/03/16.
 */
public class Hilo extends Thread {

    Socket socket;  // Socket que recibimos para saber a que direccion apuntar el hilo

    // String con la ruta y Path, este ultimo es necesario para la funcion readAllBytes
    String ruta = "/home/46465442z/IdeaProjects/Example_Sockets/src/ServidorWeb/pruebaServeis.html";
    // String ruta = "/pruebaServeis.html"; // Ruta para Dionís
    Path pathRuta = Paths.get(ruta);
    Date date = new Date(); // Fecha del dia para el log

    // Constructor con del hilo con el socket que se pasa por referencia
    public Hilo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try  {
            // Entrada en el log
            escribirLog(date + "LOG: Esperant conexio");

            // Declaramos y acoplamos nuestros InputStream y OutputStream al socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Array de bytes en el que almacenamos la peticion HTML
            byte[] mensaje = new byte[30];
            inputStream.read(mensaje);

            int contador = 0; // Creamos un contador con el que sabremos el tamaño del mensaje

            // Usamos este for para contar cuantos bytes necesitamos para nuestro mensaje
            for (int iterador = 0; iterador < mensaje.length; iterador++){
                if (mensaje[iterador]==0) {
                    break;
                }
                else {
                    contador++;
                }
            }

            // Creamos el array de bytes con el tamaño necesario
            byte[] mensajeConstruido = new byte[contador];

            // Pasamos nuetro mensaje al array del tamaño preciso
            for (int iterador = 0; iterador < mensajeConstruido.length; iterador++){
                mensajeConstruido[iterador] = mensaje[iterador];
            }

            // Lo pasamos a string
            String peticion = new String(mensajeConstruido);

            // Si la peticion que choca contra nuestro servidor no es GET, lo escribimos en el log
            if (!peticion.contains("GET")){
                escribirLog(date + "LOG: Advertencia la conexio entrant no es un GET");
            }

            // Array de bytes en el que almacenamos el codigo HTML
            byte[] html = new byte[100000000];
            inputStream.read(html);

            // Entrada en el log
            escribirLog(date + "LOG: Processant peticio");

            // Pasamos los datos que extraemos del archivo y Socket a traves del outputStream
            String direccio = "<!DOCTYPE html PUBLIC> <h2> " + socket.getInetAddress().toString() + "</h2>";
            outputStream.write(direccio.getBytes());
            outputStream.write(Files.readAllBytes(pathRuta));

            // Cerramos los buffers y el socket
            inputStream.close();
            outputStream.close();
            socket.close();

        } catch (IOException a){
            // Entrada en el log
            escribirLog(date + "LOG: S'ha produit una excepció de tipus:" + a.toString());
        }
    }

    public static void escribirLog(String arrayAEscribir){

        try {
            // Ruta del archivo en el que escribiremos
            File log = new File("/home/46465442z/IdeaProjects/Example_Sockets/src/ServidorWeb/log.txt");

            // Creamos un buffered writer que escribirá en el archivo info. True significa que no sobreescribirá el contenido
            BufferedWriter bufferedWr = new BufferedWriter(new FileWriter(log, true));

            // Si el archivo no existe, lo crea
            if (!log.exists()) {
                log.createNewFile();
            }

            // Escribimos en el archivo
            bufferedWr.write(arrayAEscribir);
            bufferedWr.newLine();

            // Cerramos el buffered writer
            bufferedWr.close();

        } catch (IOException e) {}
    }
}