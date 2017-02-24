/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.opcionservidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import proyectochat.ProyectoChat;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Comandos;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Conexion extends Thread{

    private static final int PUERTO_REMOTO = 40000;
    
    private VistaPrincipal vista;
    private DatagramSocket servidor;
    private Cliente cliente;
    
    private DatagramPacket reciboData;
    private String texto;
    
    public Conexion (VistaPrincipal vista, Cliente cliente){
        try {
      
            this.cliente = cliente;
            this.vista = vista;
            this.servidor = new DatagramSocket();
        
        } catch (SocketException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // En este hilo le mando un mensaje con "/connect" que recibe el servidor
    //
    @Override
    public void run() {
        
        byte buff[] = new byte[1024];
        
        try {
            
            // mando el codigo "/connect nombreUsuario" al servidor
            buff = (Comandos.comando[Comandos.CONECTAR] + " " + cliente.getNombre()).getBytes();
            reciboData = new DatagramPacket(buff, buff.length, InetAddress.getLocalHost(), PUERTO_REMOTO);
            vista.setMensajeChatGeneral("Se ha enviado una peticion al servidor...");
            servidor.send(reciboData);
            
            System.err.println("Puerto1: " + PUERTO_REMOTO);
            
            buff = new byte[1024];

            // si recibo "/connect" del servidor, le paso a la clase cliente la direccion y el puerto
            reciboData = new DatagramPacket (buff, buff.length);
            vista.setMensajeChatGeneral("Esperando respuesta...");
            servidor.receive(reciboData);

            cliente.setDireccion(reciboData.getAddress());
            cliente.setPuertoRemoto(reciboData.getPort());
            cliente.getServer().connect(reciboData.getAddress(), reciboData.getPort());
            
            vista.setMensajeChatGeneral(new String(reciboData.getData()));
            System.err.println("Puerto2 servidor: " + cliente.getPuertoRemoto());
            
        
        } catch (IOException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        buff = new byte[1024];
        
        while(!servidor.isClosed()){
        
            try {

                reciboData = new DatagramPacket (buff, buff.length);
                servidor.receive(reciboData);

                texto = new String(reciboData.getData());
                if (!tratarMensaje(texto)){
                    vista.setMensajeChatGeneral("Servidor: " + texto);
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
        String resul = st.nextToken();
        
        int i = 0;
        while( i < Comandos.comando.length && isComando){
            
            if (isComando && resul.equals(Comandos.comando[i])){
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
            case Comandos.LIMPIAR_CHAT:
                vista.setVaciarCajaTexto();
                break;
            case Comandos.LISTA_CLIENTES:
                obtenerLista();
                break;
            case Comandos.DESCONECTAR:
                servidor.disconnect();
                JOptionPane.showConfirmDialog(vista, "Se ha desconectado el servidor", "Información", JOptionPane.CLOSED_OPTION);
                break;
        }
    }

    private void mostrarComandos() {
        
        StringBuilder msg = new StringBuilder("Lista de comandos:\n");
        
        for (int i = 0; i < Comandos.comando.length; i++){
        
            msg.append(Comandos.comando[i]);
            
        }
        
    }

    private void obtenerLista() {
        
        StringTokenizer tk = new StringTokenizer(texto, " ");
        tk.nextToken();
        String resul = tk.nextToken();
        
        tk = new StringTokenizer(resul, "@");
        vista.setNumeroClientes(tk.countTokens());
        
        String[] lc = new String[tk.countTokens()];
        
        for (int j = 0; j < lc.length; j++) {
            lc[j] = tk.nextToken();
        }
        
        vista.setNumeroClientes(tk.countTokens());
        vista.setListaUsuarios(lc);
        
    }
    
}