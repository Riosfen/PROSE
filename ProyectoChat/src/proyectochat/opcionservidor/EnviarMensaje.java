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
import proyectochat.modelo.Comandos;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class EnviarMensaje extends Thread{
    
    private VistaPrincipal vista;
    private ServidorUDP servidor;
    private String mensaje;
    
    private DatagramPacket p;
    
    public EnviarMensaje(VistaPrincipal vista, ServidorUDP servidor, String mensaje){
        this.vista = vista;
        this.servidor = servidor;
        this.mensaje = mensaje;
    }
    
    public EnviarMensaje(DatagramSocket servidor, String mensaje){
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        
        if (mensaje != null){

            if (!mensaje.equals("")){

                try {

                    byte buf[] = new byte[1024];
                    buf = mensaje.getBytes();

                    p = new DatagramPacket(buf, buf.length, servidor.getDireccion(), servidor.getPuerto());
                    vista.setMensajeChatGeneral("Servidor: " + servidor.getDireccion() + "\nPuerto: " + servidor.getPuerto());
                    if (tratarMensaje(mensaje)){
                        vista.setMensajeChatGeneral("Cliente: " + p.getAddress() + ", " + p.getPort());
                    }else{
                        servidor.getServidorUDP().send(p);
                        vista.setMensajeChatGeneral("Cliente: " + p.getAddress() + ", " + p.getPort());
                    }
                    vista.setVaciarCajaTexto();

                } catch (IOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
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
            case Comandos.CONECTAR:
        {
            try {
                servidor.getServidorUDP().send(p);
            } catch (IOException ex) {
                Logger.getLogger(EnviarMensaje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
