/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectochat.modelo;

/**
 *
 * @author samo_
 */
public class Comandos {
    
    public static final int DESCONECTAR = 0;
    public static final int LISTA_CLIENTES = 1;
    public static final int AYUDA = 2;
    public static final int CONECTAR = 3;
    public static final int ANIADIR_CLIENTE = 4;
    public static final int SILENCIAR_CLIENTE = 5;
    public static final int ELIMINAR_CLIENTE = 6;
    public static final int CANAL_PRIVADO = 7;
    public static final int CANAL_GRUPAL = 8;
    public static final int ANIADIR_PERMISOS = 9;
    public static final int ELIMINAR_PERMISOS = 10;
    public static final int ECHAR_CLIENTE = 11;
    public static final int BANEAR_CLIENTE = 12;
    public static final int LIMPIAR_CHAT = 13;
    public static final int TIEMPO_DE_ESPERA = 14;
    public static final int SILENCIAR_CANAL = 15;
    public static final int CAMBIAR_NOMBRE_CLIENTE = 16;
    
    public static final String[] comando = new String[]{
        "/disconnect",
        "/client-list",
        "/help",
        "/connect",
        "/add-client",
        "/mute-client",
        "/delete-client",
        "/private-channel",
        "/group-channel",
        "/add-permission",
        "/delete-permission",
        "/kick-client",
        "/ban-client",
        "/clean",
        "/time-lapse",
        "/mute-channel",
        "/nickname"
    };
    
}
