/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fernando
 */
public class TcpServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int connections = 0;

        System.out.println("Servidor abierto en el puerto " + 6000);

        ServerSocket serverSocket = null;

        try {
            // Creamos el servidor en el puerto 6000
            serverSocket = new ServerSocket(6000);

            // tiempo maximo de conexion 300s 
            serverSocket.setSoTimeout(300000);

            //Maximo de conexiones que admite el sockets
            while (true) {
                if (connections == 2) {
                    serverSocket.close();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("No más conexiones activas");
                    exit(1);
                }

                connections++;
                // Esperando conexions
                Socket socket = serverSocket.accept();

                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                // Recibimos el mensaje del cliente
                String message = reader.readLine();
                System.out.println("SERVER: Recibido " + message
                        + " desde " + socket.getInetAddress().toString()
                        + ":" + socket.getPort());

                message = message.toLowerCase();

                // Enviando msj
                writer.println(message);
                System.out.println("SERVER: Enviando " + message
                        + " to " + socket.getInetAddress().toString()
                        + ":" + socket.getPort());

                //Cerramos los streams
                input.close();
                output.close();
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
