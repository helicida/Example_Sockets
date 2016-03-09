package ClienteFTP.ExamenFTP;

import java.io.*;
import java.net.Socket;

public class HiloServerFTP extends Thread {

    Socket socketEscuchado;

    public HiloServerFTP(Socket s) {
        socketEscuchado = s;
    }


    @Override
    public void run() {
        try {
            //Cuando el servidor recibe una llamada
            System.out.println("...se ha recibido una llamada");

            InputStream is = socketEscuchado.getInputStream();
            OutputStream os = socketEscuchado.getOutputStream();

            String todo = "";
            File directorio = new File ("C:/USERS/MANUEL/DESKTOP/PRUEBA");
            File[] arrayFiles = directorio.listFiles();
            for (int i =0;i<arrayFiles.length;i++){
                String archivo = (i) + ": " + arrayFiles[i].getName() + "\n";
                todo += archivo;
            }
            //Enviamos el listado de Ficheros que tenemos en el directorio
            os.write(todo.getBytes());

            //Recibimos el mensaje con la opcion elegida
            byte[] mensaje = new byte[25];
            is.read(mensaje);
            //Lo pasamos a String para interpretarlo
            String cadena = new String(mensaje);
            Integer numeroFichero = Integer.parseInt(cadena.trim());
            System.out.println("El cliente ha elegido la opcion " + numeroFichero);

            File file = null;
            for (int i =0;i<arrayFiles.length;i++){
                if(numeroFichero == i) {
                     file = arrayFiles[i];
                }
            }

            FileInputStream fileOutputStream = new FileInputStream(file);
            BufferedInputStream bufferedOutputStream = new BufferedInputStream(fileOutputStream);
            byte [] mybytearray  = new byte [(int)file.length()];

            bufferedOutputStream.read(mybytearray,0,mybytearray.length);
            System.out.println("Enviando " + file + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            System.out.println("Enviado.");

            System.out.println("Cerrando");

            is.close();
            os.close();
            fileOutputStream.close();
            bufferedOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

