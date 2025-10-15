package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IConexionProperties;

/**
 * Clase PropertiesDAO.
 * <p>
 * DAO encargado de gestionar el acceso y modificación del archivo
 * {@code configuracion.properties}, que almacena los datos precargados de las
 * mascotas exóticas.
 * </p>
 *
 * <p>
 * Este DAO provee operaciones CRUD sobre el archivo de propiedades, tales como
 * obtener, actualizar, eliminar o guardar los registros de las mascotas. Su
 * responsabilidad es únicamente el acceso al archivo; la lógica de negocio
 * recae en la capa de Control.
 * </p>
 *
 * @author Sebas 
 * @version 3.0
 * @since 2025-10-11
 */
public class PropertiesDAO implements IPropertiesDAO {

    /**
     * Conexión al archivo de propiedades.
     */
    private final IConexionProperties conexionProperties;

    /**
     * Constructor del DAO.
     *
     * @param conexionProperties objeto de conexión encargado de proporcionar la
     * ruta y los métodos de acceso al archivo
     */
    public PropertiesDAO(IConexionProperties conexionProperties) {
        this.conexionProperties = conexionProperties;
    }

    /**
     * Obtiene el objeto {@link Properties} desde el archivo.
     * <p>
     * Este método delega la lectura del archivo a la clase de conexión y
     * retorna el objeto Properties sin procesarlo.
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

    /**
     * Carga el contenido actual del archivo {@code configuracion.properties}.
     *
     * @return objeto {@link Properties} con todas las claves y valores actuales
     * @throws IOException si ocurre un error de lectura del archivo
     */
    public Properties cargarProperties() throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(conexionProperties.getRutaArchivo())) {
            props.load(in);
        }
        return props;
    }

    /**
     * Guarda los cambios realizados en el objeto {@link Properties} dentro del
     * archivo {@code configuracion.properties}.
     *
     * @param props objeto de propiedades actualizado
     * @throws IOException si ocurre un error de escritura
     */
    public void guardarProperties(Properties props) throws IOException {
        try (FileOutputStream out = new FileOutputStream(conexionProperties.getRutaArchivo())) {
            props.store(out, "Actualizado automáticamente al completar mascotas");
        }
    }

    /**
     * Elimina un registro específico del archivo de propiedades.
     * <p>
     * Se utiliza cuando una mascota ha sido completada e insertada
     * correctamente en la base de datos, evitando que vuelva a cargarse en
     * futuras ejecuciones.
     * </p>
     *
     * @param clave la clave del registro a eliminar (por ejemplo, "Animal3")
     * @throws IOException si ocurre un error al modificar el archivo
     */
    public void eliminarMascota(String clave) throws IOException {
        Properties props = cargarProperties();
        props.remove(clave);
        guardarProperties(props);
    }

    /**
     * Actualiza o sobrescribe la información de una mascota dentro del archivo
     * de propiedades. Este método permite conservar el registro actualizado con
     * los nuevos valores completados por el usuario.
     *
     * @param clave clave de la mascota (por ejemplo, "Animal3")
     * @param mascota objeto {@link MascotaVO} con los datos actualizados
     * @throws IOException si ocurre un error al escribir en el archivo
     */
    public void actualizarMascota(String clave, MascotaVO mascota) throws IOException {
        Properties props = cargarProperties();

        String valor = String.join(",",
                mascota.getNombreComun(),
                mascota.getApodo(),
                mascota.getClasificacion() != null ? mascota.getClasificacion().name() : "",
                mascota.getFamilia(),
                mascota.getGenero(),
                mascota.getEspecie(),
                mascota.getTipoAlimento() != null ? mascota.getTipoAlimento().name() : ""
        );

        props.setProperty(clave, valor);
        guardarProperties(props);
    }
}
