package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;
import udistrital.avanzada.primerparcial.Modelo.*;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionBD;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.MascotaDAO;

import javax.swing.*;
import java.util.List;

/**
 * ControlMenu.
 * <p>
 * Controlador encargado de gestionar las acciones del {@link PanelMenu},
 * incluyendo navegación, refresco, eliminación y consultas específicas según
 * los filtros seleccionados.
 * </p>
 *
 * @author Diego
 * @version 3.0
 * @since 2025-10-14
 * 
 * <p>
 * <b>Modificacion:</b> Se agregaron las consultas específicas de búsqueda por
 * Apodo, Familia, Clasificación y Tipo de alimento.
 * </p>
 */
public class ControlMenu {

    private final VentanaPrincipal vista;
    private final PanelMenu panel;
    private final MascotaDAO mascotaDAO;

    /**
     * Constructor del controlador.
     *
     * @param vista ventana principal de la aplicación
     * @param panel panel de menú asociado
     */
    public ControlMenu(VentanaPrincipal vista, PanelMenu panel) {
        this.vista = vista;
        this.panel = panel;
        this.mascotaDAO = new MascotaDAO(ConexionBD.getInstancia());

        registrarEventos();
        refrescarTabla();
    }

    /**
     * Registra los eventos del PanelMenu.
     * <p>
     * Los botones principales gestionan CRUD, mientras que el campo de búsqueda
     * y los combos activan consultas específicas en tiempo real.
     * </p>
     */
    private void registrarEventos() {

        // Botón: Insertar nueva mascota
        panel.getBtnInsertar().addActionListener(e
                -> vista.mostrarPanel(VentanaPrincipal.PANEL_INSERTAR)
        );

        // Botón: Modificar mascota seleccionada
        panel.getBtnModificar().addActionListener(e -> {
            MascotaVO seleccionada = panel.getMascotaSeleccionada();
            if (seleccionada == null) {
                JOptionPane.showMessageDialog(vista, "Seleccione una mascota de la tabla.",
                        "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }

            vista.getPanelModificar().cargarMascota(seleccionada);
            vista.mostrarPanel(VentanaPrincipal.PANEL_MODIFICAR);
        });

        // Botón: Eliminar mascota seleccionada
        panel.getBtnEliminar().addActionListener(e -> eliminarMascota());

        // Botón: Salir del sistema
        panel.getBtnSalir().addActionListener(e -> salirAplicacion());

        // Botón: Refrescar tabla
        panel.getBtnRefrescar().addActionListener(e -> refrescarTabla());

        // Campo de texto: ejecutar consulta al presionar Enter
        panel.getCampoBusqueda().addActionListener(e -> ejecutarConsultaEspecifica());

        // Combo Enum (clasificación o tipo alimento): ejecutar consulta al cambiar
        panel.getComboEnum().addActionListener(e -> {
            if (panel.getComboEnum().isVisible()) {
                ejecutarConsultaEspecifica();
            }
        });
    }

    /**
     * Ejecuta una búsqueda según el filtro y el valor ingresado.
     * <p>
     * Los filtros pueden ser por texto ("Apodo", "Familia") o por enumeración
     * ("Clasificación", "Tipo de alimento"). Si no hay texto o selección, se
     * muestra la lista completa.
     * </p>
     */
    private void ejecutarConsultaEspecifica() {
        String filtro = (String) panel.getComboFiltro().getSelectedItem();
        String texto = panel.getCampoBusqueda().getText().trim();
        List<MascotaVO> resultado = null;

        try {
            switch (filtro) {
                case "Apodo":
                    resultado = texto.isEmpty()
                            ? mascotaDAO.listaDeMascotas()
                            : mascotaDAO.consultarPorApodo(texto);
                    break;

                case "Familia":
                    resultado = texto.isEmpty()
                            ? mascotaDAO.listaDeMascotas()
                            : mascotaDAO.consultarPorFamilia(texto);
                    break;

                case "Clasificación":
                    if (panel.getComboEnum().getSelectedItem() instanceof Clasificacion clasif) {
                        resultado = mascotaDAO.consultarPorClasificacion(clasif);
                    }
                    break;

                case "Tipo de alimento":
                    if (panel.getComboEnum().getSelectedItem() instanceof TipoAlimento tipo) {
                        resultado = mascotaDAO.consultarPorTipoAlimento(tipo);
                    }
                    break;

                default:
                    resultado = mascotaDAO.listaDeMascotas();
            }

            // Refresca tabla en la vista
            panel.refrescarTabla(resultado);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al ejecutar la búsqueda:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    // Elimina la mascota seleccionada de la base de datos (con confirmación).
     
    private void eliminarMascota() {
        MascotaVO seleccionada = panel.getMascotaSeleccionada();
        if (seleccionada == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una mascota para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Deseas eliminar la mascota '" + seleccionada.getApodo() + "'?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminada = mascotaDAO.eliminarMascota(seleccionada.getId());
            if (eliminada) {
                JOptionPane.showMessageDialog(vista, "Mascota eliminada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                refrescarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo eliminar la mascota.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    // Carga todas las mascotas de la base de datos en la tabla del panel.
     
    private void refrescarTabla() {
        try {
            List<MascotaVO> mascotas = mascotaDAO.listaDeMascotas();
            panel.refrescarTabla(mascotas);
        } catch (Exception ex) {
            System.err.println("Error al refrescar la tabla: " + ex.getMessage());
        }
    }

    
    // Cierra la aplicación con confirmación del usuario.
     
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
