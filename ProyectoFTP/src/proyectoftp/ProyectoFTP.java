/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoftp;

import proyectoftp.vista.VistaPrincipal;
import javax.swing.JFrame;
import proyectoftp.controlador.Controlador;
import proyectoftp.modelo.ClienteFTP;

/**
 *
 * @author alumno2damdiurno
 */
public class ProyectoFTP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame ventana = new JFrame("Cliente FTP");
        VistaPrincipal vista = new VistaPrincipal();
        ClienteFTP cliente = new ClienteFTP();
        Controlador controlador = new Controlador(vista, cliente);
        
        vista.addControlador(controlador);
        vista.controladorLista(controlador);
        
        
        ventana.add(vista);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.pack();
        ventana.setVisible(true);
        
    }
    
}
