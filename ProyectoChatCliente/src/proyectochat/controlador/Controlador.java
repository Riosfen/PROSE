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
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Comandos;
import proyectochat.opcionservidor.Desconectar;
import proyectochat.opcionservidor.EnviarMensaje;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Controlador extends WindowAdapter implements ActionListener{

    private VistaPrincipal vista;
    private Cliente cliente;
    private Thread th;
    
    public Controlador(VistaPrincipal vista, Cliente cliente){
        this.cliente = cliente;
        this.vista = vista;
    
    }

    @Override
    public void windowClosed(WindowEvent e) {
        th = new Desconectar(cliente);
        th.start();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "enviar":
                String texto = vista.getTextoEnviar().trim();
                th = new EnviarMensaje(vista, texto, cliente);
                th.start();
                break;
                
            case "salir":
                th = new Desconectar(cliente);
                th.start();
                System.exit(0);
                break;
                
            case "desconectar":
                th = new Desconectar(cliente);
                th.start();
                break;
                
            case "usuarios":
                Thread th = new EnviarMensaje(vista, Comandos.comando[Comandos.LISTA_CLIENTES], cliente);
                th.start();
                break;
                
            case "nombre":
                String resultado = JOptionPane.showInputDialog(vista, "Nombre de usuario: ", "Cambiar nombre", JOptionPane.INFORMATION_MESSAGE);
                if (resultado != null){
                    th = new EnviarMensaje(vista, Comandos.comando[Comandos.CAMBIAR_NOMBRE_CLIENTE] + " " + resultado, cliente);
                    th.start();
                }
                
                break;
        }
        
    }
    
}
