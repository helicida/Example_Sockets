package Calculadora.CalculadoraMultihilo;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by sergi on 7/02/16.
 */
public class ServidorCalculadora{

    public static void main(String[] args){

        // Mostramos un mensaje por pantalla
        System.out.println("Esperant instruccions");

        try {
            // Creamos nuestro socket
            ServerSocket servidorSocket = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress("localhost", 5555);

            // Acoplamos el InetSocketAdress al socket y los ponemos a escuchar
            servidorSocket.bind(address);

            // Cada vez que entre alguna operación arrancamos un hilo
            while (true){
                Socket listener = servidorSocket.accept();
                Hilo hilo = new Hilo(listener);
                hilo.run();
            }
        }
        catch (IOException e){}
    }
}