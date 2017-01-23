package soket2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

	private static final String HOST = "localhost";
	private static final int  PUERTO = 2000;
	private static Scanner teclado = new Scanner(System.in);

	public static void main(String[] args) {		
            String cadenaRecibida;
            String cadenaEnviar;

            try {
                System.out.println("Programa cliente iniciado");
                Socket cliente = new Socket(HOST, PUERTO);

                DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
                DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

                System.out.println("Introduce cadenas, para terminar un .");
                do {
                    cadenaEnviar = teclado.nextLine();
                    flujoSalida.writeUTF(cadenaEnviar);
                    cadenaRecibida = flujoEntrada.readUTF();
                    System.out.println("He recibido del servidor: "+ cadenaRecibida);
                } while (!cadenaEnviar.equals("."));

                //Cerramos flujos de sesión
                flujoEntrada.close();
                flujoSalida.close();
                //Cerramos socket
                cliente.close();
            }
            catch (ConnectException e) {
                    System.out.println("Imposible conectar con el servidor");
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }//Fin main
}//FIN clase
