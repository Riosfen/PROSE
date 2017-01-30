package soketUDP1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Adrián
 */
public class ServidorUDP {
    public static void main(String argv[]) throws Exception {   
        //Argumentos local
        int puertoLocal = 2000;

        //Argumentos del datatagrama desino
        InetAddress destino = InetAddress.getLocalHost();
        int puertoDestino = 4000; 
        int bytesRecibidos;
        byte [] mensaje = new byte[1024];     
        int tamaMensaje = mensaje.length;
        
        //ASOCIO SOCKET AL PUERTO 
        DatagramSocket servidorUDP = new DatagramSocket(puertoLocal);//puerto local
        //CONSTRUYO DATAGRAMA
        DatagramPacket reciboData = new DatagramPacket (mensaje, tamaMensaje);         
        //ESPERANDO DATAGRAMA
        System.out.println("Esperando Datagrama........");
        servidorUDP.receive(reciboData);//Se espera hasta recibir el datagrama
        bytesRecibidos = reciboData.getLength();//Obtengo número de bytes
        String datagrama = new String(reciboData.getData());//Obtengo String
        
        //MUESTRO INFORMACIÓN
        System.out.println("Número de bytes recibidos: "+bytesRecibidos);
        System.out.println("Contenido del datagrama: "+datagrama.trim());
        System.out.println("Puerto origen del mensaje: "+reciboData.getPort());
        System.out.println("IP de origen: "+reciboData.getAddress().getHostAddress());
        System.out.println("Pueto destino del mensaje: "+servidorUDP.getLocalPort());
        
        //CIERRE DEL SOCKET
        servidorUDP.close();        
    }
}
