/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.opcionservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.modelo.Comandos;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Desconectar extends Thread {
    
    private VistaPrincipal vista;
    private ServidorUDP servidor;
    
    public Desconectar(VistaPrincipal vista, ServidorUDP servidor){
        this.vista = vista;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            byte[] buf = Comandos.comando[Comandos.DESCONECTAR].getBytes();
            
            DatagramPacket envio = new DatagramPacket(buf, buf.length, servidor.getDireccion(), servidor.getPuerto());
            servidor.getServidorUDP().send(envio);
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        servidor.getServidorUDP().disconnect();
        vista.setUnableDesconectar(false);
    }
    
}
