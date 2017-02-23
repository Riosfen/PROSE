/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.opcionservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.controlador.Controlador;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Clientes;
import proyectochat.modelo.Comandos;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class ConexionNueva extends Thread{
    
    private VistaPrincipal vista;
    private Cliente cliente;
    private Clientes clientes;
    
    public ConexionNueva(Clientes clientes, Cliente cliente, VistaPrincipal vista){
        this.vista = vista;
        this.cliente = cliente;
    }

    @Override
    public void run() {
                
        try {

            byte buf[] = new byte[1024];

            DatagramPacket p = new DatagramPacket(buf, buf.length);
            cliente.getServer().receive(p);
            
            String mensaje = new String(p.getData());
            
            if (tratarMensaje(mensaje)){
                
            }else{
                clientes.enviarMulticast(cliente.getNick() + ": " + mensaje);
            }
            vista.setVaciarCajaTexto();

        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private boolean tratarMensaje(String msg) {
        
        boolean isComando = false;
        
        StringTokenizer st = new StringTokenizer(msg, " ");
        String texto = st.nextToken();
        
        int i = 0;
        while( i < Comandos.comando.length && !isComando){
            
            if (texto.equals(Comandos.comando[i])){
                isComando = true;
                accionComando(i);
            }
            
            i++; 
        }
        
        return isComando;
    
    }
    
    private void accionComando(int i) {
        switch(i){
            case Comandos.AYUDA:
                mostrarComandos();
                break;
            case Comandos.LIMPIAR_CHAT:
                vista.setVaciarChatGeneral();
                break;
            case Comandos.DESCONECTAR:
                cliente.getServer().close();
                clientes.eliminarCliente(cliente);
                break;
        }
    }
    
    private void mostrarComandos() {
        
        StringBuilder msg = new StringBuilder("Lista de omandos:\n");
        
        for (int i = 0; i < Comandos.comando.length; i++){
        
            msg.append(Comandos.comando[i] + "\n");
            
        }
        
        vista.setMensajeChatGeneral(msg.toString());
    }
    
}
