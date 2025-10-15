package udistrital.avanzada.primerparcial.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionBD;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.IMascotaDAO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.MascotaDAO;
import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelModificar;

/**
 * ControlModificar.
 * <p>
 * Controlador responsable de actualizar los datos de una mascota existente.
 * Implementa {@link ActionListener} para gestionar los eventos de los botones
 * del {@link PanelModificar}.
 * </p>
 *
 * @author Diego
 * @version 2.0
 * @since 2025-10-14
 * 
 * <p>
 * <b>Modificación:</b> Se implementó la conexión real con {@link MascotaDAO},
 * actualizando el registro seleccionado y restringiendo los campos no
 * editables. Autor: <b>Diego</b>, fecha <b>2025-10-14</b>.
 * </p>
 */
public class ControlModificar implements ActionListener {

    private final VentanaPrincipal vista;
    private final PanelModificar panel;
    private final IMascotaDAO mascotaDAO;

    public ControlModificar(VentanaPrincipal vista, PanelModificar panel) {
        this.vista = vista;
        this.panel = panel;
        this.mascotaDAO = new MascotaDAO(ConexionBD.getInstancia());

        // Registro de eventos
        panel.getBtnGuardarCambios().addActionListener(this);
        panel.getBtnCancelar().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand().toLowerCase();

        switch (comando) {
            case "guardarcambios":
                actualizarMascota();
                break;

            case "cancelar":
                vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);
                break;

            default:
                System.out.println("[ControlModificar] Acción no reconocida: " + comando);
        }
    }

    /**
     * Recibe la mascota seleccionada desde el panel menú para editarla.
     */
    public void setMascotaSeleccionada(MascotaVO mascota) {
        panel.cargarMascota(mascota);
    }

    /**
     * Modificación: ahora el controlador obtiene la mascota editada
     * directamente desde la vista (panel.obtenerMascotaEditada()), evitando
     * depender de un campo interno 'mascotaSeleccionada' que puede no haber
     * sido inicializado.
     *
     * Además, al actualizar exitosamente refresca la tabla del PanelMenu para
     * reflejar los cambios inmediatamente (MVC: controlador orquesta
     * modelo->vista).
     */
    private void actualizarMascota() {
        // Obtener la mascota actual desde el panel (la vista)
        MascotaVO editada = panel.obtenerMascotaEditada();

        if (editada == null) {
            JOptionPane.showMessageDialog(vista,
                    "No hay datos para actualizar. Cargue una mascota antes de editar.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean exito = mascotaDAO.modificarMascota(editada);

            if (exito) {
                JOptionPane.showMessageDialog(vista,
                        "Mascota actualizada correctamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                // Refrescar la tabla del panel principal
                var lista = mascotaDAO.listaDeMascotas();
                vista.getPanelMenu().refrescarTabla(lista);

                // Volver al panel menú
                vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Error al actualizar la mascota.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al actualizar la mascota:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

}
