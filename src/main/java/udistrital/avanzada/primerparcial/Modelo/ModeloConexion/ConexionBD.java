package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase ConexionBD.
 * <p>
 * Implementa la interfaz {@link IConexion} y aplica el patrón Singleton para 
 * garantizar una única instancia de conexión con la base de datos durante la ejecución del aplicativo.
 * </p>
 *
 * <p>
 * Esta clase carga los parámetros de conexión (URL, usuario y contraseña)
 * desde un archivo externo de configuración llamado <b>database.properties</b>,
 * lo que permite modificar la conexión sin alterar el código fuente.
 * </p>
 *
 * <p>
 * Las clases DAO no dependen directamente de esta clase, sino de la interfaz {@link IConexion},
 * promoviendo el principio de inversión de dependencias (D de SOLID).
 * </p>
 *
 * @author Diego
 * @version 1.1
 * @since 2025-10-10
 */
public class ConexionBD implements IConexion {

    /** Instancia única de la clase ConexionBD (patrón Singleton). */
    private static ConexionBD instancia;

    /** Objeto Connection activo que representa la conexión a la base de datos. */
    private static Connection conexion;

    /** Propiedades de configuración de la conexión. */
    private static final String CONFIG_PATH = "src/data/database.properties";

    /** Datos de conexión leídos desde el archivo de propiedades. */
    private String url;
    private String user;
    private String password;

    /**
     * Constructor privado para evitar instancias externas.
     * Carga los datos de conexión desde el archivo de propiedades.
     */
    private ConexionBD() {
        cargarPropiedades();
    }

    /**
     * Obtiene la instancia única de la clase ConexionBD.
     *
     * @return instancia única de ConexionBD
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Carga los datos de conexión (URL, usuario y contraseña) desde el archivo
     * <b>database.properties</b>.
     */
    private void cargarPropiedades() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            props.load(fis);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de configuración de la base de datos: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Establece la conexión a la base de datos MySQL si no existe o si está cerrada.
     * </p>
     */
    @Override
    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Cierra la conexión actual con la base de datos si está abierta.
     * </p>
     */
    @Override
    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}


