/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samo_
 */
public class Cliente extends Thread{
    
    private String nombre;
    private DatagramPacket datagrama;
    private ServidorUDP servidor;
    
    public Cliente(String nombre, DatagramPacket datagrama, ServidorUDP servidor){
        this.nombre = nombre;
        this.datagrama = datagrama;
        this.servidor = servidor;
    }

    public DatagramPacket getDatagrama() {
        return datagrama;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
   
        byte buff[] = new byte[1024];
        
        while(true){
            
            try {
                DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                servidor.getServidorUDP().receive(reciboData);

                int bytesRecibidos = reciboData.getLength();
                String msg = new String(reciboData.getData());

                if (!msg.trim().equals("*")){

                    servidor.getClientes().enviarMulticast(msg, servidor.getServidorUDP());
                    buff = new byte[1024];

                }else{
                    
                    servidor.getClientes().getClientes().remove(this);
                    
                    break;
                }


            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
}
