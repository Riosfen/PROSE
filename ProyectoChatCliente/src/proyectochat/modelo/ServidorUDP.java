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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class ServidorUDP {
    
    private static final int PUERTO_REMOTO = 4000;
    
    private int puertoRemoto;
    private DatagramSocket servidorUDP;
    private boolean conectado;
    private VistaPrincipal vista;
    private InetAddress direccion;
    private String nombre;
    
    public ServidorUDP(VistaPrincipal vista){
        try {
            this.direccion = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.vista = vista;
        this.puertoRemoto = PUERTO_REMOTO;
        
        try {
            
            servidorUDP = new DatagramSocket();
            
        } catch (SocketException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.conectado = false;
        
    }
    
    
    // inicio getter and setter
    public boolean isActive(){
        return !servidorUDP.isClosed();
    }
    public int getPuerto(){
        return puertoRemoto;
    }
    public DatagramSocket getServidorUDP(){
        return servidorUDP;
    }
    public InetAddress getDireccion() {
        return direccion;
    }
    public String getNombre() {
        return nombre;
    }
    
    public void setPuerto(int puerto){
        
        servidorUDP.disconnect();
        
        servidorUDP.connect(direccion, puerto);
        this.puertoRemoto = puerto;
        
    }
    public void setDireccion(InetAddress direccion){
        
        servidorUDP.disconnect();
        
        servidorUDP.connect(direccion, puertoRemoto);
        this.direccion = direccion;
        
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // fin getter and setter
    
    
    // inicio metodos adicionales
    public void cerrarServidor(){
        try {
            byte[] buf = Comandos.comando[Comandos.SALIR].getBytes();
            
            DatagramPacket envio = new DatagramPacket(buf, buf.length, direccion, puertoRemoto);
            servidorUDP.send(envio);
            
        } catch (IOException ex) {
            Logger.getLogger(ServidorUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        servidorUDP.close();
    }
    public void conectarServidor() {
        servidorUDP.connect(direccion, puertoRemoto);
    }
    public void desconectarServidor() {
        servidorUDP.disconnect();
    }
    
    // fin metodos adicionales

    
}
