/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectochat.opcionservidor.EnviarMensaje;

/**
 *
 * @author samo_
 */
public class Clientes {
    
    private ArrayList<Cliente> clientes;
    
    public Clientes(){
        clientes = new ArrayList<Cliente>();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    public Cliente getCliente(int pos){
        return clientes.get(pos);
    }
    public void addCliente(Cliente cliente){
        ObjectOutputStream out = null;
        try {
            
            clientes.add(cliente);
            
            //TODO aqui se manda junto con la lista de clientes un array de string con los nombres de los clientes
            ByteArrayOutputStream contentStream = new ByteArrayOutputStream();
            out = new ObjectOutputStream(contentStream);
            out.writeObject(getNickClientes());
            out.flush();
            out.close();
            
            enviarMulticast(Comandos.comando[Comandos.LISTA_CLIENTES]);
            
        } catch (IOException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    public String[] getNickClientes(){
        String[] listaClientes = new String[clientes.size()];
        
        for (int i = 0; 0 < clientes.size() ; i++) {
            listaClientes[i] = clientes.get(i).getNombre();
        }
        
        return listaClientes;
    }
    
    public void enviarMulticast(String mensaje){
        
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            
            Thread th = new EnviarMensaje(c.getServer(), mensaje);
            
        }
    }

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);
        enviarMulticast(Comandos.comando[Comandos.LISTA_CLIENTES]);
    }
    
}
