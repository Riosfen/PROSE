/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat;

import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import proyectochat.controlador.Controlador;
import proyectochat.modelo.Comprobacion;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class ProyectoChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ServidorUDP servidor = new ServidorUDP();
        
        JFrame ventana = new JFrame("Servidor de mensajeria simple");
        VistaPrincipal vista = new VistaPrincipal();
        Controlador c = new Controlador(vista, servidor);
        
        vista.addControlador(c);
        ventana.addWindowListener(c);
        
        ventana.add(vista);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 500);
        ventana.setVisible(true);
        ventana.setMinimumSize(new Dimension(500, 200));
     
        Thread hilo = new Thread(new Comprobacion(vista,servidor));
        hilo.start();
        
        
        byte buff[] = new byte[1024];
        boolean conectado = false;
        
        while(!conectado){
            try {

                DatagramPacket envioData = new DatagramPacket (buff, buff.length, InetAddress.getLocalHost(), servidor.getPuerto());
                servidor.getServidorUDP().send(envioData);

                DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                servidor.getServidorUDP().setSoTimeout(10000);
                servidor.getServidorUDP().receive(reciboData);
                
                servidor.setPuerto(reciboData.getPort());
                
                vista.setMensajeChatGeneral("Conectado correctamente con el servidor:\n"
                        + "Puerto: " + reciboData.getPort() + 
                        "\nIP: " + reciboData.getAddress());
                
                servidor.getServidorUDP().setSoTimeout(Integer.MAX_VALUE);
                conectado = true;

            } catch (SocketTimeoutException e) {
                vista.setMensajeChatGeneral("No se ha conseguido conectar con el servidor.\nIntentandolo de nuevo...");
            }catch (IOException ex) {
                Logger.getLogger(ProyectoChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        while(true){
        
            if (servidor.isActive()){
                
                try {
                    
                    DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                    servidor.getServidorUDP().receive(reciboData);
                    
                    String texto = new String(reciboData.getData());
                    vista.setMensajeChatGeneral(texto);

                    buff = new byte[1024];
                
                } catch (IOException ex) {
                    Logger.getLogger(ProyectoChat.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        
    }
    
}
