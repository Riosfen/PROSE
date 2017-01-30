package soketUDP1;

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Adrián
 */
public class ClienteUDP {
    public static void main(String argv[]) throws Exception {        
        //Argumentos local
        int puertoLocal = 4000;

        //Argumentos del datatagrama desino
        InetAddress destino = InetAddress.getLocalHost();
        int puertoDestino = 2000; 
        byte [] mensaje = new byte[1024];        
        String saludo = "Este es el mensaje que envía el cliente al servidor...";
        mensaje = saludo.getBytes(); // Codigifico a String
        int tamaMensaje = mensaje.length;
        
        //CONSTRUCCIÓN DATAGRAMA A ENVIAR
        DatagramPacket envioData = new DatagramPacket (mensaje, tamaMensaje, destino, puertoDestino); 
        //Creación del socket
        DatagramSocket clienteUDP = new DatagramSocket(puertoLocal);//puerto local
        //Muestro información de envío
        System.out.println("Enviando Datagrama de longitud: "+tamaMensaje);
        System.out.println("Host destino: "+destino.getHostName());
        System.out.println("IP destino: "+destino.getHostAddress());
        System.out.println("Puerto local del socket: "+clienteUDP.getLocalPort());
        System.out.println("Puerto al que se envía: "+envioData.getPort());
        
        //ENVIO DE DATAGRAMA
        clienteUDP.send(envioData);
        //Cierre del socket
        clienteUDP.close();
    }
}
