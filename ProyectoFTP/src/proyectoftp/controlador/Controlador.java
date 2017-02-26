/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoftp.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.commons.net.ftp.FTPFile;
import proyectoftp.modelo.ClienteFTP;
import proyectoftp.vista.VistaPrincipal;

/**
 *
 * @author alumno2damdiurno
 */
public class Controlador extends MouseAdapter implements ActionListener {

    private VistaPrincipal vistaPrincipal;
    private ClienteFTP clienteFtp;
    private boolean logeado;
    private String nombreDirectorio;
    
    
    public Controlador(VistaPrincipal v, ClienteFTP c){
    
        this.clienteFtp = c;
        this.vistaPrincipal = v;
        
        logeado = clienteFtp.conexion();
        
        vistaPrincipal.setValorUsuario(clienteFtp.getUsuario());
        vistaPrincipal.setValorServidor(clienteFtp.ipServidor().toString());
        vistaPrincipal.setDirectorioRaiz(clienteFtp.getDirectorioActual());
        
        cargarListaDirectorioPrincipal();
        
    }

    
    
    private void cargarListaDirectorioPrincipal() {
       
        vistaPrincipal.vaciarDatos();
        
        String archivo;
        FTPFile[] archivos = clienteFtp.listaNombreDatos();
        
        for (int i=0; i<archivos.length; i++){
            
            if (archivos[i].isDirectory()){
                archivo="{DIR} " + archivos[i].getName();
                vistaPrincipal.annadirDato(archivo);
            }else{
               archivo=archivos[i].getName();
               vistaPrincipal.annadirDato(archivo);
            }
            
        }
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(e.getClickCount()==2){

            if(clienteFtp.esDirectorio(this.vistaPrincipal.getFicheroSeleccionado())){

                vistaPrincipal.setDirectorio(clienteFtp.getDirectorioActual());
                cargarListaDirectorioPrincipal();
            }

        }else{
            if(clienteFtp.esDirectorio(this.vistaPrincipal.getFicheroSeleccionado())){
            
                int numArchivos = 0;
                FTPFile[] archivos = clienteFtp.listaNombreDatos();
                numArchivos = archivos.length;
                
                if (numArchivos > 0){
                    
                    vistaPrincipal.setDirectorio(clienteFtp.getDirectorioActual() + ", " + numArchivos);
                }else{
                    
                    vistaPrincipal.setDirectorio(clienteFtp.getDirectorioActual());
                }
                
                vistaPrincipal.setDirectorioActual("/" + this.vistaPrincipal.getFicheroSeleccionado().replace("{DIR}","").trim());
                
                try {
                    clienteFtp.getConexion().changeToParentDirectory();
                } catch (IOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                
                vistaPrincipal.setDirectorio(clienteFtp.getDirectorioActual());
                vistaPrincipal.setDirectorioActual("/" + this.vistaPrincipal.getFicheroSeleccionado());
            }
        }

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
                
                descargarFichero();

                break;

            case "botonEliminarFichero":
                
                tratarBorrarFichero();

                break;

            case "botonCrearDirectorio":

                tratarCrearDirectorio();
                
                break;

            case "botonEliminarDirectorio":

                tratarEliminarDirectorio();
                
                break;

            case "botonSalir":
                clienteFtp.cerrarSesion();
                System.exit(0);
                
                break;
        }

    }
    
    private void tratarBorrarFichero() {
    	
    	String nomFich=vistaPrincipal.getFicheroSeleccionado();
    	if(nomFich!=null){
            
            try {
                if(clienteFtp.borrarFichero(nomFich)){
                    
                    JOptionPane.showMessageDialog(vistaPrincipal, "Se ha borrado correctamente");

                    cargarListaDirectorioPrincipal();
                }
                else
                    JOptionPane.showMessageDialog(vistaPrincipal, "Ha habido un error");

            } catch (IOException e) {
                e.printStackTrace();
            }
            
    	}else{
            JOptionPane.showMessageDialog(vistaPrincipal, "Selecciona uno de los ficheros de la lista");
    	}
		
    }
    
    private void descargarFichero() {
        
        int pos, correcto;
        boolean descargar;
        String fichero;
        FTPFile fFTP;

        JFileChooser exploradorArchivos = new JFileChooser();
        pos = vistaPrincipal.devolverPulsadoLista();

        if (pos <= -1) {
            JOptionPane.showMessageDialog(vistaPrincipal, "Debes de pulsar un archivo para poder descargarlo.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            fichero = vistaPrincipal.devolverObjeto(pos);
            fFTP=new FTPFile();
            fFTP.setName(fichero);
            if (fFTP.isDirectory()) {
                JOptionPane.showMessageDialog(vistaPrincipal, "Debes seleccionar un fichero para descargar.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                correcto = exploradorArchivos.showOpenDialog(vistaPrincipal);
                if (correcto == JFileChooser.APPROVE_OPTION) {
                    File file = exploradorArchivos.getSelectedFile();

                    try {

                        descargar = clienteFtp.descargarFichero(file.getCanonicalPath(), fFTP.getName());
                        comprobarDescargas(descargar);
                    } catch (IOException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        }
    }

    private void comprobarDescargas(boolean descargar) {
        
        if (descargar){
            JOptionPane.showMessageDialog(vistaPrincipal, "La descarga ha sido un éxito", "Descargas", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(vistaPrincipal, "No se ha podido realizar la descarga", "Descargas", JOptionPane.ERROR_MESSAGE);
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
        nombreDirectorio = JOptionPane.showInputDialog(vistaPrincipal, "Introduce el nombre del directório.");
        if (nombreDirectorio != null){
        nombreDirectorio = nombreDirectorio.trim();
        
            if (!nombreDirectorio.equals("")){

                logeado = clienteFtp.conexion();

                try {

                    clienteFtp.crearDirectorio(nombreDirectorio);

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Error al crear la carpeta '" + nombreDirectorio + "'");
                }

                cargarListaDirectorioPrincipal();

            }else{
                JOptionPane.showMessageDialog(vistaPrincipal, "Error, no se puede crear el directório.");
            } 
        }else{
            JOptionPane.showMessageDialog(vistaPrincipal, "Error, se ha dejado el campo vacio.");
        }
    }

    private void tratarEliminarDirectorio() {
        
        nombreDirectorio = vistaPrincipal.getFicheroSeleccionado();
        if (nombreDirectorio != null){
        String[] resultado = nombreDirectorio.split(" ", 2);

            if (resultado[0].equals("{DIR}")){

                nombreDirectorio = resultado[1];

                if (clienteFtp.conexion()){

                    try {

                        clienteFtp.eliminarDirectorio(nombreDirectorio);

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(vistaPrincipal, "Error al eliminar la carpeta '" + nombreDirectorio + "'");
                    }

                    cargarListaDirectorioPrincipal();

                }else{
                    JOptionPane.showMessageDialog(vistaPrincipal, "Error, no se puede eliminar el directório.");
                }
            }else{
                JOptionPane.showMessageDialog(vistaPrincipal, "Error, solo se pueden borrar directorios.");
            }
        }else{
            JOptionPane.showMessageDialog(vistaPrincipal, "Error, no se ha seleccionado nada.");
        }
    }
    
}
