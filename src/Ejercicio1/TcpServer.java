/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio1;

import java.io.IOException;
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

        System.out.println("Servidor abierto en el puerto" + 6000);

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

                
                System.out.println("SERVER: Conexión abierta " + socket.getInetAddress().toString()
                        + ":" + socket.getPort() + " Puerto Local:" + socket.getLocalPort());
            }
        } catch (SocketTimeoutException e) {
            System.err.println("No recibidas en 300s");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
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
    

