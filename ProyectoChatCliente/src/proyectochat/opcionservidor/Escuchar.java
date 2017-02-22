/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.opcionservidor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import proyectochat.ProyectoChat;
import proyectochat.modelo.Comandos;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Escuchar extends Thread{

    private VistaPrincipal vista;
    private ServidorUDP servidor;
    
    private String texto;
    
    public Escuchar (VistaPrincipal vista, ServidorUDP servidorUDP){
        this.vista = vista;
        this.servidor = servidorUDP;
    }
    
    @Override
    public void run() {
        byte buff[] = new byte[1024];
        while(!servidor.getServidorUDP().isClosed()){
        
            try {

                DatagramPacket reciboData = new DatagramPacket (buff, buff.length);
                servidor.getServidorUDP().receive(reciboData);

                texto = new String(reciboData.getData());
                if (!tratarMensaje(texto)){
                    
                }else{
                    vista.setMensajeChatGeneral(texto);
                }
                
                buff = new byte[1024];

            } catch (IOException ex) {
                Logger.getLogger(ProyectoChat.class.getName()).log(Level.SEVERE, null, ex);
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
                cambiarNombre();
                break;
            case Comandos.CONECTAR:
                iniciarConexion();
                break;
            case Comandos.LIMPIAR_CHAT:
                vista.setVaciarCajaTexto();
                break;
            case Comandos.LISTA_CLIENTES:
                obtenerLista();
                break;
            case Comandos.DESCONECTAR:
                servidor.getServidorUDP().disconnect();
                vista.setUnableDesconectar(false);
                break;
        }
    }

    private void mostrarComandos() {
        
        StringBuilder msg = new StringBuilder("Lista de omandos:\n");
        
        for (int i = 0; i < Comandos.comando.length; i++){
        
            msg.append(Comandos.comando[i]);
            
        }
        
    }

    private void cambiarNombre() {
        
        StringTokenizer st = new StringTokenizer(texto, " ");
        st.nextToken();
        servidor.setNombre(st.nextToken());
        
    }

    private void iniciarConexion() {
        
        vista.setUnableDesconectar(true);
        vista.setMensajeChatGeneral("Se ha conectado correctamente al servidor de chat.");
        JOptionPane.showConfirmDialog(vista, "Se ha conectado correctamente al servidor", "Información", JOptionPane.CLOSED_OPTION);
        
    }

    private void obtenerLista() {
        
        
    }
    
}