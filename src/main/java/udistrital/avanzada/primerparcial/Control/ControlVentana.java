package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelInsertar;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelCompletarDatos;
import java.io.File;
import javax.swing.JFileChooser;

/**
 * ControlVentana.
 * <p>
 * Controlador principal encargado de manejar la navegación general y coordinar
 * los controladores específicos de cada panel.
 * </p>
 *
 * @author Diego
 * @version 1.1
 * @since 2025-10-13
 * 
 * <b>Modificación:</b> Se añadió la referencia a {@link ControlPrincipal} y el método
 * {@code obtenerArchivoSerializado(String)} para conectar la interfaz con la lógica
 * principal y gestionar la carga de archivos serializados. 
 */
public class ControlVentana {

    private final VentanaPrincipal vista;
    private final ControlPrincipal logica;

    // Controladores secundarios
    private ControlMenu controlMenu;
    private ControlInsertar controlInsertar;
    private ControlCompletarDatos controlCompletar;

    /**
     * Constructor principal.
     *
     * @param vista instancia principal de la aplicación
     * @param logica instancia del controlador lógico principal
     */
    public ControlVentana(VentanaPrincipal vista, ControlPrincipal logica) {
        this.vista = vista;
        this.logica = logica;
        inicializarControladores();
    }

    
    // Inicializa los controladores específicos de cada panel.
     
    private void inicializarControladores() {
        PanelMenu panelMenu = vista.getPanelMenu();
        PanelInsertar panelInsertar = vista.getPanelInsertar();
        PanelCompletarDatos panelCompletar = vista.getPanelCompletarDatos();

        controlMenu = new ControlMenu(vista, panelMenu);
        controlInsertar = new ControlInsertar(vista, panelInsertar);
        controlCompletar = new ControlCompletarDatos(vista, panelCompletar);
    }

    /**
     * Permite al usuario seleccionar un archivo serializado y lo procesa.
     *
     * @param ruta ruta inicial del explorador de archivos
     */
    public void obtenerArchivoSerializado(String ruta) {
        JFileChooser chooser = vista.getFileChoser(
                "Elija el archivo serializado",
                "bin",
                JFileChooser.FILES_ONLY,
                ruta
        );
        int seleccion = chooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = chooser.getSelectedFile();
            logica.procesarArchivoSerializado(archivoSeleccionado);
        }
    }
}
