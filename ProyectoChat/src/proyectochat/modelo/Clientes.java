/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

/**
 *
 * @author samo_
 */
public class Clientes {
    
    public ArrayList<Cliente> clientes;
    
    public Clientes(){}

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public Cliente getCliente(int pos){
        return clientes.get(pos);
    }
    
    public void enviarMulticast(String mensaje, DatagramSocket servidorUDP) throws IOException{
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            
            byte men[] = new byte[1024];
            men = mensaje.getBytes();
            DatagramPacket datCliente = c.getDatagrama();
            
            DatagramPacket enviarPaket = new DatagramPacket(men, men.length, datCliente.getAddress(), datCliente.getPort());
            servidorUDP.send(enviarPaket);
        }
    }
    
}
