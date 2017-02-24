/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.opcionservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private DatagramPacket p;
    
    public ConexionNueva(Clientes clientes, Cliente cliente, VistaPrincipal vista){
        this.vista = vista;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        
        byte buf[] = new byte[1024];
        
         try {
             
            buf = (Comandos.comando[Comandos.CONECTAR]).getBytes();
            p = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), cliente.getPuertoRemoto());
            cliente.getServer().send(p);
            
        } catch (IOException ex) {
            Logger.getLogger(ConexionNueva.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(true){

                buf = new byte[1024];

                p = new DatagramPacket(buf, buf.length);
            try {
                cliente.getServer().receive(p);
            } catch (IOException ex) {
                Logger.getLogger(ConexionNueva.class.getName()).log(Level.SEVERE, null, ex);
            }

                String mensaje = new String(p.getData());

                if (tratarMensaje(mensaje)){

                }else{
                    clientes.enviarMulticast(cliente.getNick() + ": " + mensaje);
                    vista.setMensajeChatGeneral(cliente.getNick() + ": " + mensaje);
                    System.out.println(p.getPort() + " = " + cliente.getServer().getLocalPort());
                }
                vista.setVaciarCajaTexto();

            
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
                clientes.enviarListaClientes();
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
