/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

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
public class Cliente{
        
    private InetAddress direccion;
    private int puertoRemoto;
    private String nick;
    private DatagramPacket datagrama;
    private DatagramSocket server;
    
    public Cliente(String nombre, DatagramPacket datagrama){
        try {
        
            this.nick = nombre;
            this.datagrama = datagrama;
            this.direccion = datagrama.getAddress();
            this.puertoRemoto = datagrama.getPort();
            server = new DatagramSocket();
            
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InetAddress getDireccion() {
        return direccion;
    }

    public int getPuertoRemoto() {
        return puertoRemoto;
    }
    
    public DatagramPacket getDatagrama() {
        return datagrama;
    }

    public String getNombre() {
        return nick;
    }

    public String getNick() {
        return nick;
    }

    public DatagramSocket getServer() {
        return server;
    }

    
    public void setPuertoRemoto(int puertoRemoto) {
        this.puertoRemoto = puertoRemoto;
    }

    public void setDireccion(InetAddress direccion) {
        this.direccion = direccion;
    }

    public void setDatagrama(DatagramPacket datagrama) {
        this.datagrama = datagrama;
    }

    public void setServer(DatagramSocket server) {
        this.server = server;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }
    
}
