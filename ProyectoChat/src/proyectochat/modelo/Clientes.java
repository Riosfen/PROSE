/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.util.ArrayList;
import proyectochat.opcionservidor.EnviarMensaje;
import proyectochat.vista.VistaPrincipal;

/**
 *
 * @author samo_
 */
public class Clientes {
    
    private ArrayList<Cliente> clientes;
    private VistaPrincipal vista;
    
    public Clientes(VistaPrincipal vista){
        this.vista = vista;
        clientes = new ArrayList<Cliente>();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public Cliente getCliente(int pos){
        return clientes.get(pos);
    }
    public void addCliente(Cliente cliente){
        clientes.add(cliente);
        vista.setListaUsuarios(getNickClientes());
        enviarMulticast(Comandos.comando[Comandos.LISTA_CLIENTES]);
        
    }
    public String[] getNickClientes(){
        String[] listaClientes = new String[clientes.size()];
        
        for (int i = 0; i < clientes.size() ; i++) {
            listaClientes[i] = clientes.get(i).getNombre();
        }
        
        return listaClientes;
    }
    
    public void enviarMulticast(String mensaje){
        
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            
            Thread th = new EnviarMensaje(c, mensaje);
            th.start();
            
        }
    }
    
    public void enviarListaClientes(){
        
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            
            sb.append(c.getNick() + "@");
            
        }
        
        enviarMulticast(Comandos.comando[Comandos.LISTA_CLIENTES] + " " + sb.toString());
        
    }

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);
        enviarMulticast(Comandos.comando[Comandos.LISTA_CLIENTES]);
    }
    
}
