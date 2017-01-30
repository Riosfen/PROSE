package soketUDP2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author Adrián
 */
public class ServidorUDP {
    public static void main(String argv[]) throws Exception {        
        //Parámetros
        int puertoOrigen = 4000;//Puerto por el que escucha
        byte [] enviados = new byte [1024];
        byte [] recibidos = new byte [1024];
        int tamEnviados = enviados.length;
        int tamRecibidos = recibidos.length;
        String mensaje;
        //int puertoLocal = 2000;     
                 
        //ESTABLEZCO ESCUCHA:           
        DatagramSocket servidorUDP = new DatagramSocket(puertoOrigen);//Sin establecer puerto
        
        while(true){
            //RECEPCION DATAGRAMA DEL CLIENTE
            System.out.println("Esperando datagrama.....");
            DatagramPacket reciboData = new DatagramPacket(recibidos,tamRecibidos);
            servidorUDP.receive(reciboData);
            mensaje = new String(reciboData.getData());
            
            //DIRECCION ORIGEN
            InetAddress IPOrigen = reciboData.getAddress();
            int puertoDestino = reciboData.getPort();
            System.out.println("\tProcedente de: "+IPOrigen+" : "+puertoDestino);
            System.out.println("\tDatos: "+mensaje.trim());
            
            //CONVERTIR CADENA A MAYUSCULA
            String textoMensajeMayus = mensaje.trim().toUpperCase();
            enviados =textoMensajeMayus.getBytes();
            
            //ENVIO DATAGRAMA AL CLIENTE
            DatagramPacket envioData = new DatagramPacket(enviados,enviados.length,IPOrigen, puertoDestino);
            servidorUDP.send(envioData);
            
            recibidos = new byte [1024];
            
            //PARA TERMINAR EL BUCLE
            if(mensaje.trim().equals("*")) break;
        }//fin while
        
        //CIERRE DEL SOCKET
        servidorUDP.close();
        System.out.println("Socket cerrado.....");
    }//fin main
}//FIN CLASE
