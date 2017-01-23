package soket2;

import java.net.*;
import java.io.*;

public class HiloServicio extends Thread {
	private final Socket  socket;
	DataOutputStream flujoSalida;
	DataInputStream flujoEntrada;
   
   //Constructor
   HiloServicio(Socket socket)  throws IOException {
      this.socket = socket;      
   }

  //CONJUNTO DE MÉTODOS QUE SE EJECUTAN DENTRO DE RUN
  //Estabelecer canales de entrada y salida
   private void setStreams( ) throws IOException{
	   flujoSalida =new DataOutputStream(socket.getOutputStream());
	   flujoEntrada=new DataInputStream(socket.getInputStream());
   }
   //Cierre de flujos (sockets)
   private void closeStreams( ) throws IOException {
	  flujoSalida.close();
	  flujoEntrada.close();
   }
   //Envío de mensaje "ECO" al cliente
   public void enviarMensaje(String mensaje) throws IOException {	
      flujoSalida.writeUTF(mensaje);                     
   } 
   //Respuesta mensaje del cliente
   public String recibirMensaje( ) throws IOException {	     
      String mensaje = flujoEntrada.readUTF();  
      return mensaje;
   } 
   //FLUJO DE EJECUCIÓN DEL HILO
   @Override
   public void run() {
       String mensaje;
       boolean terminar = false;
       System.out.println("Conexion aceptada");
       try {
            //1. Establecemos canales de entrada y salida
            setStreams();
            
            while (terminar == false) {
                //2. Recibimos mensaje cliente
                mensaje = recibirMensaje( );
	        System.out.println("Mensaje recibido: "+ mensaje);
                
                //4. Si recibido ".", cerramos flujos de sesión y socket
	        if (mensaje.equals (ServidorEco.MENSAJEFINAL)){                    
                    enviarMensaje("Cerramos la sesion"); 
                    //Cerramos flujos
		    closeStreams();
                    //Cerramos socket
                    socket.close();		        
		    terminar = true;
	        } 
                //3. Envío de mensaje "ECO" al cliente
	        else {		    
		    enviarMensaje(mensaje);// Enviar el mensaje al cliente
	        } 
         }//fin while       
       } catch (IOException e) {
            System.err.println( "Error: "  + e.getMessage());
       } 
   }//fin run    
} //FIN Clase
