package proyectochat.opcionservidor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import proyectochat.modelo.Comandos;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Conectar extends Thread{

    private VistaPrincipal vista;
    private ServidorUDP servidor;
    
        public Conectar(VistaPrincipal vista, ServidorUDP servidor){
            this.vista = vista;
            this.servidor = servidor;
        }
    
        @Override
        public void run() {
            
            if (servidor.isActive()){servidor.getServidorUDP().disconnect();}
                
            String resul = JOptionPane.showInputDialog(vista, "Dirección: ", "Conexión", JOptionPane.INFORMATION_MESSAGE);
            
            if (resul != null){
            
                resul = resul.trim();

                if (resul.equals("")){
                    vista.setMensajeChatGeneral("Error, no se ha introducido una dirección/puerto correctos.\n"
                            + "La estructura correcta es(ip|direccion:puerto):\n"
                            + "1) localhost:8080    2) 127.0.0.1:8080   3) google.com:8080\n"
                            + "Contacta con el administrador para mas información.");

                }else{

                    String direccion = "";
                    int puerto = servidor.getPuerto();
                    
                    vista.setMensajeChatGeneral("Intentando establecer conexión...");

                    if (resul.contains(":") && resul.charAt(0) != ':' && resul.charAt(resul.length()-1) != ':'){
                        try {
                            StringTokenizer tk = new StringTokenizer(resul, ":");

                            direccion = tk.nextToken().trim();
                            puerto = Integer.valueOf(tk.nextToken().trim());

                            servidor.setDireccion(InetAddress.getByName(direccion));
                            servidor.setPuerto(puerto);
                        
                            String buff = Comandos.comando[Comandos.CONECTAR] + " " + resul;
                            Thread th = new EnviarMensaje(vista, servidor, buff);
                            th.start();
                            
                        } catch (UnknownHostException ex) {
                            JOptionPane.showConfirmDialog(vista, "Se ha producido un error al conectarse a '" + resul + "', Intentelo de nuevo con otra dirección.", "Error de conexión", JOptionPane.CLOSED_OPTION);
                        }

                    }else{
                        direccion = resul;

                        try {

                            servidor.setDireccion(InetAddress.getByName(direccion));

                            String buff = Comandos.comando[Comandos.CONECTAR] + " " + resul;
                            Thread th = new EnviarMensaje(vista, servidor, buff);
                            th.start();

                        } catch (UnknownHostException ex) {
                            JOptionPane.showConfirmDialog(vista, "Se ha producido un error al conectarse a '" + resul + "', Intentelo de nuevo con otra dirección.", "Error de conexión", JOptionPane.CLOSED_OPTION);
                        }

                    }
                    
                }
            }
            
        }
    }
