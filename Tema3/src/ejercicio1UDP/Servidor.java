package ejercicio1UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Servidor {
	
	public static Scanner teclado = new Scanner(System.in);

	private static final int PUERTO_LOCAL = 2000;

	public static void main(String[] args) throws Exception {

		byte[] mensajeEntrada = new byte[1024];
		
		InetAddress destino = InetAddress.getLocalHost();
		DatagramSocket servidorUDP = new DatagramSocket(PUERTO_LOCAL);
		
		while (true){
			
			System.out.println("Esperando conexion del cliente...");
			
			DatagramPacket reciboData = new DatagramPacket (mensajeEntrada, mensajeEntrada.length);
			servidorUDP.receive(reciboData);
	        
	        int bytesRecibidos = reciboData.getLength();
	        String msg = new String(reciboData.getData());
	        
	        System.out.println("Número de bytes recibidos: "+bytesRecibidos);
	        System.out.println("Contenido del datagrama: "+msg.trim());
	        System.out.println("Puerto origen del mensaje: "+reciboData.getPort());
	        System.out.println("IP de origen: "+reciboData.getAddress().getHostAddress());
	        System.out.println("Pueto destino del mensaje: "+servidorUDP.getLocalPort());
			
			if (!msg.trim().equals("*")){

				System.out.println("Esperando al servidor...");
				
				DatagramPacket envioData = new DatagramPacket (mensajeEntrada, mensajeEntrada.length,destino,reciboData.getPort());
				servidorUDP.send(envioData);
				
				mensajeEntrada = new byte[1024];
				
			}else{break;}
			
		}
		
		servidorUDP.close();

	}

}
