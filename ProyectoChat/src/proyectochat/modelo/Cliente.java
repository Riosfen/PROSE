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
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Cliente extends Thread{
    
    private String nombre;
    private DatagramPacket datagrama;
    private DatagramSocket servidor;
    private VistaPrincipal vista;
    
    public Cliente(String nombre, DatagramPacket datagrama, VistaPrincipal vista) throws SocketException{
        this.nombre = nombre;
        this.vista = vista;
        this.datagrama = datagrama;
        this.servidor = new DatagramSocket();
    }

    public DatagramPacket getDatagrama() {
        return datagrama;
    }

    public String getNombre() {
        return nombre;
    }
    public DatagramSocket getDatagramSocket(){
        return servidor;
    }

    @Override
    public void run() {
        
        byte[] mensaje = "Conectado correctamente al servidor".getBytes();
        
        try{
        
            DatagramPacket p = new DatagramPacket(mensaje, mensaje.length, InetAddress.getLocalHost(), datagrama.getPort());
            servidor.send(p);
            
        }catch(IOException e){
            System.out.println("Error, no se ha podido verificar el usuario");
        }
        
        byte buff[] = new byte[1024];
        
        while(true){
            
            try {
                DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                servidor.receive(reciboData);

                int bytesRecibidos = reciboData.getLength();
                String msg = new String(reciboData.getData());

                if (!msg.trim().equals("*")){
                    
                    msg = nombre + ": " + msg; 
                    Clientes.enviarMulticast(msg);
                    
                    vista.setMensajeChatGeneral(msg);
                    vista.setVaciarCajaTexto();
                    
                    buff = new byte[1024];

                }else{
                    
                    Clientes.clientes.remove(this);
                    
                    break;
                }


            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
}
