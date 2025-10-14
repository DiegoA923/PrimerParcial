package udistrital.avanzada.primerparcial.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelCompletarDatos;

/**
 * Clase ControlCompletarDatos.
 * <p>
 * Controlador encargado de manejar las acciones del panel
 * {@link PanelCompletarDatos}. Implementa {@link ActionListener} para capturar
 * eventos de botones, asegurando la separación entre la lógica y la interfaz
 * visual.
 * </p>
 *
 * <p>
 * En esta versión inicial se controla únicamente el botón "Guardar", el cual
 * muestra un mensaje de confirmación y regresa al menú principal. En versiones
 * futuras se agregará validación, persistencia y manejo de errores.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class ControlCompletarDatos implements ActionListener {

    // Atributos
    private final VentanaPrincipal vista;
    private final PanelCompletarDatos panel;

    /**
     * Constructor del controlador.
     *
     * @param vista referencia a la ventana principal de la aplicación
     * @param panel panel asociado al proceso de completar datos
     */
    public ControlCompletarDatos(VentanaPrincipal vista, PanelCompletarDatos panel) {
        this.vista = vista;
        this.panel = panel;

        // Registro de eventos: solo un listener central
        panel.getBtnGuardar().addActionListener(this);
    }

    /**
     * Método que gestiona los eventos generados desde el panel.
     *
     * @param e evento de acción recibido
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        // Identifica el comando del botón
        switch (comando.toLowerCase()) {
            case "guardar":
                guardarCambios();
                break;

            default:
                System.out.println("Acción no reconocida en PanelCompletarDatos: " + comando);
        }
    }
    
    /**
     * Simula el guardado de los datos de la mascota seleccionada.
     * <p>
     * En versiones posteriores se añadirá validación de campos, verificación de
     * datos faltantes y persistencia en base de datos o archivo.
     * </p>
     */
    private void guardarCambios() {
        JOptionPane.showMessageDialog(
                vista,
                "Datos de la mascota guardados correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Cambia al panel de menú principal
        vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);
    }
}
