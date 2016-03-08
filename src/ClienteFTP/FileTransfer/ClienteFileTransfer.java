package ClienteFTP.FileTransfer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ClienteFileTransfer {
    public final static int PUERTO = 5555;      // you may change this
    public final static String SERVER = "127.0.0.1";  // localhost
    public final static String RUTA = "c:/temp/source-downloaded.pdf";  // poner la ruta del archivo
    // different name because i don't want to
    // overwrite the one used by server...

    public final static int FILE_SIZE = 10000000; // file size temporary hard coded
    // should bigger than the file to be downloaded

    public static void main (String [] args ) throws IOException {
        int bytesRead;
        int actual = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, PUERTO);
            System.out.println("Conectando...");

            // Recibiendo archivo
            byte [] byteFichero  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(RUTA);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(byteFichero,0,byteFichero.length);
            actual = bytesRead;

            do {
                bytesRead =
                        is.read(byteFichero, actual, (byteFichero.length-actual));
                if(bytesRead >= 0) actual += bytesRead;
            } while(bytesRead > -1);

            bos.write(byteFichero, 0 , actual);
            bos.flush();
            System.out.println("Recibido " + RUTA
                    + " ...Descargado (" + actual + " bytes read)");
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }
}
