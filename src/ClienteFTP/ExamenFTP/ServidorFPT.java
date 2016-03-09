package ClienteFTP.ExamenFTP;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorFPT {

    public static void main(String[] args) {

        try {
            //El constructor del serverSocket es diferente del cliente Tiene metodos que el cliente no tiene
            System.out.println("...creando servidor");
            ServerSocket serverSocket = new ServerSocket();

            //El servidor escucha en la direccion que le digamos
            InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
            System.out.println("...realizando bind");
            serverSocket.bind(addr);    //bind = vincular

            System.out.println("...escuchando");

            //Aqui es donde el servidor se quedara escuchando asta que reciba una conexion
            while (true) {

                Socket socketEscucha = serverSocket.accept();

                HiloServerFTP th = new HiloServerFTP(socketEscucha);
                th.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}