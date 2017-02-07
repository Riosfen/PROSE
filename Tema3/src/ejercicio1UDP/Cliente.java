package ejercicio1UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
	
	public static Scanner teclado = new Scanner(System.in);

	private static final int PUERTO_DESTINO = 2000;

	public static void main(String[] args) throws Exception {

		byte[] mensaje = new byte[1024];
		byte[] mensajeEntrada = new byte[1024];
		
		InetAddress destino = InetAddress.getLocalHost();
		DatagramSocket clienteUDP = new DatagramSocket();
		
		while (true){
			
			System.out.println("Envia un mensaje al servidor (para cerrar la conexion introduce \"*\"):");
			String msg = teclado.nextLine();
			
			mensaje = msg.getBytes();
			
			DatagramPacket envioData = new DatagramPacket (mensaje, mensaje.length, destino, PUERTO_DESTINO);
			clienteUDP.send(envioData);
			
			
			if (!msg.trim().equals("*")){

				System.out.println("Esperando al servidor...");
				
				DatagramPacket reciboData = new DatagramPacket (mensajeEntrada, mensajeEntrada.length);
		        clienteUDP.receive(reciboData);//Se espera hasta recibir el datagrama
		        
		        int bytesRecibidos = reciboData.getLength();//Obtengo número de bytes
		        String datagrama = new String(reciboData.getData());//Obtengo String
		        
		        //MUESTRO INFORMACIÓN
		        System.out.println("N�mero de bytes recibidos: "+bytesRecibidos);
		        System.out.println("Contenido del datagrama: "+datagrama.trim());
		        System.out.println("Puerto origen del mensaje: "+reciboData.getPort());
		        System.out.println("IP de origen: "+reciboData.getAddress().getHostAddress());
		        System.out.println("Pueto destino del mensaje: "+clienteUDP.getLocalPort());
				
			}else{break;}
			
		}
		
		clienteUDP.close();
		
	}

}
