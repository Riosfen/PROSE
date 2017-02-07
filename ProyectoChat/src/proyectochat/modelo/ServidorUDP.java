/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author samo_
 */
public class ServidorUDP {
    
    private static final int PUERTO_LOCAL = 4000;
    
    private Clientes clientes;
    private int puertoLocal;
    private DatagramSocket servidorUDP;
    
    public ServidorUDP(Clientes clientes) throws SocketException{
        this.clientes = clientes;
        puertoLocal = PUERTO_LOCAL;
        servidorUDP = new DatagramSocket(puertoLocal);
        
    }
    
    public void iniciarServidor(){
        try {
            servidorUDP = new DatagramSocket(puertoLocal);
        } catch (SocketException ex) {
             System.out.println("Error al conectar el servidor: " + ServidorUDP.class.getName());
        }
    }
    public void setPuertoEscucha(int puerto){
        puertoLocal = puerto;
        
        if (!servidorUDP.isClosed()){
            servidorUDP.close();
        }   
        
        iniciarServidor();

    }
    public void cerrarServidor() throws IOException{
        clientes.enviarMulticast("*", servidorUDP);
        servidorUDP.close();
    }
    
    public Clientes getClientes(){
        return clientes;
    }
    public boolean isActive(){
        return !servidorUDP.isClosed();
    }
    public int getPuerto(){
        return puertoLocal;
    }
    public DatagramSocket getServidorUDP(){
        return servidorUDP;
    }
    
}
