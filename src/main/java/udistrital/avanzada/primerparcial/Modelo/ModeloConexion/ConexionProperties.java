package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase ConexionProperties.
 * <p>
 * Implementa la interfaz {@link IConexionProperties} para gestionar 
 * la conexión y lectura del archivo de propiedades que contiene 
 * información de mascotas exóticas precargadas.
 * </p>
 * <p>
 * Esta clase se encarga únicamente de la lectura del archivo,
 * delegando el procesamiento de los datos a la capa de Control.
 * </p>
 *
 * @author sebas
 * @version 1.0
 * @since 2025-10-10
 */
public class ConexionProperties implements IConexionProperties {
    
    /** Ruta del archivo de propiedades */
    private String rutaArchivo;
    
    /**
     * Constructor por defecto.
     * Inicializa la ruta del archivo properties en la carpeta data.
     */
    public ConexionProperties() {
        this.rutaArchivo = "data/mascotas.properties";
    }
    
    /**
     * Constructor con ruta personalizada.
     *
     * @param rutaArchivo ruta del archivo de propiedades
     */
    public ConexionProperties(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Properties cargarProperties() throws Exception {
        Properties properties = new Properties();
        
        try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new Exception("Error al cargar el archivo de propiedades: " + e.getMessage(), e);
        }
        
        return properties;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getRutaArchivo() {
        return rutaArchivo;
    }
    
    /**
     * Establece la ruta del archivo de propiedades.
     *
     * @param rutaArchivo nueva ruta del archivo
     */
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}