/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.opcionservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Comandos;

/**
 *
 * @author samo_
 */
public class Desconectar extends Thread {
    
    private Cliente cliente;
    
    public Desconectar(Cliente cliente){
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            byte[] buf = Comandos.comando[Comandos.DESCONECTAR].getBytes();
            
            DatagramPacket envio = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), cliente.getPuertoRemoto());
            cliente.getServer().send(envio);
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cliente.getServer().disconnect();
    }
    
}
