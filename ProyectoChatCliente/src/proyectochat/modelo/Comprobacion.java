/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Comprobacion extends Thread{
    
    private VistaPrincipal vista;
    private ServidorUDP servidor;

    public Comprobacion(VistaPrincipal vista, ServidorUDP servidor){
        this.servidor = servidor;
        this.vista = vista;
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
            
        }
        
    }

    private void comprobarEstadoServidor() {
        if (servidor.isActive()){
            vista.setTextoEstado("Conectado");
        }else{
            vista.setTextoEstado("desconectado");
        }
        
    }
    
    
    
}
