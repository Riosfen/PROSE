package soketUDP2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author Adrián
 */
public class ClienteUDP {
	
	public static Scanner teclado = new Scanner(System.in);
	
    public static void main(String argv[]) throws Exception {   
        //Parámetros
        int puertoDestino = 2000;//Puerto por el que escucha
        byte [] enviados = new byte [1024];
        byte [] recibidos = new byte [1024];  
        int tamEnvio = enviados.length;
        int tamRecibido = recibidos.length;
        
        //Argumentos del datatagrama desino
        InetAddress destino = InetAddress.getLocalHost();
        //int puertoDestino = 4000; 
        int bytesRecibidos;
        
        //ASOCIO SOCKET AL PUERTO 
        DatagramSocket clienteUDP = new DatagramSocket(puertoDestino);//puerto local
        
        while (true){
            //ENVIO DE DATAGRAMA
        	System.out.println("Escribe un mensaje: ");
        	 String linea = teclado.nextLine();
        	 enviados = linea.getBytes();

             //CONSTRUCCIÓN DATAGRAMA A ENVIAR
             DatagramPacket envioData = new DatagramPacket (enviados, enviados.length, destino, 4000);
             clienteUDP.send(envioData);
        	
        	if (!linea.trim().equals("*")){
        		//RECEPCION DE DATAGRAMA
        		//CONSTRUYO DATAGRAMA
		        DatagramPacket reciboData = new DatagramPacket (recibidos, tamRecibido);         
		        //ESPERANDO DATAGRAMA
		        System.out.println("Esperando Datagrama........");
		        clienteUDP.receive(reciboData);//Se espera hasta recibir el datagrama
		        bytesRecibidos = reciboData.getLength();//Obtengo número de bytes
		        String datagrama = new String(reciboData.getData());//Obtengo String
		        
		        //MUESTRO INFORMACIÓN
		        System.out.println("Número de bytes recibidos: "+bytesRecibidos);
		        System.out.println("Contenido del datagrama: "+datagrama.trim());
		        System.out.println("Puerto origen del mensaje: "+reciboData.getPort());
		        System.out.println("IP de origen: "+reciboData.getAddress().getHostAddress());
		        System.out.println("Pueto destino del mensaje: "+clienteUDP.getLocalPort());
        	}else{break;}
        }
        
        //CIERRE DEL SOCKET
        clienteUDP.close();        
    }
}
