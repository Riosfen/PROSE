/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.opcionservidor;

import java.io.IOException;
import java.net.DatagramPacket;
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
    
    public EnviarMensaje(VistaPrincipal vista, ServidorUDP servidor, String mensaje){
        this.vista = vista;
        this.servidor = servidor;
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        
        if (mensaje != null){

            if (!mensaje.equals("")){

                try {

                    byte buf[] = new byte[1024];
                    buf = mensaje.getBytes();

                    DatagramPacket p = new DatagramPacket(buf, buf.length, servidor.getDireccion(), servidor.getPuerto());
                    if (!tratarMensaje(mensaje)){
                    
                    }else{
                        servidor.getServidorUDP().send(p);
                    }
                    vista.setVaciarCajaTexto();

                } catch (IOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private boolean tratarMensaje(String msg) {
        
        boolean isComando = true;
        
        StringTokenizer st = new StringTokenizer(msg, " ");
        String texto = st.nextToken();
        
        int i = 0;
        while( i < Comandos.comando.length && isComando){
            
            if (isComando && texto.equals(Comandos.comando[i])){
                isComando = false;
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
            case Comandos.CAMBIAR_NOMBRE_CLIENTE:
                //cambiarNombre();
                break;
            case Comandos.CONECTAR:
                //iniciarConexion();
                break;
            case Comandos.LIMPIAR_CHAT:
                vista.setVaciarChatGeneral();
                break;
            case Comandos.LISTA_CLIENTES:
                break;
            case Comandos.SALIR:
                Thread th = new Desconectar(vista, servidor);
                th.start();
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
