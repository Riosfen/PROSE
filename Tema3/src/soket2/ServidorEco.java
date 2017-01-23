package soket2;

import java.net.*;

public class ServidorEco {
	public static final String MENSAJEFINAL = ".";
	private static final int PUERTO = 2000;
	
	public static void main(String[] args) {

		HiloServicio hiloServicio;

		try {
			// Creamos el serverSocket
			ServerSocket miServerSocket = new ServerSocket(PUERTO);
			System.out.println("Servidor creado");
			while (true) {
                            // ESPERAMOS CONEXIONES:
                            System.out.println("Servidor esperando conexiones");				
                            //El servidor espera a nuevo cliente
                            Socket sCliente = miServerSocket.accept();
                            //Se crea nuevo hilo
                            hiloServicio = new HiloServicio(sCliente);
                            //Se inicia su ejecución
                            hiloServicio.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}//FIN clase
