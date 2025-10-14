package udistrital.avanzada.primerparcial.Vista.paneles;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Vista.componentes.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * PanelModificar.
 * <p>
 * Permite modificar los datos de una mascota seleccionada desde el
 * {@link PanelMenu}. Muestra todos los campos del formulario, bloqueando los
 * que no son editables (Familia, Género y Especie), y aplica el diseño visual
 * del tema.
 * </p>
 *
 * <p>
 * Estructura visual:
 * <ul>
 * <li>Título dinámico: “Modificar Mascota - (Apodo / ID)”.</li>
 * <li>Formulario completo dentro de una tarjeta blanca.</li>
 * <li>Botones de acción: “Guardar cambios” y “Cancelar”.</li>
 * </ul>
 * </p>
 *
 * @author Diego
 * @version 2.2
 * @since 2025-10-14
 */
public class PanelModificar extends PanelBaseSeccion {

    private PanelFormularioMascota panelFormulario;
    private BotonPersonalizado btnGuardarCambios;
    private BotonPersonalizado btnCancelar;

    // Mascota actualmente cargada en el formulario
    private MascotaVO mascotaActual;

    /**
     * Constructor principal.
     */
    public PanelModificar() {
        super("Modificar datos de mascota");
        inicializar();
    }

    /**
     * Configura la estructura del panel.
     */
    private void inicializar() {
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.setOpaque(false);

        contenedorPrincipal.add(crearZonaCentral(), BorderLayout.CENTER);
        contenedorPrincipal.add(crearZonaInferior(), BorderLayout.SOUTH);

        setContenidoCentral(contenedorPrincipal);
    }

    /**
     * Crea la zona central con el formulario dentro de una tarjeta decorada.
     */
    private JComponent crearZonaCentral() {
        panelFormulario = new PanelFormularioMascota();

        JPanel tarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 240));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(new Color(0, 0, 0, 40));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                g2.dispose();
            }
        };

        tarjeta.setOpaque(false);
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        tarjeta.add(panelFormulario, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setOpaque(false);
        wrapper.add(tarjeta);

        return wrapper;
    }

    /**
     * Crea la zona inferior con botones de acción.
     */
    private JComponent crearZonaInferior() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        barra.setBackground(TemaVisual.PANEL_IZQUIERDO_BG);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TemaVisual.PRIMARIO_OSCURO));

        btnGuardarCambios = new BotonPersonalizado("Guardar cambios",
                TemaVisual.BOTON_FONDO,
                TemaVisual.BOTON_HOVER,
                TemaVisual.BOTON_TEXTO);

        btnCancelar = new BotonPersonalizado("Cancelar",
                TemaVisual.PRIMARIO_OSCURO,
                TemaVisual.BOTON_HOVER,
                TemaVisual.BOTON_TEXTO);

        Dimension dBtn = new Dimension(160, 38);
        btnGuardarCambios.setPreferredSize(dBtn);
        btnCancelar.setPreferredSize(dBtn);

        barra.add(btnGuardarCambios);
        barra.add(btnCancelar);

        return barra;
    }

    // ------------------------------------------------------------
    // Métodos públicos
    // ------------------------------------------------------------
    /**
     * Carga los datos de una mascota seleccionada desde el PanelMenu.
     * <p>
     * Bloquea los campos Familia, Género y Especie, ya que no son modificables.
     * </p>
     *
     * @param mascota objeto MascotaVO seleccionado.
     */
    public void cargarMascota(MascotaVO mascota) {
        this.mascotaActual = mascota;
        setSubtitulo("Modificar Mascota - " + mascota.getApodo() + " (ID: " + mascota.getId() + ")");
        panelFormulario.cargarDatosMascota(mascota);

        // Bloquear campos no editables
        panelFormulario.getCampoFamilia().getCampoTexto().setEditable(false);
        panelFormulario.getCampoGenero().getCampoTexto().setEditable(false);
        panelFormulario.getCampoEspecie().getCampoTexto().setEditable(false);
    }

    /**
     * Devuelve la mascota con los nuevos valores ingresados.
     * <p>
     * Los campos no editables mantienen su valor original.
     * </p>
     *
     * @return MascotaVO actualizada.
     */
    public MascotaVO obtenerMascotaEditada() {
        MascotaVO editada = panelFormulario.obtenerDatosMascota();
        editada.setId(mascotaActual.getId());
        editada.setFamilia(mascotaActual.getFamilia());
        editada.setGenero(mascotaActual.getGenero());
        editada.setEspecie(mascotaActual.getEspecie());
        return editada;
    }

    // ------------------------------------------------------------
    // Getters para el Control
    // ------------------------------------------------------------
    public JButton getBtnGuardarCambios() {
        return btnGuardarCambios;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public PanelFormularioMascota getPanelFormulario() {
        return panelFormulario;
    }

    public MascotaVO getMascotaActual() {
        return mascotaActual;
    }
}
