/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import proyectochat.controlador.Controlador;
import proyectochat.modelo.Cliente;
import proyectochat.opcionservidor.Conexion;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class ProyectoChat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame ventana = new JFrame("Cliente");
        
        // Si no inserta un nombre, no puede iniciar el cliente
        //
        String nombre = "";
        
        while(nombre.equals("")){
            nombre = JOptionPane.showInputDialog(ventana, "Nombre de usuario: ", "Conexión", JOptionPane.OK_OPTION);
            nombre = nombre.replace("+-*/=%&#!?^“‘~\\|<>()[]{}:;.,", nombre);
            nombre = nombre.trim();
        }
        
        Cliente cliente = new Cliente(nombre);
        VistaPrincipal vista = new VistaPrincipal();
        Controlador c = new Controlador(vista, cliente);
        
        vista.addControlador(c);
        ventana.addWindowListener(c);
        
        ventana.add(vista);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(800, 500);
        ventana.setVisible(true);
        ventana.setMinimumSize(new Dimension(500, 200));
        
        // Iniciamos un hilo que escucha siempre al servidor
        //
        Thread th = new Conexion(vista, cliente);
        th.start();
       
    }
    
}
