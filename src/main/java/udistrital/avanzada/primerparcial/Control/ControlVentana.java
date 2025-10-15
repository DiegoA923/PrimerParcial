package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelInsertar;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelCompletarDatos;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.MascotaDAO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionBD;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import udistrital.avanzada.primerparcial.Modelo.Config;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelModificar;

/**
 * ControlVentana.
 * <p>
 * Controlador que gestiona la navegación general de la aplicación y coordina
 * los controladores específicos de cada panel visual.
 * </p>
 *
 * @author Diego
 * @version 1.4
 * @since 2025-10-13
 * 
 * <p>
 * Modificación: Diego – 2025-10-14: Se integró la carga de mascotas desde base
 * de datos al panel menú para que muestre todos los registros reales,
 * preparando la conexión con los botones de Insertar, Modificar y Eliminar.
 * </p>
 */
public class ControlVentana {

    private final VentanaPrincipal vista;
    private final ControlPrincipal logica;

    private ControlMenu controlMenu;
    private ControlInsertar controlInsertar;
    private ControlCompletarDatos controlCompletar;
    private final ControlMascota controlMascota;
    private ControlModificar controlModificar;

    /**
     * Constructor principal del controlador de ventana.
     *
     * @param vista instancia principal de la interfaz
     * @param logica controlador central del sistema
     */
    public ControlVentana(VentanaPrincipal vista, ControlPrincipal logica) {
        this.vista = vista;
        this.logica = logica;
        this.controlMascota = new ControlMascota();

        inicializarControladores();
        cargarDatosIniciales();
    }

    /**
     * Inicializa los controladores secundarios y enlaza los paneles base.
     * <p>
     * Esta rutina solo crea los controladores de los paneles; la decisión de
     * cuál vista mostrar (menu o completar) se delega a
     * {@code mostrarVistaInicial(...)}.
     * </p>
     *
     * Modificación: Diego - 2025-10-14
     */
    private void inicializarControladores() {
        PanelMenu panelMenu = vista.getPanelMenu();
        PanelInsertar panelInsertar = vista.getPanelInsertar();
        PanelCompletarDatos panelCompletar = vista.getPanelCompletarDatos();
        PanelModificar panelModificar = vista.getPanelModificar();

        controlMenu = new ControlMenu(vista, panelMenu);
        controlInsertar = new ControlInsertar(vista, panelInsertar);
        controlModificar = new ControlModificar(vista, panelModificar);

        // controlCompletar se inicializará solo si hay mascotas incompletas;
        // la lógica de decisión la hace ControlPrincipal mediante mostrarVistaInicial(...)
    }

    /**
     * Carga los datos iniciales desde la base o el archivo .properties y
     * muestra la vista correspondiente.
     */
    private void cargarDatosIniciales() {
        File archivoSerializado = new File(Config.RUTA_PREDETERMINADA_ARCHIVO_SERIALIZADO_ANIMALES);
        File archivoProperties = new File(Config.RUTA_CARPETA_PRECARGA);

        List<MascotaVO> incompletas = null;

        // Prioridad: cargar desde .properties si existe
        if (archivoProperties.exists()) {
            incompletas = controlMascota.cargarDesdeProperties();
        } else if (archivoSerializado.exists()) {
            obtenerArchivoSerializado(Config.RUTA_PREDETERMINADA_ARCHIVO_SERIALIZADO_ANIMALES);
        }

        vista.setVisible(true);

        if (incompletas != null && !incompletas.isEmpty()) {
            // Si hay mascotas con datos faltantes, mostrar el panel de completado
            PanelCompletarDatos panelCompletar = vista.getPanelCompletarDatos();
            MascotaDAO mascotaDAO = new MascotaDAO(ConexionBD.getInstancia());
            controlCompletar = new ControlCompletarDatos(vista, panelCompletar, mascotaDAO, incompletas);
            vista.mostrarPanel(VentanaPrincipal.PANEL_COMPLETAR);
        } else {
            // Si no hay pendientes, cargar el menú con todas las mascotas desde la base
            mostrarPanelMenuConDatos();
        }
    }

    /**
     * Carga todas las mascotas desde la base de datos y las muestra en el panel
     * menú.
     */
    private void mostrarPanelMenuConDatos() {
        PanelMenu panelMenu = vista.getPanelMenu();
        MascotaDAO mascotaDAO = new MascotaDAO(ConexionBD.getInstancia());
        List<MascotaVO> mascotasBD = mascotaDAO.listaDeMascotas();

        if (mascotasBD != null && !mascotasBD.isEmpty()) {
            try {
                // Refrescar tabla directamente desde el panel
                var metodoRefrescar = panelMenu.getClass().getDeclaredMethod("refrescarTabla", List.class);
                metodoRefrescar.setAccessible(true);
                metodoRefrescar.invoke(panelMenu, mascotasBD);
            } catch (Exception ex) {
                System.err.println("Error al actualizar la tabla del menú: " + ex.getMessage());
            }
        }

        vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);
    }

    /**
     * Muestra la vista inicial según la lista de mascotas incompletas recibida.
     * <p>
     * Si {@code incompletas} no es nula y contiene elementos, se crea el
     * controlador de completar datos; en caso contrario se muestra el menú.
     * </p>
     *
     * Modificación: Diego - 2025-10-14
     *
     * @param incompletas lista de mascotas con datos faltantes (proviene de
     * ControlPrincipal)
     */
    public void mostrarVistaInicial(List<MascotaVO> incompletas) {
        if (incompletas != null && !incompletas.isEmpty()) {
            // Inyección controlada del DAO
            MascotaDAO mascotaDAO = new MascotaDAO(ConexionBD.getInstancia());

            // Crear controlador del panel de completar datos con la lista ya filtrada
            controlCompletar = new ControlCompletarDatos(vista, vista.getPanelCompletarDatos(), mascotaDAO, incompletas);

            vista.mostrarPanel(VentanaPrincipal.PANEL_COMPLETAR);
        } else {
            // No hay pendientes — mostrar menú
            vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);
        }
    }

    /**
     * Permite al usuario seleccionar un archivo serializado y lo procesa.
     *
     * @param ruta ruta inicial del explorador de archivos
     * @return archivo seleccionado
     */
    public File obtenerArchivoSerializado(String ruta) {
        File archivoSeleccionado = null;
        JFileChooser chooser = vista.getFileChoser(
                "Elija el archivo serializado",
                "bin",
                JFileChooser.FILES_ONLY,
                ruta
        );
        int seleccion = chooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            archivoSeleccionado = chooser.getSelectedFile();
        }
        return archivoSeleccionado;
    }
    
    /**
     * Permite al usuario seleccionar un archivo propiedades y lo procesa.
     *
     * @param ruta ruta inicial del explorador de archivos
     * @param mensaje que se solicita
     * @return archivo seleccionado por usuario
     */
    public File obtenerArchivoPropiedades(String ruta, String mensaje) {
        File archivoSeleccionado = null;
        JFileChooser chooser = vista.getFileChoser(
                mensaje,
                "properties",
                JFileChooser.FILES_ONLY,
                ruta
        );
        int seleccion = chooser.showOpenDialog(null);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            archivoSeleccionado = chooser.getSelectedFile();         
        }
        return archivoSeleccionado;
    }
}
