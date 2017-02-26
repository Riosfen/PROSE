/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoftp.modelo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private String rutaActual = "/public_html/ftpProyecto";
    
    public ClienteFTP(){
        clienteFtp = new FTPClient();
    }
    
    public InetAddress ipServidor(){
        
        return clienteFtp.getLocalAddress();
    }
    
    public String getUsuario(){
        
        return USU;
    }
    
    public String getDirectorioActual(){
        
        String raiz = null;
        
        try {
            
            if (clienteFtp.changeWorkingDirectory(rutaActual)){
                raiz = clienteFtp.printWorkingDirectory();
                System.err.println("entro");
            }else{
                System.out.println("Error, no se puede acceder a '" + rutaActual + "'.");
            }

            
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
            
            clienteFtp.enterLocalPassiveMode();
            
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
            
            if(FTPReply.isPositiveCompletion(reply))
            {
                System.out.println("Conectado Satisfactoriamente");    
            }
            else
                {
                    System.out.println("Imposible conectarse al servidor");
                }
        
        
        
        
        
        clienteFtp.setFileType(FTPClient.BINARY_FILE_TYPE);
        BufferedInputStream filtroLectura = new BufferedInputStream(new FileInputStream(file));
        
        clienteFtp.enterRemoteActiveMode(InetAddress.getByName(FTP),21);
        
        clienteFtp.storeFile("/public_html/ftpProyecto/"+file.getName(), filtroLectura);
     
          System.err.println("DENTRO");
        
        filtroLectura.close();
    }

    public FTPFile[] listaNombreDatos() {

        FTPFile[] lista = null;
        
        try {
            lista = clienteFtp.listFiles();
        } catch (IOException ex) {
            Logger.getLogger(ClienteFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return lista;
    }

    public void crearDirectorio(String nombreDirectorio) throws IOException {
    
        boolean correcto = clienteFtp.makeDirectory(getDirectorioActual() + nombreDirectorio);
        
        if (correcto) {
            System.out.println("El directorio se ha creado correctamente: " + nombreDirectorio);
        } else {
            System.out.println("Error al crear el directorio '" + nombreDirectorio + "'.");
        }
        
    }

    public void eliminarDirectorio(String nombreDirectorio) throws IOException {
        
        boolean correcto = clienteFtp.removeDirectory(getDirectorioActual() + nombreDirectorio);
        
        if (correcto) {
            System.out.println("El directorio se ha borrado correctamente: " + nombreDirectorio);
        } else {
            System.out.println("Error al borrar el directorio'" + nombreDirectorio + "'.");
        }
        
    }
    
    private void cambiarDirectorio(String direccion) {
        rutaActual = rutaActual + direccion + "/";
        
        getDirectorioActual();
    }

    public boolean descargarFichero(String ruta, String fichero) throws FileNotFoundException, IOException{
      
        boolean descargar;
        
        FileOutputStream fos = new FileOutputStream(ruta);
        descargar=clienteFtp.retrieveFile("/public_html/ftpProyecto/" + fichero, fos);
            
        return descargar;
       
    }
    
    public boolean borrarFichero(String nombreFichero) throws IOException{
        
    return clienteFtp.deleteFile(nombreFichero);	
			
    }
    
    public boolean esDirectorio(String aux) {

    	boolean encontrado=false;
    	
    	if(aux.startsWith("{DIR}"))
    	{
    		encontrado=true;
    		aux=aux.replace("{DIR}","").trim();
    		try {
    			if(aux.equalsIgnoreCase(".")){
                            
                            clienteFtp.changeToParentDirectory();
                            rutaActual = clienteFtp.printWorkingDirectory();
    				
    			}else if(aux.equalsIgnoreCase("..")){
                            
                            clienteFtp.changeToParentDirectory();
                            clienteFtp.changeToParentDirectory();
                            rutaActual = clienteFtp.printWorkingDirectory();
                            
                        }else {

                            rutaActual = clienteFtp.printWorkingDirectory()+"/"+aux;
                            clienteFtp.changeWorkingDirectory(clienteFtp.printWorkingDirectory()+"/"+aux);

                        }
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    	}
    	
    	return encontrado;
    }
    
}
