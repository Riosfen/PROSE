/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Controlador extends WindowAdapter implements ActionListener{

    private VistaPrincipal vista;
    private ServidorUDP servidor;
    
    public Controlador(VistaPrincipal vista, ServidorUDP servidor){
        this.vista = vista;
        this.servidor = servidor;
    
    }

    @Override
    public void windowClosed(WindowEvent e) {
        servidor.cerrarServidor();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "enviar":
                String texto = vista.getTextoEnviar().trim();
                if (!texto.equals("")){
                   
                    try {
                        
                        byte buf[] = new byte[1024];
                        buf = texto.getBytes();

                        DatagramPacket p = new DatagramPacket(buf, buf.length, servidor.getDireccion(), servidor.getPuerto());
                        vista.setMensajeChatGeneral("Direccion: "+servidor.getDireccion()+"\n"+"Puerto: "+servidor.getPuerto());
                        servidor.getServidorUDP().send(p);
                        
                        vista.setVaciarCajaTexto();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
            case "salir":
                servidor.cerrarServidor();
                System.exit(0);
                break;
                
            case "conectar":
                if (servidor.isActive()){servidor.desconectarServidor();}
                
                servidor.conectarServidor();
                String resul = JOptionPane.showInputDialog(vista, "Dirección: ", "Conexión", JOptionPane.INFORMATION_MESSAGE);
                resul = resul.trim();
                
                if (resul.equals("")){
                    vista.setMensajeChatGeneral("Error, no se ha introducido una dirección/puerto correctos.\n"
                            + "La estructura correcta es(ip|direccion:puerto):\n"
                            + "1) localhost:8080    2) 127.0.0.1:8080   3) google.com:8080\n"
                            + "Contacta con el administrador para mas información.");
                }else{
                    
                    String direccion = "";
                    int puerto = servidor.getPuerto();
                    
                    if (resul.contains(":") && resul.charAt(0) != ':' && resul.charAt(resul.length()) != ':'){
                        vista.setMensajeChatGeneral("He entrado");
                        StringTokenizer tk = new StringTokenizer(resul, ":");

                        direccion = tk.nextToken().trim();
                        puerto = Integer.valueOf(tk.nextToken().trim());
                    
                    }else{
                        direccion = resul;
                    }
                        vista.setMensajeChatGeneral(direccion);
                        vista.setMensajeChatGeneral(""+puerto);
                    
                    try {
                        
                        servidor.setDireccion(InetAddress.getByName(direccion));
                        //if (puerto != ){servidor.setPuerto(puerto);}
                    
                        //servidor.buscarServidor();
                        
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
            case "desconectar":
                servidor.cerrarServidor();
                break;
                
            case "usuarios":
                mostrarUsuarios();
                break;
                
            case "nombre":
                    
                servidor.setNombre(JOptionPane.showInputDialog(vista, "Nombre de usuario: ", "Cambiar nombre", JOptionPane.INFORMATION_MESSAGE));
                
                
                
                
                break;
        }
        
    }

    private void mostrarUsuarios() {
        
    }
    
}
