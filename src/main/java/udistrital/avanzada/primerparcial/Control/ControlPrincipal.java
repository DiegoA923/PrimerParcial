package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import udistrital.avanzada.primerparcial.Modelo.Config;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionBD;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.SerializableDAO;

/**
 * ControlPrincipal.
 * <p>
 * Controlador principal que coordina la lógica general del programa. Gestiona
 * la inicialización de la vista, el control de mascotas y la persistencia de
 * datos tanto en base de datos como en archivos.
 * </p>
 *
 * <p>
 * Al iniciar, el sistema intenta cargar datos desde el archivo
 * <b>configuracion.properties</b>. Si no está disponible, intenta cargar desde
 * un archivo serializado; si tampoco existe, crea uno vacío.
 * </p>
 *
 * @author Diego
 * @version 1.2
 * @since 2025-10-13
 *
 * <b>Modificación:</b> Se actualizó la secuencia de inicialización para
 * integrar la carga dinámica desde el archivo .properties mediante
 * {@link ControlMascota#cargarDesdeProperties()} y la creación de
 * {@link ControlVentana} con inyección de dependencias.
 */
public class ControlPrincipal {

    private final VentanaPrincipal vista;
    private final ControlVentana controlVentana;
    private final ControlMascota controlMascota;
    private final GestorArchivoAleatorio gestorAleatorio;
    private final SerializableDAO serializableDAO;

    /**
     * Constructor principal.
     * <p>
     * Inicializa la vista, los controladores y verifica la existencia de los
     * archivos de datos requeridos.
     * </p>
     */
    public ControlPrincipal() {
        this.controlMascota = new ControlMascota();
        this.vista = new VentanaPrincipal();
        this.gestorAleatorio = new GestorArchivoAleatorio();
        this.serializableDAO = new SerializableDAO();
        this.controlVentana = new ControlVentana(vista, this, this.gestorAleatorio,this.serializableDAO);
        inicializar();
    }

