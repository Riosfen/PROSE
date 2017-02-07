/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.modelo.Cliente;
import proyectochat.modelo.Clientes;
import proyectochat.modelo.ServidorUDP;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Controlador implements ActionListener{

    private VistaPrincipal vista;
    private Clientes clientes;
    private ServidorUDP servidor;
    
    public Controlador(VistaPrincipal vista, Clientes clientes, ServidorUDP servidor){
        this.vista = vista;
        this.clientes = clientes;
        this.servidor = servidor;
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "enviar":
                String texto = vista.getTextoEnviar().trim();
                if (!texto.equals("")){
                    
                    try {
                        clientes.enviarMulticast("Servidor: "+texto, servidor.getServidorUDP());
                    } catch (IOException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    vista.setMensajeChatGeneral("Tú: " + texto);
                    vista.setVaciarCajaTexto();
                }
                break;
                
            case "mostrar":
                mostrarUsuarios();
                    
                break;
                
            case "parar":
                
                if (servidor.isActive()){
                try {
                    servidor.cerrarServidor();
                } catch (IOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                    vista.setBtnPararText("Iniciar");
                }else{
                    servidor.iniciarServidor();
                    vista.setBtnPararText("Parar");
                }
                
                break;
                
            case "salir":
                try {
                    servidor.cerrarServidor();
                } catch (IOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                
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
