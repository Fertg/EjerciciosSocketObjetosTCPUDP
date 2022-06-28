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
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 *
 * @author Fernando
 */
public class Cliient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      
        Socket socket = null;
        try {
            // Obtenemos la ip
            InetAddress serverAddress = InetAddress.getByName("localhost");
            // Obtenemos el pueto
            int serverPort = 6000;
            // Inicializamos el objeto pasandole N=5
            
            System.out.println("Introduzca un número: ");
            Scanner sc = new Scanner(System.in);
            int numero=sc.nextInt();
            Numeros numeros = new Numeros(numero);
            // Creamos el socket y establecemos la conexion
            socket = new Socket(serverAddress, serverPort);
            // Tiempo máximo de conexión 300 secs
            socket.setSoTimeout(300000);
            System.out.println("CLIENTE: Conexión establecida con "
                    + serverAddress.toString() + " - Puerto Remoto: " + socket.getPort()
                    + " - Puerto Local: " + socket.getLocalPort());
            // Obtenemos el e output stream desde el socket.
            OutputStream sOutput = socket.getOutputStream();
            //Objeto de salida
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(sOutput);

            System.out.println("CLIENTE: Enviando  numero " + numeros.getNumero()
                    + " para " + socket.getInetAddress().toString()
                    + ":" + socket.getPort());

            // Enviado numero
            objectOutputStream.writeObject(numeros);

            InputStream sInput = socket.getInputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(sInput);

            Numeros response = (Numeros) objectInputStream.readObject();

            //// Recibiendo del server los parametros
            int number = response.getNumero();
            long square = response.getCuadrado();
            long cube = response.getCubo();

            System.out.println("CLIENTE: Recibido numero " + number
                    + ", cuadrado " + square + ", cubo " + cube
                    + " desde " + socket.getInetAddress().toString()
                    + ":" + socket.getPort());

            // Cerramos
            sOutput.close();
            sInput.close();
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
