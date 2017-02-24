/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class ServidorUDP {
    
    private static final int PUERTO = 40000;
    
    private DatagramSocket servidorUDP;
    private InetAddress direccion;
    
    public ServidorUDP(VistaPrincipal vista){
        try {

            this.direccion = InetAddress.getLocalHost();
            servidorUDP = new DatagramSocket(PUERTO, direccion);
            
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Muestro en la ventana el puerto y la direccion
        //
        vista.setDireccion(direccion.toString());
        vista.setPuerto(servidorUDP.getLocalPort());
        
    }
    
    
    // inicio getter and setter
    public boolean isActive(){
        return !servidorUDP.isClosed();
    }
    public DatagramSocket getServidorUDP(){
        return servidorUDP;
    }
    public InetAddress getDireccion() {
        return direccion;
    }
    // fin getter and setter
    
}
