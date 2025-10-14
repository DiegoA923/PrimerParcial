package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.util.Properties;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IConexionProperties;

/**
 * Clase PropertiesDAO.
 * <p>
 * DAO que gestiona el acceso al archivo de propiedades.
 * Aplica el patrón DAO para encapsular el acceso a la fuente de datos (archivo properties).
 * </p>
 * <p>
 * Responsabilidad única: obtener el objeto {@link Properties} desde la conexión.
 * No realiza procesamiento de datos ni iteraciones; esa responsabilidad 
 * corresponde a la capa de Control.
 * </p>
 *
 * @author sebas
 * @version 2.1
 * @since 2025-10-11
 */
public class PropertiesDAO {

    private final IConexionProperties conexionProperties;

    public PropertiesDAO(IConexionProperties conexionProperties) {
        this.conexionProperties = conexionProperties;
    }

    /**
     * Obtiene el objeto {@link Properties} desde el archivo.
     * <p>
     * Este método delega la lectura del archivo a la clase de conexión
     * y retorna el objeto Properties sin procesarlo.
     * </p>
     *
     * @return objeto {@link Properties} con los datos del archivo
     * @throws RuntimeException si ocurre un error al leer el archivo
     */
    public Properties obtenerProperties() {
        try {
            return conexionProperties.cargarProperties();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener properties: " + e.getMessage(), e);
        }
    }
}
