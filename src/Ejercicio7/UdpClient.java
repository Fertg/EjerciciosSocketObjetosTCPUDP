/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio7;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
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

        Persona persona = new Persona("Fernando","Teodoro");

        try {
      
            sDatagram = new DatagramSocket();
            // Tiempo máximo de conexión 300 secs
            sDatagram.setSoTimeout(300000);
             // Obtenemos la ip
            InetAddress serverAddress = InetAddress.getByName("localhost");
            /// Obtenemos el puerto
            int serverPort = 6000;

            // Serializamos byte a byet
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(bStream);
            oo.writeObject(persona);

            byte[] serializedObject = bStream.toByteArray();

            // preparamos el datagram
            DatagramPacket dgramSent = new DatagramPacket(serializedObject,
                    serializedObject.length, serverAddress, serverPort);
            // Enviamos el datagram
            sDatagram.send(dgramSent);

            System.out.println("CLIENTE: Enviando "
                    + persona.getNombre() + " " + persona.getApellido() + " para  "
                    + dgramSent.getAddress().toString() + ":"
                    + dgramSent.getPort());


            // Preparando el datagrama 
            byte response[] = new byte[1024];
            DatagramPacket dgramRec = new DatagramPacket(response, response.length);

            // Recibimos
            sDatagram.receive(dgramRec);

            ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(response));
            Persona newPersona = (Persona) iStream.readObject();

            String name = newPersona.getNombre();
            String surname = newPersona.getApellido();

            System.out.println("CLIENTE: Recibido  "
                    + name + " " + surname + " desde  "
                    + dgramRec.getAddress().toString() + ":" + dgramRec.getPort());

            iStream.close();
            oo.close();
      } catch (SocketTimeoutException e) {
            System.err.println("No se recibe nada en 300 segundos");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            //Cerramos el socket
            sDatagram.close();
        }
    }
    
}
