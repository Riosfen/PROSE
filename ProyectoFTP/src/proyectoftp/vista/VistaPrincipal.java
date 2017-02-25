/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoftp.vista;

import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;

/**
 *
 * @author alumno2damdiurno
 */
public class VistaPrincipal extends javax.swing.JPanel {

    /**
     * Creates new form VistaPrincipal
     */
    
    private DefaultListModel modelo;
    
    public VistaPrincipal() {
        initComponents();
        
        modelo = new DefaultListModel();
        jListItems.setModel(modelo);
    }
    
    public void addControlador(ActionListener e){
        
        this.jButtonSubirFichero.addActionListener(e);
        this.jButtonSubirFichero.setActionCommand("botonSubirFichero");
        
        this.jButtonDescargarFichero.addActionListener(e);
        this.jButtonDescargarFichero.setActionCommand("botonDescargarFichero");
        
        this.jButtonEliminarFichero.addActionListener(e);
        this.jButtonEliminarFichero.setActionCommand("botonEliminarFichero");
        
        this.jButtonCrearDirectorio.addActionListener(e);
        this.jButtonCrearDirectorio.setActionCommand("botonCrearDirectorio");
        
        this.jButtonEliminarDirectorio.addActionListener(e);
        this.jButtonEliminarDirectorio.setActionCommand("botonEliminarDirectorio");
        
        this.jButtonSalir.addActionListener(e);
        this.jButtonSalir.setActionCommand("botonSalir");
        
    }
    
    public void annadirDato(String dato){
        
        modelo.addElement(dato);    
        
    }
    
    public void vaciarDatos(){
        modelo.clear();
    }
    
    public void setValorServidor(String direccionFtp){
        
        this.jLabelServidorValor.setText(direccionFtp);
        
    }
    
    public void setDirectorioRaiz(String directorio){
        
        this.jLabelDirectorioRaizValor.setText(directorio);
    }
    
    public void setValorUsuario(String usu){
        
        this.jLabelUsuarioValor.setText(usu);
    }
    
    public void setDirectorio(String directorio){
        
        this.jLabelDirectorioValor.setText(directorio);
    }
    
    public void setDirectorioActual(String directorioActual){
        
        this.jLabelDirectorioActualValor.setText(directorioActual);
    }
    
    public void setJLista(String[] lista){
        
        this.jListItems.setListData(lista);
        
    }
    
    public int devolverPulsadoLista(){
        
        return jListItems.getSelectedIndex();
    }
    
    public String devolverObjeto(int pos){
        
        String fichero;
        
        fichero=String.valueOf(modelo.get(pos));
        
        return fichero;
    }
    
    public String getFicheroSeleccionado(){
        
    	if(jListItems.isSelectionEmpty())
    		return null;
    	else
    			
    	return jListItems.getSelectedValue().toString();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelServidor = new javax.swing.JLabel();
        jLabelServidorValor = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabelUsuario = new javax.swing.JLabel();
        jLabelUsuarioValor = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabelDirectorioRaiz = new javax.swing.JLabel();
        jLabelDirectorioRaizValor = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabelDirectorio = new javax.swing.JLabel();
        jLabelDirectorioValor = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jLabelDirectorioActual = new javax.swing.JLabel();
        jLabelDirectorioActualValor = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButtonSubirFichero = new javax.swing.JButton();
        jButtonDescargarFichero = new javax.swing.JButton();
        jButtonEliminarFichero = new javax.swing.JButton();
        jButtonCrearDirectorio = new javax.swing.JButton();
        jButtonEliminarDirectorio = new javax.swing.JButton();
        jButtonSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListItems = new javax.swing.JList();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(2, 2));

        jLabelServidor.setText("SERVIDOR: ");
        jPanel4.add(jLabelServidor);

        jLabelServidorValor.setText("valor");
        jPanel4.add(jLabelServidorValor);

        jPanel1.add(jPanel4);

        jLabelUsuario.setText("USUARIO: ");
        jPanel5.add(jLabelUsuario);

        jLabelUsuarioValor.setText("valor");
        jPanel5.add(jLabelUsuarioValor);

        jPanel1.add(jPanel5);

        jLabelDirectorioRaiz.setText("DIRECTORIO RAÍZ: ");
        jPanel6.add(jLabelDirectorioRaiz);

        jLabelDirectorioRaizValor.setText("valor");
        jPanel6.add(jLabelDirectorioRaizValor);

        jPanel1.add(jPanel6);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.GridLayout(4, 1));
        jPanel2.add(jSeparator2);

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jLabelDirectorio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDirectorio.setText("DIRECTORIO: ");
        jPanel7.add(jLabelDirectorio);

        jLabelDirectorioValor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelDirectorioValor.setText("valor");
        jPanel7.add(jLabelDirectorioValor);

        jPanel2.add(jPanel7);
        jPanel2.add(jSeparator1);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jLabelDirectorioActual.setText("DIRECTORIO ACTUAL: ");
        jPanel8.add(jLabelDirectorioActual);

        jLabelDirectorioActualValor.setText("valor");
        jPanel8.add(jLabelDirectorioActualValor);

        jPanel2.add(jPanel8);

        add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setLayout(new java.awt.GridLayout(6, 0));

        jButtonSubirFichero.setText("Subir fichero");
        jPanel3.add(jButtonSubirFichero);

        jButtonDescargarFichero.setText("Descargar fichero");
        jPanel3.add(jButtonDescargarFichero);

        jButtonEliminarFichero.setText("Eliminar fichero");
        jPanel3.add(jButtonEliminarFichero);

        jButtonCrearDirectorio.setText("Crear directorio");
        jPanel3.add(jButtonCrearDirectorio);

        jButtonEliminarDirectorio.setText("Eliminar directorio");
        jPanel3.add(jButtonEliminarDirectorio);

        jButtonSalir.setText("Salir");
        jPanel3.add(jButtonSalir);

        add(jPanel3, java.awt.BorderLayout.LINE_END);

        jScrollPane1.setViewportView(jListItems);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCrearDirectorio;
    private javax.swing.JButton jButtonDescargarFichero;
    private javax.swing.JButton jButtonEliminarDirectorio;
    private javax.swing.JButton jButtonEliminarFichero;
    private javax.swing.JButton jButtonSalir;
    private javax.swing.JButton jButtonSubirFichero;
    private javax.swing.JLabel jLabelDirectorio;
    private javax.swing.JLabel jLabelDirectorioActual;
    private javax.swing.JLabel jLabelDirectorioActualValor;
    private javax.swing.JLabel jLabelDirectorioRaiz;
    private javax.swing.JLabel jLabelDirectorioRaizValor;
    private javax.swing.JLabel jLabelDirectorioValor;
    private javax.swing.JLabel jLabelServidor;
    private javax.swing.JLabel jLabelServidorValor;
    private javax.swing.JLabel jLabelUsuario;
    private javax.swing.JLabel jLabelUsuarioValor;
    private javax.swing.JList jListItems;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables

    
}
