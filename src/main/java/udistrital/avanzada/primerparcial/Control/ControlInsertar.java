package udistrital.avanzada.primerparcial.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelInsertar;
import udistrital.avanzada.primerparcial.Vista.componentes.PanelFormularioMascota;

/**
 * ControlInsertar.
 * <p>
 * Controlador encargado de gestionar las acciones del {@link PanelInsertar}.
 * Implementa {@link ActionListener} para capturar los eventos de los botones
 * del panel de inserción: Guardar, Limpiar e Inicio.
 * </p>
 *
 * <p>
 * En esta versión, las operaciones son simuladas para la capa gráfica. En
 * versiones futuras, este controlador validará los datos ingresados y
 * gestionará la comunicación con la capa DAO para realizar la inserción real en
 * la base de datos.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class ControlInsertar implements ActionListener {

    private final VentanaPrincipal vista;
    private final PanelInsertar panel;

    /**
     * Constructor del controlador.
     *
     * @param vista ventana principal de la aplicación
     * @param panel panel de inserción asociado
     */
    public ControlInsertar(VentanaPrincipal vista, PanelInsertar panel) {
        this.vista = vista;
        this.panel = panel;

        // Registro de eventos
        panel.getBtnGuardar().addActionListener(this);
        panel.getBtnLimpiar().addActionListener(this);
        panel.getBtnInicio().addActionListener(this);
    }

    /**
     * Maneja las acciones de los botones del panel de inserción.
     *
     * @param e evento generado por la vista
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand().toLowerCase();

        switch (comando) {
            case "guardar":
                guardarMascota();
                break;

            case "limpiar":
                limpiarCampos();
                break;

            case "inicio":
                vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);
                break;

            default:
                System.out.println("[ControlInsertar] Acción no reconocida: " + comando);
        }
    }
    
    /**
     * Simula el registro de una nueva mascota.
     * <p>
     * En el futuro, este método se conectará con la capa DAO, verificará que la
     * mascota no exista previamente, validará los datos del formulario y luego
     * mostrará un mensaje de confirmación o error según corresponda.
     * </p>
     */
    private void guardarMascota() {
        JOptionPane.showMessageDialog(
                vista,
                "Mascota registrada correctamente (simulado).",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Regresa al menú principal
        vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);
    }

    
    // Limpia todos los campos del formulario de inserción.
     
    private void limpiarCampos() {
        PanelFormularioMascota form = panel.getPanelFormulario();

        form.getCampoNombre().setTexto("");
        form.getCampoApodo().setTexto("");
        form.getCampoFamilia().setTexto("");
        form.getCampoGenero().setTexto("");
        form.getCampoEspecie().setTexto("");
        form.getComboClasificacion().setSelectedIndex(-1);
        form.getComboTipoAlimento().setSelectedIndex(-1);
    }
}
