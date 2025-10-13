package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelInsertar;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelCompletarDatos;

/**
 * ControlVentana.
 * <p>
 * Controlador principal encargado de manejar la navegación general y coordinar
 * los controladores específicos de cada panel.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-15
 */
public class ControlVentana {

    private final VentanaPrincipal vista;

    // Controladores secundarios
    private ControlMenu controlMenu;
    private ControlInsertar controlInsertar;
    private ControlCompletarDatos controlCompletar;

    /**
     * Constructor principal.
     *
     * @param vista instancia principal de la aplicación
     */
    public ControlVentana(VentanaPrincipal vista) {
        this.vista = vista;
        inicializarControladores();
    }

    /**
     * Inicializa los controladores específicos de cada panel.
     */
    private void inicializarControladores() {
        PanelMenu panelMenu = vista.getPanelMenu();
        PanelInsertar panelInsertar = vista.getPanelInsertar();
        PanelCompletarDatos panelCompletar = vista.getPanelCompletarDatos();

        controlMenu = new ControlMenu(vista, panelMenu);
        controlInsertar = new ControlInsertar(vista, panelInsertar);
        controlCompletar = new ControlCompletarDatos(vista, panelCompletar);
    }
}
