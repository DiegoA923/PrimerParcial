package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;

/**
 * Clase ControlPrincipal.
 * <p>
 * Controla el flujo general del programa desde el arranque:
 * crea la vista principal y delega el manejo de eventos a {@link ControlVentana}.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-11
 */
public class ControlPrincipal {

    private VentanaPrincipal ventanaPrincipal;
    private ControlVentana controlVentana;

    /**
     * Constructor por defecto.
     * <p>
     * Inicializa la aplicación, crea la ventana principal y muestra
     * el panel inicial correspondiente.
     * </p>
     */
    public ControlPrincipal() {
        inicializar();
    }

    /**
     * Inicializa los componentes principales de la aplicación.
     */
    private void inicializar() {
        ventanaPrincipal = new VentanaPrincipal();
        controlVentana = new ControlVentana(ventanaPrincipal);

        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.mostrarPanel(VentanaPrincipal.PANEL_COMPLETAR);
    }
}
