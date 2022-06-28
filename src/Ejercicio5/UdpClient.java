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
public class UdpClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DatagramSocket sDatagram = null;
        try {
            
            
            sDatagram = new DatagramSocket();
            // Establecemos 300seg
            sDatagram.setSoTimeout(300000);
            /// Obtenemos la ip
            InetAddress serverAddress = InetAddress.getByName("localhost");
            // Obtenemos el pueto
            int serverPort = 6000;
            String message = "Hola";
            // Preparamos el  datagrama
            DatagramPacket dgramSent = new DatagramPacket(message.getBytes(),
                    message.getBytes().length, serverAddress, serverPort);
            // Enviamos el datagrama
            sDatagram.send(dgramSent);
            System.out.println("CLIENTE: Enviando "
                    + new String(dgramSent.getData()) + " para "
                    + dgramSent.getAddress().toString() + ":"
                    + dgramSent.getPort());
            // Preparamos el  datagrama reception
            byte array[] = new byte[1024];
            DatagramPacket dgramRec = new DatagramPacket(array, array.length);
            // Recibimos el mensaje
            sDatagram.receive(dgramRec);
            System.out.println("CLIENTE: Recibido  "
                    + new String(dgramRec.getData(), 0, dgramRec.getLength())
                    + " desde " + dgramRec.getAddress().toString() + ":"
                    + dgramRec.getPort());
       } catch (SocketTimeoutException e) {
            System.err.println("No se recibe nada en 300 segundos");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Cerramos el  socket del datagrama
            sDatagram.close();
        }
    }
    
}
