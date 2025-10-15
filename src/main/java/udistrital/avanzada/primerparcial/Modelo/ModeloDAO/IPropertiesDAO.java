package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.io.IOException;
import java.util.Properties;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;

/**
 * Interfaz que define las operaciones para acceder y manipular archivos de configuración
 * en formato {@link Properties}, relacionados con registros de mascotas.
 * 
 * Proporciona métodos para cargar, guardar, eliminar y actualizar entradas en el archivo
 * de propiedades.  
 *
 * @author Mauricio
 * @since 2025-10-14
 */

public interface IPropertiesDAO {

    /**
     * Obtiene el objeto {@link Properties} cargado desde el archivo de configuración.
     * 
     * @return objeto {@link Properties} con los datos cargados
     * @throws RuntimeException si ocurre un error al acceder al archivo
     */
    Properties obtenerProperties();

    /**
     * Carga el contenido actual del archivo de propiedades desde el disco.
     * 
     * @return objeto {@link Properties} con todas las claves y valores actuales
     * @throws IOException si ocurre un error al leer el archivo
     */
    Properties cargarProperties() throws IOException;

    /**
     * Guarda el contenido de un objeto {@link Properties} en el archivo de configuración.
     * 
     * @param props objeto {@link Properties} con los datos actualizados a guardar
     * @throws IOException si ocurre un error al escribir en el archivo
     */
    void guardarProperties(Properties props) throws IOException;

    /**
     * Elimina una entrada específica del archivo de propiedades.
     * 
     * @param clave clave del registro a eliminar
     * @throws IOException si ocurre un error al modificar el archivo
     */
    void eliminarMascota(String clave) throws IOException;

    /**
     * Actualiza o sobrescribe una entrada específica del archivo de propiedades con los datos
     * de una mascota.
     * 
     * @param clave clave de la mascota a actualizar
     * @param mascota objeto {@link MascotaVO} con los nuevos datos
     * @throws IOException si ocurre un error al modificar el archivo
     */
    void actualizarMascota(String clave, MascotaVO mascota) throws IOException;
}
