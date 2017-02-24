/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samo_
 */
public class Cliente{
    
    private InetAddress direccion;
    private int puertoRemoto;
    private String nick;
    private DatagramSocket server;
    
    public Cliente(String nombre){
        this.nick = nombre;
        
        try {
            server = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombre() {
        return nick;
    }

    public DatagramSocket getServer() {
        return server;
    }

    public int getPuertoRemoto() {
        return puertoRemoto;
    }

    public InetAddress getDireccion() {
        return direccion;
    }
    

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setDireccion(InetAddress direccion) {
        this.direccion = direccion;
    }

    public void setPuertoRemoto(int puertoRemoto) {
        this.puertoRemoto = puertoRemoto;
    }
    
}
