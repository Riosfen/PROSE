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
    
    
    public Controlador(VistaPrincipal v, ClienteFTP c) {
    
        this.clienteFtp = c;
        this.vistaPrincipal = v;
    
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {

        String id;

        id = e.getActionCommand();

        switch (id) {

            case "botonSubirFichero":

                int correcto;
                
                JFileChooser exploradorArchivos = new JFileChooser();
                
                correcto = exploradorArchivos.showOpenDialog(vistaPrincipal);
                
                if(correcto == JFileChooser.APPROVE_OPTION){
                    
                   
                    File file = exploradorArchivos.getSelectedFile();
                    
                    logeado = clienteFtp.conexion();
                    
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
                
                
                break;

            case "botonDescargarFichero":

                break;

            case "botonEliminarFichero":

                break;

            case "botonCrearDirectorio":

                break;

            case "botonEliminarDirectorio":

                break;

            case "botonSalir":

                System.exit(0);
                
                break;
        }

    }

}
