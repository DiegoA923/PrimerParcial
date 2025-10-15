package udistrital.avanzada.primerparcial.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionBD;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.MascotaDAO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelInsertar;
import udistrital.avanzada.primerparcial.Vista.componentes.PanelFormularioMascota;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;

/**
 * ControlInsertar.
 * <p>
 * Controlador encargado de gestionar las acciones del {@link PanelInsertar}.
 * Implementa {@link ActionListener} para capturar los eventos de los botones
 * del panel de inserción: Guardar, Limpiar e Inicio.
 * </p>
 *
 *
 * @author Diego
 * @version 2.0
 * @since 2025-10-13
 * <p>
 * <b>Modificación:</b> Se integró conexión real con {@link MascotaDAO} para
 * insertar registros en la base de datos, validando los datos del formulario
 * antes de guardar. Autor: <b>Diego</b>, fecha <b>2025-10-14</b>.
 * </p>
 */
public class ControlInsertar implements ActionListener {

    private final VentanaPrincipal vista;
    private final PanelInsertar panel;
    private final MascotaDAO mascotaDAO;

    /**
     * Constructor del controlador.
     *
     * @param vista ventana principal de la aplicación
     * @param panel panel de inserción asociado
     */
    public ControlInsertar(VentanaPrincipal vista, PanelInsertar panel) {
        this.vista = vista;
        this.panel = panel;
        this.mascotaDAO = new MascotaDAO(ConexionBD.getInstancia()); // 🔗 conexión DAO

        // Registro de eventos
        panel.getBtnGuardar().addActionListener(this);
        panel.getBtnLimpiar().addActionListener(this);
        panel.getBtnInicio().addActionListener(this);
    }

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
     * Guarda una nueva mascota en la base de datos.
     * <p>
     * Valida que los campos no estén vacíos antes de crear el objeto
     * {@link MascotaVO} y delegar la inserción al {@link MascotaDAO}.
     * </p>
     */
    private void guardarMascota() {
        PanelFormularioMascota form = panel.getPanelFormulario();

        // Validaciones mínimas
        if (form.getCampoApodo().getTexto().isBlank()
                || form.getCampoEspecie().getTexto().isBlank()) {
            JOptionPane.showMessageDialog(vista, "Debe completar los campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear objeto MascotaVO desde los campos del formulario
        MascotaVO mascota = new MascotaVO();
        mascota.setNombreComun(form.getCampoNombre().getTexto());
        mascota.setApodo(form.getCampoApodo().getTexto());
        mascota.setFamilia(form.getCampoFamilia().getTexto());
        mascota.setGenero(form.getCampoGenero().getTexto());
        mascota.setEspecie(form.getCampoEspecie().getTexto());
        mascota.setClasificacion((Clasificacion) form.getComboClasificacion().getSelectedItem());
        mascota.setTipoAlimento((TipoAlimento) form.getComboTipoAlimento().getSelectedItem());

        // Inserta en BD
        boolean exito = mascotaDAO.insertarMascota(mascota);

        if (exito) {
            JOptionPane.showMessageDialog(vista, "Mascota registrada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            // Obtener referencia al PanelMenu desde la ventana principal
            PanelMenu panelMenu = vista.getPanelMenu();
            // Recargar los datos desde el DAO
            List<MascotaVO> lista = mascotaDAO.listaDeMascotas();
            panelMenu.refrescarTabla(lista);
            vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);

        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar la mascota.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
