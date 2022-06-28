/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio7;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
        
    System.out.println("Abriendo UDPserver desde el puerto " + 6000);
     
        DatagramSocket socket = null;
        //Bytes que acepta para la conversión
        byte[] buf = new byte[1024];


        try {
            // Creamos el servidor del datagramSocket
            socket = new DatagramSocket(6000);

          // tiempo maximo de conexion 300s 
            socket.setSoTimeout(300000);

            while (true) {
                  // Peparamos el datagrama
                DatagramPacket request = new DatagramPacket(buf, buf.length);

                // Recibimos
                socket.receive(request);

                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(buf));
                Persona receivedPerson = (Persona) iStream.readObject();

                String name = receivedPerson.getNombre();
                String surname = receivedPerson.getApellido();

                System.out.println("SERVER: Recibida persona:  " + name + " " + surname + " from " +
                        request.getAddress().toString() + ":" + request.getPort());

                // Preparamos la respuesta al cliente
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();

                char c;
                // Revertimos el nombre
                StringBuilder newName = new StringBuilder();
                for (int i = 0; i < name.length(); i++) {
                    c = name.charAt(i); 
                    newName.insert(0, c); 
                }

                   // Revertimos el apellido
                StringBuilder newSurname = new StringBuilder();
                for (int i = 0; i < surname.length(); i++) {
                    c = surname.charAt(i); 
                    newSurname.insert(0, c); 
                }

                Persona newPersona = new Persona(newName.toString(), newSurname.toString());

                // Clase serializable introducimos los bytes en array
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(newPersona);

                byte[] serializedObject = bStream.toByteArray();

                // Enviamos respuesta
                DatagramPacket response = new DatagramPacket(serializedObject, serializedObject.length, clientAddress, clientPort);
                socket.send(response);

                System.out.println("SERVIDOR: Enviando  " + newPersona.getNombre() + " "
                        + newPersona.getApellido() + " para " + clientAddress.toString() + ":"
                        + clientPort);

                iStream.close();
                oo.close();
            }
       } catch (SocketTimeoutException e) {
            System.err.println("No se recibe nada en 300 segundos");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            //Cerramos el socket
            socket.close();
        }
    }

}
