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
import proyectochat.ProyectoChat;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Clientes;
import proyectochat.modelo.Comandos;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Escuchar extends Thread{

    private final VistaPrincipal vista;
    private final ServidorUDP servidor;
    private DatagramPacket reciboData;
    private Cliente cliente;
    private final Clientes clientes;
    
    private String texto;
    
    public Escuchar (VistaPrincipal vista, ServidorUDP servidorUDP, Clientes clientes){
        this.vista = vista;
        this.servidor = servidorUDP;
        this.clientes = clientes;
    }
    
    @Override
    public void run() {
        while(!servidor.getServidorUDP().isClosed()){
            
            try {
                
                byte buff[] = new byte[1024];   

                reciboData = new DatagramPacket (buff, buff.length);
                servidor.getServidorUDP().receive(reciboData);

                texto = new String(reciboData.getData());
                if (tratarMensaje(texto)){
                    
                }else{
                    vista.setMensajeChatGeneral("Servidor: " + texto);
                }

            } catch (IOException ex) {
                Logger.getLogger(ProyectoChat.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
    }

    private boolean tratarMensaje(String msg) {
        
        boolean isComando = false;
        
        StringTokenizer st = new StringTokenizer(msg, " ");
        String resul = st.nextToken();
        
        int i = 0;
        while( i < Comandos.comando.length && !isComando){
            
            if (resul.equals(Comandos.comando[i])){
                isComando = true;
                accionComando(i);
            }
            
            i++; 
        }
        
        return isComando;
    
    }

    private void accionComando(int i) {
        switch(i){
            case Comandos.CONECTAR:
                iniciarConexion();
                break;
        }
    }

    private void iniciarConexion() {
        
        StringTokenizer st = new StringTokenizer(texto, " ");
        st.nextToken();
        
        cliente = new Cliente(st.nextToken() , reciboData);
        vista.setMensajeChatGeneral(cliente.getNick() + ", se ha conectado correctamente al servidor.");
        
        clientes.addCliente(cliente);
        Thread th = new ConexionNueva(clientes, cliente, vista);
        th.start();
        
    }
    
}
