/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio1;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 *
 * @author Fernando
 */
public class TcpClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        Socket socket = null;
        try {
            // Obtenemos la ip
            InetAddress serverAddress = InetAddress.getByName("localhost");
            // Obtenemos el pueto
            int serverPort =6000;
            // Creamos el socket y establecemos la conexion
            socket = new Socket(serverAddress, serverPort);
            // Tiempo máximo de conexión 300 secs
            socket.setSoTimeout(300000);

            System.out.println("CLIENTE: Conexión establecida: "
                    + serverAddress.toString() + " - Puerto Remoto: " + socket.getPort()
                    + " Puerto Local: " + socket.getLocalPort());
        } catch (SocketTimeoutException e) {
            System.err.println("No se recibe nada en 300 segundos");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    
    }
    
}
