package soket1;

import java.io.*;
import java.net.*;

public class ClienteEjemplo1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String host= "localhost";
		int puerto=6000;
		String cadenaRecibida;
		
		try{
			System.out.println("Programa cliente iniciado");
			Socket cliente= new Socket (host, puerto);
			
			DataOutputStream flujoSalida=new DataOutputStream( cliente.getOutputStream());
			
			flujoSalida.writeUTF("Saludos al servidor desde el cliente");
			
			DataInputStream flujoEntrada=new DataInputStream(cliente.getInputStream());
			
			cadenaRecibida= flujoEntrada.readUTF();
			
			System.out.println("He recibido del servidor: " + cadenaRecibida);
			
			flujoEntrada.close();
			flujoSalida.close();
			cliente.close();
		}
		
		catch ( ConnectException e){
			System.out.println("Imposible conectar con el servidor");
		}
		catch  ( IOException e)	{
			e.printStackTrace();
		}
		
	}

}
