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
import java.io.InputStream;
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
    private static final String FTP = "ftp.victormanuel-carmona.esy.es";
    private static final String USU = "u329292900.usuarioftp";
    private static final String PASSWORD = "usuftp";
    
    public ClienteFTP(){
        clienteFtp = new FTPClient();
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

        clienteFtp.setFileType(FTPClient.BINARY_FILE_TYPE,FTPClient.BINARY_FILE_TYPE);
        BufferedInputStream filtroLectura = new BufferedInputStream(new FileInputStream(file));
        
        clienteFtp.enterRemotePassiveMode();
        
        clienteFtp.storeFile("http://victormanuel-carmona.esy.es/FTP-PSP/"+file.getName(), filtroLectura);
     
          System.err.println("DENTRO");
        
        filtroLectura.close();
    }
    
    
}
