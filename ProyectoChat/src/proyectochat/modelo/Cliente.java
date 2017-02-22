/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samo_
 */
public class Cliente{
    
    public static int num = 0;
    
    private String nick;
    private DatagramPacket datagrama;
    private DatagramSocket server;
    
    public Cliente(String nombre, DatagramPacket datagrama){
        this.nick = nombre;
        this.datagrama = datagrama;
        
        try {
            server = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        num++;
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

    public void setNick(String nick) {
        this.nick = nick;
    }
    
}
