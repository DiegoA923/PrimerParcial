package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.IOException;
import java.util.Properties;

/**
 * Interfaz IConexionProperties.
 * <p>
 * Define el contrato para las clases encargadas de gestionar la lectura 
 * del archivo de propiedades que contiene la información precargada de mascotas exóticas.
 * Garantiza un acceso centralizado y controlado al recurso del archivo properties.
 * </p>
 *
 * @author Sebas
 * @version 1.0
 * @since 2025-10-10
 */
public interface IConexionProperties {
    
    /**
     * Carga y devuelve el objeto Properties con los datos del archivo.
     *
     * @return un objeto {@link Properties} con los datos cargados del archivo
     * @throws Exception si ocurre un error al leer el archivo
     */
    Properties cargarProperties() throws Exception;
    
    /**
     * Obtiene la ruta del archivo de propiedades.
     *
     * @return String con la ruta del archivo
     */
    String getRutaArchivo();
}
