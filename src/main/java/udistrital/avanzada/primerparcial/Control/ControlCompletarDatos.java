package udistrital.avanzada.primerparcial.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionListener;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.MascotaDAO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.PropertiesDAO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionProperties;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.IMascotaDAO;
import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelCompletarDatos;

/**
 * Clase ControlCompletarDatos.
 * <p>
 * Controlador encargado de manejar la lógica del panel
 * {@link PanelCompletarDatos}. Gestiona la carga de mascotas desde el archivo
 * .properties, permite completar sus datos y realiza el guardado real en la
 * base de datos a través de {@link MascotaDAO}.
 * </p>
 *
 * @author Diego
 * @version 2.1
 * @since 2025-10-15
 */
public class ControlCompletarDatos implements ActionListener {

    private final VentanaPrincipal vista;
    private final PanelCompletarDatos panel;
    private final IMascotaDAO mascotaDAO;
    private final List<MascotaVO> listaMascotas;

    /**
     * Constructor principal.
     *
     * @param vista referencia a la ventana principal
     * @param panel panel asociado a la vista de completar datos
     * @param mascotaDAO DAO responsable de las operaciones en la base de datos
     * @param listaMascotas lista de mascotas cargadas desde el archivo
     * properties
     */
    public ControlCompletarDatos(VentanaPrincipal vista,
            PanelCompletarDatos panel,
            MascotaDAO mascotaDAO,
            List<MascotaVO> listaMascotas) {
        this.vista = vista;
        this.panel = panel;
        this.mascotaDAO = mascotaDAO;
        this.listaMascotas = listaMascotas;

        inicializarListeners();
        actualizarListaVisual();
    }

    /**
     * Inicializa los listeners del panel, eliminando duplicados.
     * <p>
     * Esta rutina evita que se ejecuten múltiples veces los eventos de botón o
     * de selección de lista, lo que causaba mensajes dobles o corrimiento de la
     * tabla.
     * </p>
     */
    private void inicializarListeners() {
        // Limpiar listeners antiguos del botón
        for (ActionListener al : panel.getBtnGuardar().getActionListeners()) {
            panel.getBtnGuardar().removeActionListener(al);
        }
        panel.getBtnGuardar().addActionListener(this);

        // Limpiar listeners antiguos de la lista
        for (ListSelectionListener lsl : panel.getListaMascotas().getListSelectionListeners()) {
            panel.getListaMascotas().removeListSelectionListener(lsl);
        }
        panel.getListaMascotas().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDatosSeleccionados();
            }
        });
    }

    /**
     * Actualiza dinámicamente la lista visual de mascotas incompletas.
     * <p>
     * Limpia el modelo actual y vuelve a cargar solo las mascotas que aún
     * requieren completarse, asegurando sincronía entre el modelo visual y los
     * datos internos.
     * </p>
     */
    private void actualizarListaVisual() {
        var modelo = (DefaultListModel<String>) panel.getListaMascotas().getModel();
        modelo.clear();

        for (MascotaVO m : listaMascotas) {
            modelo.addElement(m.getNombreComun() != null && !m.getNombreComun().isEmpty()
                    ? m.getNombreComun()
                    : "(Sin nombre)");
        }
    }

    /**
     * Muestra en el formulario los datos de la mascota seleccionada para
     * completar los datos faltantes.
     */
    private void mostrarDatosSeleccionados() {
        int index = panel.getListaMascotas().getSelectedIndex();
        if (index >= 0 && index < listaMascotas.size()) {
            MascotaVO mascota = listaMascotas.get(index);
            panel.getPanelFormulario().cargarDatosMascota(mascota);
            panel.setTituloFormulario("Editar datos de: " + mascota.getNombreComun());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Guardar".equalsIgnoreCase(e.getActionCommand())) {
            guardarCambios();
        }
    }

    /**
     * Guarda la mascota seleccionada en la base de datos, valida duplicados,
     * elimina del archivo properties y actualiza la lista.
     * <p>
     * Mantiene la consistencia de índices usando la clave real de cada
     * MascotaVO para eliminar correctamente del archivo, evita mensajes
     * duplicados y corrimiento de la lista.
     * </p>
     */
    private void guardarCambios() {
        int index = panel.getListaMascotas().getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione una mascota primero.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        MascotaVO mascota = panel.getPanelFormulario().obtenerDatosMascota();

        if (!mascota.tieneDatosCompletos()) {
            JOptionPane.showMessageDialog(vista,
                    "Complete todos los datos antes de guardar.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            boolean insertada = mascotaDAO.insertarMascota(mascota);

            if (insertada) {
                JOptionPane.showMessageDialog(vista,
                        "Mascota guardada correctamente en la base de datos.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                // Eliminar del archivo .properties usando la clave real
                try {
                    PropertiesDAO propertiesDAO = new PropertiesDAO(new ConexionProperties());
                    GestorArchivoProperties gestor = new GestorArchivoProperties(propertiesDAO);

                    if (mascota.getClaveProperties() != null && !mascota.getClaveProperties().isEmpty()) {
                        gestor.eliminarMascotaDeProperties(mascota.getClaveProperties());
                        gestor.guardarCambios();
                    }
                } catch (Exception ex) {
                    System.err.println("Error al eliminar del archivo properties: " + ex.getMessage());
                }

                // Eliminar de la lista interna y visual
                listaMascotas.remove(index);
                ((DefaultListModel<String>) panel.getListaMascotas().getModel()).remove(index);

                // Seleccionar automáticamente la siguiente mascota si existe
                if (!listaMascotas.isEmpty()) {
                    int siguiente = Math.min(index, listaMascotas.size() - 1);
                    panel.getListaMascotas().setSelectedIndex(siguiente);
                } else {
                    JOptionPane.showMessageDialog(vista,
                            "Todas las mascotas han sido completadas.",
                            "Proceso finalizado",
                            JOptionPane.INFORMATION_MESSAGE);

                    try {
                        // Usar el DAO existente para obtener todas las mascotas
                        List<MascotaVO> todasMascotas = mascotaDAO.listaDeMascotas();

                        // Obtener el panel menú y llamar al método refrescarTabla usando reflexión
                        var panelMenu = vista.getPanelMenu();
                        var metodoRefrescar = panelMenu.getClass().getDeclaredMethod("refrescarTabla", List.class);
                        metodoRefrescar.setAccessible(true);
                        metodoRefrescar.invoke(panelMenu, todasMascotas);
                    } catch (Exception ex) {
                        System.err.println("Error al refrescar la tabla del menú: " + ex.getMessage());
                    }

                    vista.mostrarPanel(VentanaPrincipal.PANEL_MENU);

                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al guardar en la base de datos:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
