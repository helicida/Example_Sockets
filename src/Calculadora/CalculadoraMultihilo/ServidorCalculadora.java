package Calculadora.CalculadoraMultihilo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by sergi on 7/02/16.
 */
public class ServidorCalculadora {

    public static void main(String[] args) {

        // Mostramos un mensaje por pantalla
        System.out.println("Esperant instruccions");

        try {
            // Creamos nuestro socket
            ServerSocket servidorSocket = new ServerSocket();
            InetSocketAddress address = new InetSocketAddress("localhost",5555);

            // Acoplamos el InetSocketAdress al socket y los ponemos a escuchar
            servidorSocket.bind(address);
            Socket listener = servidorSocket.accept();
            InputStream input = listener.getInputStream();
            OutputStream output = listener.getOutputStream();

            // Array de bytes en el que almacenamos el mensaje
            byte[] mensaje = new byte[30];
            input.read(mensaje);

            System.out.println("\nS'ha rebut una operacio desde la IP: " + listener.getInetAddress().toString() + "/" + listener.getLocalPort());

           Integer contador = 0; // Creamos un contador con el que sabremos el tamaño del mensaje

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
            String opreacion = new String(mensajeConstruido);
            System.out.println("\n- Operació:  " + opreacion);

            // Enviamos la operación al cliente
            output.write(calcular(opreacion).getBytes());

            // String que contiene el resultado de la operación
            String resultado = calcular(opreacion);

            // Imprimimos el resultado de la operación en pantalla
            System.out.println("\n- Resultat de la operació: " + resultado);

            // Construimos el array que encajaremos en nuestro log
            String datosConexion = "\n\nDATA: " + new Date().toString() +
                    "\nIP: "+listener.getInetAddress().toString() + "/" + listener.getLocalPort() +
                    "\nOPERACIO: " + opreacion +
                    "\nRESULTAT: " + resultado;


            // Cerramos todas las conexiones
            listener.close();
            servidorSocket.close();
            input.close();
            output.close();

            // Escribimos en el log el mensaje
            escribirLog(datosConexion);
        }
        catch (IOException e){}
    }

    public static String calcular(String operacion){

        // Esto es auxiliar para hacer las operaciones de manera automatica
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        // Creamos un string con el texto que aparecerá en caso de error.
        String resultado = "La operació no és vàlida";

        try {
            // Intentamos calcular el resultado. En caso de ser invalido, el string se quedará con el valor que le dimos antes
            resultado = engine.eval(operacion).toString();
        }

        catch (ScriptException e) {}

        return resultado;   // Devolvemos un string con el resultado
    }

    public static void escribirLog(String arrayAEscribir){

        try {

            // Ruta del archivo en el que escribiremos
            File log = new File("/home/sergi/IdeaProjects/Example_Sockets/src/Calculadora/registre.txt");

            // Creamos un buffered writer que escribirá en el archivo info. True significa que no sobreescribirá el contenido
            BufferedWriter bufferedWr = new BufferedWriter(new FileWriter(log, true));

            // Si el archivo no existe, lo crea
            if (!log.exists()) {
                log.createNewFile();
            }

            // Escribimos en el archivo
            bufferedWr.write(arrayAEscribir);

            // Cerramos el buffered writer
            bufferedWr.close();

        } catch (IOException e) {}
    }
}