    /**
     * Inicializa la aplicación verificando los archivos de datos existentes y
     * cargando la información inicial del sistema.
     * <ul>
     * <li>Si existe <b>configuracion.properties</b>, se cargan las mascotas
     * desde allí mediante {@link ControlMascota#cargarDesdeProperties()},
     * insertando las completas en base de datos y devolviendo solo las
     * incompletas.</li>
     * <li>Si existe un archivo serializado, se carga desde él.</li>
     * <li>De lo contrario, se crea un archivo serializado vacío para uso
     * futuro.</li>
     * </ul>
     *
     * <p>
     * Una vez cargados los datos, delega en
     * {@link ControlVentana#mostrarVistaInicial(List)} la decisión de qué panel
     * mostrar: el de completar datos (si hay mascotas incompletas) o el menú
     * principal.
     * </p>
     *
     * <b>Modificación:</b> Diego - 2025-10-14. Se reestructuró la lógica. Ahora
     * {@code ControlPrincipal} solo carga los datos y delega la elección de la
     * vista inicial a {@code ControlVentana}, eliminando la dependencia directa
     * de la vista.
     */
    private void inicializar() {
        File archivoPropiedades = null;
        String rutaSerializado = null;
        String rutaAleatorio = null;
        String rutaPropiedades = null;
        String dbUrl = null;
        String dbUser = null;
        String dbPassword = null;
        do {
            archivoPropiedades = controlVentana.obtenerArchivoPropiedades(
                    "specs/data",
                    "Eliga Archivo de propiedades valido con configuracion base"
            );
            if (archivoPropiedades.exists()) {
                Properties props = new Properties();
                try (FileInputStream fis = new FileInputStream(archivoPropiedades)) {
                    props.load(fis);
                    // Leer propiedades
                    rutaSerializado = props.getProperty("ruta.serializado");
                    Paths.get(rutaSerializado);
                    rutaAleatorio = props.getProperty("ruta.aleatorio");
                    Paths.get(rutaAleatorio);
                    rutaPropiedades = props.getProperty("ruta.propiedades");
                    Paths.get(rutaPropiedades);
                    dbUrl = props.getProperty("db.url");
                    dbUser = props.getProperty("db.user");
                    dbPassword = props.getProperty("db.password");
                    ConexionBD.getInstancia().config(dbUrl, dbUser, dbPassword);

                } catch (Exception e) {
                    rutaSerializado = null;
                    rutaAleatorio = null;
                    rutaPropiedades = null;
                    dbUrl = null;
                    dbUser = null;
                    dbPassword = null;
                }

            }
            // Archivo no tiene propiedades esenciales para el funcionamiento 
            // de la app entonces se sigue pidiendo un archivo válido
        } while (rutaSerializado == null
                || rutaAleatorio == null
                || dbUrl == null
                || dbUser == null
                || dbPassword == null
                || rutaPropiedades == null);

        File archivoSerializado = new File(rutaSerializado);
        File archivoPropiedadesMascotas = new File(rutaPropiedades);
        
        List<MascotaVO> listaIncompletas = new ArrayList<>();

        if (archivoPropiedadesMascotas.exists() && !archivoSerializado.exists()) {
            // Carga desde .properties → inserta completas y devuelve las incompletas
            listaIncompletas = controlMascota.cargarDesdeProperties();
        } else if (archivoSerializado.exists()) {
            // Carga desde archivo serializado si no hay properties
            File archivoSeleccionado = null;
            do {                
               archivoSeleccionado = controlVentana.obtenerArchivoSerializado(rutaSerializado);
               serializableDAO.setArchivo(archivoSerializado);
            } while (archivoSeleccionado == null);            
            // A todas las mascotas les faltara el tipo de comida por la serializacion
            // entonces todas estan incompletas 
            try {
                listaIncompletas = serializableDAO.listaDeMascotas();
            } catch (Exception e) {
                
            }            
        } else {
            // Si no hay ningún archivo, crear uno vacío para la serializacion
            serializableDAO.setArchivo(archivoSerializado);            
        }

        // Mostrar ventana y delegar qué vista se debe abrir primero
        vista.setVisible(true);
        controlVentana.mostrarVistaInicial(listaIncompletas);
    }

    /**
     * Procesa un archivo serializado seleccionado desde la vista.
     *
     * @param archivo archivo binario que contiene mascotas serializadas
     */
    public void procesarArchivoSerializado(File archivo) {
        serializableDAO.setArchivo(archivo);
    }

    /**
     * Inserta en la base de datos las mascotas cargadas desde un archivo
     * serializado.
     */
    public void insertarDatosDesdeSerializador() {
        try {
            ArrayList<MascotaVO> mascotas = serializableDAO.listaDeMascotas();
            for (MascotaVO mascota : mascotas) {
                controlMascota.insertarMascota(mascota);
            }
        } catch (RuntimeException e) {
            // Error no crítico: se omite en esta versión
        }
    }

    /**
     * Guarda el estado actual antes de cerrar la aplicación.
     * <p>
     * Serializa las mascotas y las persiste también en un archivo aleatorio.
     * </p>
     */
    public void salir() {
        ArrayList<MascotaVO> mascotas;
        try {
            mascotas = controlMascota.obtenerMascotas();
        } catch (RuntimeException e) {
            return;
        }

        if (mascotas == null || mascotas.isEmpty()) {
            return;
        }

        try {
            serializableDAO.guardar(mascotas);
        } catch (RuntimeException e) {
            // Manejo de error silencioso
        }

        gestorAleatorio.setRutaArchivo(Config.RUTA_PREDETERMINADA_ARCHIVO_AlEATORIO_ANIMALES);
        try {
            gestorAleatorio.insertarMascotas(mascotas);
        } catch (RuntimeException e) {
            // Manejo de error silencioso
        }
    }
}
