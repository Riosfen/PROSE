package soket1;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class ServidorEjemplo1 {
	
	public static Scanner teclado = new Scanner(System.in);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try
		{
		int numeroPuerto=6000;
		
		System.out.println("Numero de clientes que espera");
		int cont = Integer.valueOf(teclado.nextLine());
		
		ServerSocket servidor=new ServerSocket(numeroPuerto);
		Socket clienteConectado= null;
		
		System.out.println("Esperando...");
		
		int contador = 0;
		do {
			clienteConectado= servidor.accept();
			
			//Creo flujo de entrada con cliente
			InputStream entrada=null;
			entrada= clienteConectado.getInputStream();
			DataInputStream flujoEntrada=new DataInputStream (entrada);
			
			// Recibo el mensaje del cliente
			
			System.out.println("He recibido del cliente: " + flujoEntrada.readUTF());
			
			// Crear fljo de salida al cliente
			
			OutputStream salida=null;
			salida=clienteConectado.getOutputStream();
			DataOutputStream flujoSalida=new DataOutputStream(salida);
			
			
			// Envio un saludo al cliente
			
			flujoSalida.writeUTF( "Hola cliente soy el servidor");
			
			// Cerrar streams y socket
			
			entrada.close();
			flujoEntrada.close();
			salida.close();
			flujoSalida.close();
			clienteConectado.close();
			contador++;
		
		} while(contador < cont);
		servidor.close();
		
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

}
