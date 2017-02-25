/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoftp.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import proyectoftp.modelo.ClienteFTP;
import proyectoftp.vista.VistaPrincipal;

/**
 *
 * @author alumno2damdiurno
 */
public class Controlador implements ActionListener {

    private VistaPrincipal vistaPrincipal;
    private ClienteFTP clienteFtp;
    private boolean logeado;
    private String nombreDirectorio;
    
    
    public Controlador(VistaPrincipal v, ClienteFTP c) {
        this.clienteFtp = c;
        this.vistaPrincipal = v;
        
        cargarDatosConexion();
    
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        String id;

        id = e.getActionCommand();

        switch (id) {

            case "botonSubirFichero":
                
                tratarSubirArchivo();
                
                break;

            case "botonDescargarFichero":

                break;

            case "botonEliminarFichero":

                break;

            case "botonCrearDirectorio":

                tratarCrearDirectorio();
                
                break;

            case "botonEliminarDirectorio":

                tratarEliminarDirectorio();
                
                break;

            case "botonSalir":

                System.exit(0);
                
                break;
        }

    }
    
    private void tratarSubirArchivo(){
        int correcto;
        
        JFileChooser exploradorArchivos = new JFileChooser();
        
        correcto = exploradorArchivos.showOpenDialog(vistaPrincipal);
        
        if(correcto == JFileChooser.APPROVE_OPTION){
            
            
            File file = exploradorArchivos.getSelectedFile();
            
            
            
            if(logeado){
                
                try {
                    
                    clienteFtp.subirFichero(file);
                    
                } catch (IOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }else{
                
                JOptionPane.showMessageDialog(vistaPrincipal, "Imposible conectarse");
            }
            
            
            
        }
    }

    private void tratarCrearDirectorio() {
        
        nombreDirectorio = "/" + JOptionPane.showInputDialog(vistaPrincipal, "Introduce el nombre del directório.");
        
        logeado = clienteFtp.conexion();

        try {
            
            clienteFtp.crearDirectorio(nombreDirectorio);
        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(vistaPrincipal, "Error al crear la carpeta '" + nombreDirectorio + "'");
        }
        
    }

    private void tratarEliminarDirectorio() {
        
        nombreDirectorio = JOptionPane.showInputDialog(vistaPrincipal, "Introduce el nombre del directório.", "Eliminar Directório", JOptionPane.OK_CANCEL_OPTION);
       
        logeado = clienteFtp.conexion();

        try {
            
            clienteFtp.eliminarDirectorio(nombreDirectorio);
        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(vistaPrincipal, "Error al eliminar la carpeta '" + nombreDirectorio + "'");
        }
        
    }

    private void cargarDatosConexion() {
        
        clienteFtp.conexion();
        
        vistaPrincipal.setValorServidor("" + clienteFtp.ipServidor());
        vistaPrincipal.setValorUsuario(clienteFtp.getUsuario());
        vistaPrincipal.setValorDirectorioRaiz(clienteFtp.getDirectorioRaiz());
        vistaPrincipal.setJLista(clienteFtp.listaNombreDatos());
        
        clienteFtp.cerrarSesion();
        
    }

}
