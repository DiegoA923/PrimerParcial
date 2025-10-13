package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;

import javax.swing.*;

/**
 * ControlMenu.
 * <p>
 * Controlador específico para el {@link PanelMenu}. Se encarga de gestionar las
 * acciones de los botones del menú principal.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class ControlMenu {

    private final VentanaPrincipal ventana;
    private final PanelMenu panel;

    /**
     * Constructor del controlador.
     *
     * @param ventana ventana principal de la aplicación
     * @param panel panel de menú asociado
     */
    public ControlMenu(VentanaPrincipal ventana, PanelMenu panel) {
        this.ventana = ventana;
        this.panel = panel;
        registrarEventos();
    }

    /**
     * Registra los eventos de los botones del menú principal.
     */
    private void registrarEventos() {
        panel.getBtnInsertar().addActionListener(e -> ventana.mostrarPanel(VentanaPrincipal.PANEL_INSERTAR));

        panel.getBtnModificar().addActionListener(e
                -> JOptionPane.showMessageDialog(ventana, "Función modificar próximamente disponible.")
        );

        panel.getBtnEliminar().addActionListener(e
                -> JOptionPane.showMessageDialog(ventana, "Función eliminar próximamente disponible.")
        );

        panel.getBtnSalir().addActionListener(e -> salirAplicacion());
    }

    /**
     * Cierra la aplicación con confirmación.
     */
    private void salirAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(
                ventana,
                "¿Deseas salir del sistema?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
