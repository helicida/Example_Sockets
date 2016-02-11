package EscanerPuertos;

import java.net.Socket;

/**
 * Created by 46465442z on 04/02/16.
 */
public class EscanerPuertos {

    public static void main(String[]args){

        int pInicio = 1;  // Variable de inicio
        int pFin = 100;  // Variable fin

        while(pFin <= 65500){   // Vamos a ir escaneando puertos de 100 en 100 hasta llegar al 65500
            Scanner escaneadorPuertos = new Scanner(pInicio, pFin);
            pFin += 100;
            pInicio += 100;
        }
    }
}

/**
 * Created by 46465442z on 04/02/16.
 */
class Scanner {

    public Scanner(int inicio,int fin){ // Definimos nuestro EscanerPuertos.Scanner personalizado

        Socket socket1;  // Socket
        String ip = "localhost";    // Ip que vamos a escanear

        for(int iterador = inicio; iterador <= fin; iterador++){    // Que compruebe todos los puertos entre dos valores

            try{
                socket1 = new Socket(ip,iterador);  // creamos el socket con su ip y el puerto correspondiente

                System.out.println("El puerto " + iterador + " está abierto."); // Cuando lo encuentra abierto que imprima en pantalla
                socket1.close();

            }
            catch(Exception ex){}
        }
    }
}