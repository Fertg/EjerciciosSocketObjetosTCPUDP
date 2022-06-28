/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio5;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 *
 * @author Fernando
 */
public class UdpServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        DatagramSocket socket = null;
        byte[] buf = new byte[256];

        try {
            // Create a server socket
            socket = new DatagramSocket(6000);

            // tiempo maximo de conexion 300s 
            socket.setSoTimeout(300000);

            while (true) {
                // Preparo el datagrama para la recepcion
                DatagramPacket request = new DatagramPacket(buf, buf.length);

                // Recibo el mensaje
                socket.receive(request);

                String message = new String(request.getData(), 0, request.getLength());

                System.out.println("SERVIDOR: Recibe " + message + " desde "
                        + request.getAddress().toString() + ":" + request.getPort());

                // contador de vocales
                int count = 0;

                for (int i = 0; i < message.length(); i++) {
                    char ch = message.charAt(i);
                    if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                        count++;
                    }
                }

                // Preparo datagrama para enviar respuesta
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                String data = "Tu texto tiene  " + count + " vocales";
                buf = data.getBytes();

                // Enviamos  respuestas
                DatagramPacket response = new DatagramPacket(buf, buf.length, clientAddress, clientPort);
                socket.send(response);

                System.out.println("SERVER: Sending "
                        + new String(response.getData(), 0, response.getLength())
                        + " to " + clientAddress.toString() + ":"
                        + clientPort);
            }

            // Uncomment next catch clause after implementing the logic
        } catch (SocketTimeoutException e) {
            System.err.println("No se recibe nada en 300 segundos");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the socket
            socket.close();
        }
    }

}
