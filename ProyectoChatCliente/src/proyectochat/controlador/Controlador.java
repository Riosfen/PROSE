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
import java.util.logging.Level;
import java.util.logging.Logger;
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

                        DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), servidor.getPuerto());
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
        }
    }
    
}
