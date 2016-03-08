package ClienteFTP;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by sergi on 8/03/16.
 */
public class Cliente {

    private static Scanner teclat = new Scanner(System.in);

    public static void main(String[] args) {

        String RUTA = "";

        System.out.println("Escoge uno");
        System.out.println("1 - /home/sergi/Descargas/ServidorWeb/Controller.java");
        System.out.println("2 - /home/sergi/Descargas/ServidorWeb/Hilo.java");
        System.out.println("3 - /home/sergi/Descargas/ServidorWeb/pruebaServeis.html");

        String option = teclat.nextLine();

        try {
            Socket clienteSocket = new Socket();
            InetSocketAddress address = new InetSocketAddress("localhost", 5555);
            clienteSocket.connect(address);

            System.out.println("\nENVIADA PETICIÓN PARA DESCARGAR EL ARCHIVO " + option);

            byte[] bytesFichero = new byte[1000000];
            InputStream inputStream = clienteSocket.getInputStream();

            FileOutputStream fos = new FileOutputStream(RUTA);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            int tamanyoFichero = inputStream.read(bytesFichero, 0, bytesFichero.length);
            int variableAuxiliar = tamanyoFichero;

            do {
                tamanyoFichero = inputStream.read(bytesFichero, variableAuxiliar, (bytesFichero.length - variableAuxiliar));
                if(tamanyoFichero >= 0) variableAuxiliar += tamanyoFichero;
            } while(tamanyoFichero > -1);

            bos.write(bytesFichero, 0 , variableAuxiliar);
            bos.flush();
            System.out.println("Recibido " + RUTA
                    + " ...Descargado (" + variableAuxiliar + " bytes read)");

            clienteSocket.close();
            inputStream.close();
        } catch (IOException e) {}
    }
}
