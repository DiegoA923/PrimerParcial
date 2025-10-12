/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

/**
 *
 * @author user
 */
public class ConexionProperties implements IConexion {

    private Properties properties;
    private String archivo;

    public ConexionProperties(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public void desconectar() {
        properties = null;
    }

    @Override
    public Connection getConexion() {
        
        return null;
        
    }

}
