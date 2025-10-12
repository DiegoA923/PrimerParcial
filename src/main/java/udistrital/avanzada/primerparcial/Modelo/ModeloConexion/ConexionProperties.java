/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import udistrital.avanzada.primerparcial.Modelo.interfaz.IConexion;

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
    public void conectar() throws IOException {
        properties = new Properties();
        try(FileInputStream entrada = new FileInputStream(archivo)){
            properties.load(entrada);
        }
    }
    
    @Override
    public void desconectar() throws IOException{
        properties = null;
    }

}
