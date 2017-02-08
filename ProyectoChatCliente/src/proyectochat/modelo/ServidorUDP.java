/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samo_
 */
public class ServidorUDP {
    
    private static final int PUERTO_REMOTO = 4000;
    
    private int puertoRemoto;
    private DatagramSocket servidorUDP;
    
    public ServidorUDP(){
        this.puertoRemoto = PUERTO_REMOTO;
        
        try {
            
            servidorUDP = new DatagramSocket();
            
        } catch (SocketException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void cerrarServidor(){
        try {
            byte[] buf = "*".getBytes();
            
            DatagramPacket envio = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), puertoRemoto);
            servidorUDP.send(envio);
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        servidorUDP.close();
    }
    
    public boolean isActive(){
        return !servidorUDP.isClosed();
    }
    public int getPuerto(){
        return puertoRemoto;
    }
    public DatagramSocket getServidorUDP(){
        return servidorUDP;
    }
    
    public void setPuerto(int puerto){
        
        try {
            int port =  servidorUDP.getPort();
            servidorUDP = new DatagramSocket(port);
        
        } catch (SocketException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.puertoRemoto = puerto;
        
    }
    
}
