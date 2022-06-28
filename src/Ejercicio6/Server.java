/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio6;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import static java.lang.System.exit;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fernando
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //apuntes del moodle. Revisar.
        
        
    System.out.println("Abriendo server desde el puerto " + 6000);

        ServerSocket serverSocket = null;

        try {
             // Creamos el servidor en el puerto 6000
            serverSocket = new ServerSocket(6000);

            // tiempo maximo de conexion 300s 
            serverSocket.setSoTimeout(300000);

            while (true) {
                // Esperando conexiones y aceptamos
                Socket socket = serverSocket.accept();

                // Obtenemos el stream imput de la conexion del socket
                InputStream sInput = socket.getInputStream();
               
                ObjectInputStream objectInputStream = new ObjectInputStream(sInput);

                // Obtenemos  el output del socket
                OutputStream sOutput = socket.getOutputStream();
     
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(sOutput);

                // Recibo el objeto desde el cliente
                Numeros numeros = (Numeros) objectInputStream.readObject();
                int number = numeros.getNumero();

                System.out.println("SERVER: Recibido numero " + number
                        + " desde " + socket.getInetAddress().toString()
                        + ":" + socket.getPort());

                if (number == 0) {
                    serverSocket.close();

                    numeros.setCuadrado(0);
                    numeros.setCubo(0);

                    // Enviando respuesta al cliente
                    objectOutputStream.writeObject(numeros);

                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Recibido  0. Cerrando servidor...");
                    exit(1);
                }

                // Calculamos resultados
                long square = (long) number * number;
                numeros.setCuadrado(square);
                long cube = (long) number * number * number;
                numeros.setCubo(cube);

             
                objectOutputStream.writeObject(numeros);

                System.out.println("SERVIDOR: Enviando numbero " + number
                        + ", cuadrado " + square + ", cubo " + cube
                        + " desde " + socket.getInetAddress().toString() +
                        ":" + socket.getPort());

                // Cerramos stream, importanteee
                sInput.close();
                sOutput.close();
            }
         } catch (SocketTimeoutException e) {
            System.err.println("No se recibe nada en 300 segundos");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            //Cerramos el socket
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
