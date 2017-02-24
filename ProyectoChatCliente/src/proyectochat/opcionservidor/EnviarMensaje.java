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
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Comandos;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class EnviarMensaje extends Thread{
    
    private VistaPrincipal vista;
    private DatagramSocket servidor;
    private String mensaje;
    private Cliente cliente;
    
    public EnviarMensaje(VistaPrincipal vista, String mensaje, Cliente cliente){
        this.cliente = cliente;
        this.vista = vista;
        this.servidor = cliente.getServer();
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        
        if (mensaje != null){

            if (!mensaje.equals("")){

                try {

                    byte buf[] = new byte[1024];
                    buf = mensaje.getBytes();

                    DatagramPacket p = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), cliente.getPuertoRemoto());
                    System.out.println(cliente.getPuertoRemoto());
                    if (tratarMensaje(mensaje)){
                    
                    }else{
                        servidor.send(p);
                        vista.setMensajeChatGeneral(cliente.getNombre() + ": " + mensaje);
                    }
                    vista.setVaciarCajaTexto();

                } catch (IOException e) {
                    JOptionPane.showConfirmDialog(vista, "El servidor no responde.", "Información", JOptionPane.CLOSED_OPTION);
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
            case Comandos.DESCONECTAR:
                Thread th = new Desconectar(cliente);
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
