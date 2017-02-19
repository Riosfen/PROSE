/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat;

import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import proyectochat.controlador.Controlador;
import proyectochat.modelo.Comandos;
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
        
        JFrame ventana = new JFrame("Cliente");
        VistaPrincipal vista = new VistaPrincipal();
        ServidorUDP servidor = new ServidorUDP(vista);
        Controlador c = new Controlador(vista, servidor);
        
        vista.addControlador(c);
        ventana.addWindowListener(c);
        
        ventana.add(vista);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 500);
        ventana.setVisible(true);
        ventana.setMinimumSize(new Dimension(500, 200));
        
        
        
        DatagramSocket servidorUDP = servidor.getServidorUDP();
        
        byte buff[] = new byte[1024];
        buff = Comandos.comando[Comandos.CONECTAR].getBytes();
        
        boolean conectado = false;
        
        while(!conectado){
            try {

                DatagramPacket envioData = new DatagramPacket (buff, buff.length, servidor.getDireccion(), servidor.getPuerto());
                servidorUDP.send(envioData);

                DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                servidorUDP.setSoTimeout(10000);
                servidorUDP.receive(reciboData);
                
                String resul = new String(reciboData.getData());
                
                if (resul.equals(Comandos.comando[Comandos.CONECTAR])){
                    servidor.setPuerto(reciboData.getPort());
                    servidor.setDireccion(reciboData.getAddress());

                    vista.setMensajeChatGeneral("Conectado correctamente con el servidor:\n"
                            + "Puerto: " + reciboData.getPort() + 
                            "\nIP: " + reciboData.getAddress());

                    servidorUDP.setSoTimeout(Integer.MAX_VALUE);
                    vista.setMensajeChatGeneral("false");
                    conectado = true;
                }

            }catch (SocketTimeoutException e) {
                vista.setMensajeChatGeneral("No se ha conseguido conectar con el servidor.\nIntentandolo de nuevo...");
            }catch (IOException e) {
                conectado = true;
                vista.setMensajeChatGeneral("No se ha encontrado el servidor.\nIntentalo de nuevo con una direccion distinta.");
            }
        }
    
    
        
        buff = new byte[1024];
        while(!servidorUDP.isClosed()){
        
            try {

                DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                servidorUDP.receive(reciboData);

                String texto = new String(reciboData.getData());
                vista.setMensajeChatGeneral(texto);

                buff = new byte[1024];

            } catch (IOException ex) {
                Logger.getLogger(ProyectoChat.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
       
    }
    
}
