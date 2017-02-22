/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import proyectochat.opcionservidor.Conectar;
import proyectochat.modelo.ServidorUDP;
import proyectochat.opcionservidor.Desconectar;
import proyectochat.opcionservidor.EnviarMensaje;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Controlador extends WindowAdapter implements ActionListener{

    private VistaPrincipal vista;
    private ServidorUDP servidor;
    private Thread th;
    
    public Controlador(VistaPrincipal vista, ServidorUDP servidor){
        this.vista = vista;
        this.servidor = servidor;
    
    }

    @Override
    public void windowClosed(WindowEvent e) {
        th = new Desconectar(vista, servidor);
        th.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "enviar":
                String texto = vista.getTextoEnviar().trim();
                th = new EnviarMensaje(vista, servidor, texto);
                th.start();
                break;
                
            case "salir":
                th = new Desconectar(vista, servidor);
                th.start();
                System.exit(0);
                break;
                
            case "conectar":
                th = new Conectar(vista, servidor);
                th.start();
                break;
                
            case "desconectar":
                th = new Desconectar(vista, servidor);
                th.start();
                break;
                
            case "usuarios":
                mostrarUsuarios();
                break;
                
            case "nombre":
                    
                servidor.setNombre(JOptionPane.showInputDialog(vista, "Nombre de usuario: ", "Cambiar nombre", JOptionPane.INFORMATION_MESSAGE));
                
                
                
                
                break;
        }
        
    }

    private void mostrarUsuarios() {
        
    }
    
}
