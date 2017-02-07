/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Controlador implements ActionListener{

    public VistaPrincipal vista;
    
    public Controlador(VistaPrincipal vista){
        this.vista = vista;
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "enviar":
                
                break;
                
            case "mostrar":
                break;
                
            case "parar":
                break;
                
            case "salir":
                break;
        }
    }
    
}
