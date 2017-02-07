/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat;

import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import javax.swing.JFrame;
import proyectochat.controlador.Controlador;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Clientes;
import proyectochat.modelo.Comprobacion;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class ProyectoChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException, IOException {
        // TODO code application logic here
        
        JFrame ventana = new JFrame("Servidor de mensajeria simple");
        VistaPrincipal vista = new VistaPrincipal();
        Clientes clientes = new Clientes();
        ServidorUDP servidor = new ServidorUDP(clientes);
        Controlador c = new Controlador(vista, clientes, servidor);
        
        vista.addControlador(c);
        
        ventana.add(vista);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 500);
        ventana.setVisible(true);
        ventana.setMinimumSize(new Dimension(500, 200));
     
        Thread hilo = new Thread(new Comprobacion(vista,servidor,c));
        hilo.start();
        
        Thread hiloCliente;
        
        byte buff[] = new byte[1024];
        int contador = 0;
        Cliente cliente;
        
        while(true){
        
            if (servidor.isActive()){
                DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                servidor.getServidorUDP().receive(reciboData);

                cliente = new Cliente("Cliente nº"+contador, reciboData, vista);
                clientes.getClientes().add(cliente);
                hiloCliente = new Thread(cliente);
                hiloCliente.start();

                contador++;
            }
        }
        
    }
    
}
