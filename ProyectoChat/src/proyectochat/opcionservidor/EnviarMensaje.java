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
import proyectochat.modelo.Comandos;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class EnviarMensaje extends Thread{
    
    private VistaPrincipal vista;
    private Cliente cliente;
    private String mensaje;
    
    public EnviarMensaje(VistaPrincipal vista, Cliente cliente, String mensaje){
        this.vista = vista;
        this.cliente = cliente;
        this.mensaje = mensaje;
    }
    
    public EnviarMensaje(Cliente cliente, String mensaje){
        this.mensaje = mensaje;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        
        if (mensaje != null){

            if (!mensaje.equals("")){

                try {

                    byte buf[] = new byte[1024];
                    buf = mensaje.getBytes();

                    DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), cliente.getPuertoRemoto());
                    if (tratarMensaje(mensaje)){
                        
                    }else{
                        cliente.getServer().send(p);
                        vista.setMensajeChatGeneral("Servidor: " + mensaje);
                    }
                    vista.setVaciarCajaTexto();

                } catch (IOException ex) {
                    Logger.getLogger(EnviarMensaje.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
