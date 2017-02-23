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
import java.util.ArrayList;
import java.util.Iterator;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Clientes;
import proyectochat.modelo.Comandos;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Controlador extends WindowAdapter implements ActionListener{

    private VistaPrincipal vista;
    private Clientes clientes;
    private ServidorUDP servidor;
    
    public Controlador(VistaPrincipal vista, Clientes clientes, ServidorUDP servidor){
        this.vista = vista;
        this.clientes = clientes;
        this.servidor = servidor;
    
    }

    @Override
    public void windowClosed(WindowEvent e) {
        clientes.enviarMulticast(Comandos.comando[Comandos.DESCONECTAR]);
        System.exit(0);
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "enviar":
                String texto = vista.getTextoEnviar().trim();
                if (!texto.equals("")){
                    
                    clientes.enviarMulticast("Servidor: "+texto);
                    
                    vista.setMensajeChatGeneral("Servidor: " + texto);
                    vista.setVaciarCajaTexto();
                }
                break;
                
            case "mostrar":
                mostrarUsuarios();
                break;
                
            case "parar":
                clientes.enviarMulticast(Comandos.comando[Comandos.DESCONECTAR]);
                
                break;
                
            case "salir":
                clientes.enviarMulticast(Comandos.comando[Comandos.DESCONECTAR]);
                servidor.getServidorUDP().close();
                System.exit(0);
                break;
        }
    }

    public void mostrarUsuarios() {
        ArrayList<Cliente> lCliente = clientes.getClientes();
                    
        ArrayList<String> usuarios = new ArrayList<String>();

        if (lCliente.isEmpty()){
            vista.setListaUsuarios(usuarios);
        }else{
            for (Iterator<Cliente> iterator = lCliente.iterator(); iterator.hasNext();) {
                Cliente cliente = iterator.next();

                usuarios.add(cliente.getNombre());

            }
            vista.setListaUsuarios(usuarios);

        }
    }
    
}
