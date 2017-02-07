/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

import java.net.DatagramPacket;

/**
 *
 * @author samo_
 */
public class Cliente {
    
    private String nombre;
    private DatagramPacket datagrama;
    
    public Cliente(String nombre, DatagramPacket datagrama){
        this.nombre = nombre;
        this.datagrama = datagrama;
    }

    public DatagramPacket getDatagrama() {
        return datagrama;
    }

    public String getNombre() {
        return nombre;
    }
    
    
    
}
