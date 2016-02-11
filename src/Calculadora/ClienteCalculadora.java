package Calculadora;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by sergi on 7/02/16.
 */
public class ClienteCalculadora {

    private static Scanner teclat = new Scanner(System.in);

    public static void main(String[] args) {

        // Imprimimos un mensaje
        System.out.println("Introduce una operación");
            String operacionAEnviar = teclat.nextLine();

        try {
            // Creamos nuestro socket
            Socket clienteSocket = new Socket();
            InetSocketAddress address = new InetSocketAddress("localhost", 5555);

            // Conectamos el InetSocketAdress al socket
            clienteSocket.connect(address);
            InputStream input = clienteSocket.getInputStream();
            OutputStream output = clienteSocket.getOutputStream();

            // Traducimos y enviamos la operación como bytes
            output.write(operacionAEnviar.getBytes());

            Integer contador = 0;   // Creamos un contador con el que sabremos el tamaño del mensaje

            byte[] mensaje = new byte[10];  // Array de bytes en el que reconstruiremos el mensaje

            // Recibimos la operación
            input.read(mensaje);

            // Usamos este for para contar cuantos bytes necesitamos para nuestro mensaje
            for (int iterador = 0; iterador < mensaje.length; iterador++)  {
                if (mensaje[iterador] == 0) {
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

            // Lo pasamos a string y lo imprimimos en pantalla
            String resultado = new String(mensajeLimpio);
            System.out.println("\nEl resultado es: " + resultado);

            // Cerramos todas las conexiones
            clienteSocket.close();
            input.close();
            output.close();
        }
        catch (IOException e) {}
    }
}