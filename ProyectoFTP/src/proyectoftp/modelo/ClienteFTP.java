/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoftp.modelo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.*;



/**
 *
 * @author alumno2damdiurno
 */
public class ClienteFTP {
    
    private FTPClient clienteFtp;
    private boolean logeado;
    private static final String FTP = "josemanuelarahal.esy.es";
    private static final String USU = "u599502366";
    private static final String PASSWORD = "aprobado2016";
    
    public ClienteFTP(){
        clienteFtp = new FTPClient();
    }
    
    public InetAddress ipServidor(){
        
        return clienteFtp.getLocalAddress();
    }
    
    public String getUsuario(){
        
        return USU;
    }
    
    public String getDirectorioRaiz(){
        
        String raiz = null;
        
        try {
            
            clienteFtp.changeWorkingDirectory("/public_html/ftpProyecto");

            raiz = clienteFtp.printWorkingDirectory();
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return raiz;
    }
    
    public FTPClient getConexion(){
        
        return clienteFtp;
    }
    
    public boolean conexion(){
        
        try {
            clienteFtp.connect(InetAddress.getByName(FTP));
            
            
            logeado = clienteFtp.login(USU, PASSWORD);   
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return logeado;
    }
    
    public void cerrarSesion(){
        
        try {
            
            clienteFtp.logout();    
            clienteFtp.disconnect();
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public void subirFichero(File file) throws IOException {

        
         int reply = clienteFtp.getReplyCode();
            
            System.out.println("Respuesta recibida de conexión FTP:" + reply);
            
            if(FTPReply.isPositiveCompletion(reply)){
                System.out.println("Conectado Satisfactoriamente");    
            
            }else{
                System.out.println("Imposible conectarse al servidor");
                
            }
        
        
        
        
        
        clienteFtp.setFileType(FTPClient.BINARY_FILE_TYPE);
        BufferedInputStream filtroLectura = new BufferedInputStream(new FileInputStream(file));
        
        clienteFtp.enterRemoteActiveMode(InetAddress.getByName(FTP),21);
        
        clienteFtp.storeFile("/public_html/ftpProyecto/"+file.getName(), filtroLectura);
     
        System.err.println("DENTRO");
        
        filtroLectura.close();
    }
    
    public void cambiarDirecctorio(){
        
        try {
            clienteFtp.changeWorkingDirectory("/public_html/ftpProyecto");
        } catch (IOException ex) {
            Logger.getLogger(ClienteFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    public String[] listaNombreDatos() {

        String[] lista = null;
        
        try {
            lista = clienteFtp.listNames();
        } catch (IOException ex) {
            Logger.getLogger(ClienteFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lista;
    }

    public void crearDirectorio(String nombreDirectorio) throws IOException {
    
        clienteFtp.makeDirectory(nombreDirectorio);
        
    }

    public void eliminarDirectorio(String nombreDirectorio) throws IOException {
        
        clienteFtp.removeDirectory(rutaActual() + nombreDirectorio);
        
    }

    private String rutaActual() {
        String ruta = "/";
        
        
        
        return ruta;
    }
    
    
}
