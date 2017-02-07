/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.controlador.Controlador;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Comprobacion extends Thread{
    
    private VistaPrincipal vista;
    private ServidorUDP servidor;
    private Controlador controlador;

    public Comprobacion(VistaPrincipal vista, ServidorUDP servidor, Controlador controlador){
        this.servidor = servidor;
        this.vista = vista;
        this.controlador = controlador;
    }
    
    @Override
    public void run() {
        
        while(true){
            
            comprobarEstadoServidor();
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Comprobacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            comprobarListaClientes();
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Comprobacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    private void comprobarEstadoServidor() {
        if (servidor.isActive()){
            vista.setTextoEstado("Conectado");
            vista.setBtnPararText("Parar");
        }else{
            vista.setTextoEstado("desconectado");
            vista.setBtnPararText("Iniciar");
        }
        
    }

    private void comprobarListaClientes() {
        
        if (servidor.isActive()){
            controlador.mostrarUsuarios();
            
        }else{}
        
    }
    
    
    
}
