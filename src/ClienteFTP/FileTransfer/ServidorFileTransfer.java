package ClienteFTP.FileTransfer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ServidorFileTransfer {

    public final static int PUERTO = 5555;  
    public final static String RUTA = "c:/temp/source.pdf";  // you may change this

    public static void main (String [] args ) throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;

        boolean escuchando = true;

        try {
            servsock = new ServerSocket(PUERTO);
            while (escuchando) {
                System.out.println("Escuchando...");
                try {
                    sock = servsock.accept();
                    System.out.println("Conexion aceptada: " + sock);
                    // send file
                    File myFile = new File (RUTA);
                    byte [] byteFichero  = new byte [(int)myFile.length()];
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    bis.read(byteFichero,0,byteFichero.length);
                    os = sock.getOutputStream();
                    System.out.println("Enviando " + RUTA + "(" + byteFichero.length + " bytes)");
                    os.write(byteFichero,0,byteFichero.length);
                    os.flush();
                    System.out.println("Enviado.");
                }
                finally {
                    if (bis != null) bis.close();
                    if (os != null) os.close();
                    if (sock!=null) sock.close();
                }
            }
        }
        finally {
            if (servsock != null) servsock.close();
        }
    }

}
