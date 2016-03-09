package ClienteFTP.ExamenFTP;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClienteFTP {

    public final static int FILE_SIZE = 6022386; //El tamaño provisional del archivo que recibiremos, debe ser mayor al file

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        Socket clienteSocket = new Socket();
        System.out.println("Creado el socket cliente");

        System.out.println("Estableciendo conexion");
        InetSocketAddress addr = new InetSocketAddress("localhost", 5555);

        try {

            //Este comando conecta el socket con la direccion y el puerto especificado
            clienteSocket.connect(addr);

            //Los comandos siguientes por donde va a escuchar y por donde va a hablar
            InputStream is = clienteSocket.getInputStream();
            OutputStream os = clienteSocket.getOutputStream();

            //Recibir el listado de ficheros que viene del servidor
            byte[] respuesta = new byte[1024];
            is.read(respuesta);
            System.out.println(new String(respuesta));


            System.out.println("Que fichero quieres descargar? ");
            String fichero = teclado.nextLine();
            System.out.println("Opcion enviada...");
            //Ponemos el mensaje en el canal, RECORDAR que hay que ponerlo en bits
            os.write(fichero.getBytes());

            File file = new File("C:/USERS/MANUEL/DESKTOP/RECIBIDO");
            FileOutputStream fileInputStream =  new FileOutputStream(file);
            BufferedOutputStream bufferedInputStream = new BufferedOutputStream(fileInputStream);

            //Recibir el fichero que viene del servidor
            byte [] mybytearray  = new byte [FILE_SIZE];
            int bytesRead;
            int current = 0;
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;
            do {
                bytesRead =
                        is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bufferedInputStream.write(mybytearray, 0 , current);
            bufferedInputStream.flush();

            System.out.println("File " + file
                            + " downloaded (" + current + " bytes read)");

            System.out.println("Completado");

            is.close();
            os.close();
            fileInputStream.close();
            bufferedInputStream.close();
            clienteSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}