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

    private final VentanaPrincipal vista;
    private final PanelMenu panel;

    /**
     * Constructor del controlador.
     *
     * @param vista ventana principal de la aplicación
     * @param panel panel de menú asociado
     */
    public ControlMenu(VentanaPrincipal vista, PanelMenu panel) {
        this.vista = vista;
        this.panel = panel;
        registrarEventos();
    }

    
    // Registra los eventos de los botones del menú principal.
     
    private void registrarEventos() {
        panel.getBtnInsertar().addActionListener(e -> vista.mostrarPanel(VentanaPrincipal.PANEL_INSERTAR));
        panel.getBtnModificar().addActionListener(e -> vista.mostrarPanel(VentanaPrincipal.PANEL_MODIFICAR));

        panel.getBtnEliminar().addActionListener(e
                -> JOptionPane.showMessageDialog(vista, "Función eliminar próximamente disponible.")
        );

        panel.getBtnSalir().addActionListener(e -> salirAplicacion());
    }

    
    // Cierra la aplicación con confirmación.
     
    private void salirAplicacion() {
        int opcion = JOptionPane.showConfirmDialog(
                vista,
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
