package udistrital.avanzada.primerparcial.Vista.paneles;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.componentes.BotonPersonalizado;
import udistrital.avanzada.primerparcial.Vista.componentes.PanelBaseSeccion;
import udistrital.avanzada.primerparcial.Vista.componentes.PanelFormularioMascota;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * PanelCompletarDatos.
 * <p>
 * Panel destinado a completar los datos faltantes de mascotas exóticas. Utiliza
 * {@link PanelBaseSeccion} como contenedor base y un formulario reutilizable de
 * {@link PanelFormularioMascota}.
 * </p>
 *
 * @author Diego
 * @version 1.1
 * @since 2025-10-13
 * 
 * <p>
 * <b>Modificación (2025-10-14):</b> Se eliminó la simulación de datos estáticos
 * y se adaptó el panel para recibir y mostrar las mascotas cargadas
 * dinámicamente desde {@link ControlCompletarDatos}. También se simplificó el
 * código de inicialización visual.
 * </p>
 */
public class PanelCompletarDatos extends PanelBaseSeccion {

    // Lista visual de nombres de mascotas 
    private JList<String> listaMascotas;

    
    // Modelo de datos para la lista
    private DefaultListModel<String> modeloLista;

    
    // Formulario reutilizable para mostrar o editar datos de mascota
     
    private PanelFormularioMascota panelFormulario;

    
    // Botón principal para guardar los cambios
    
    private BotonPersonalizado btnGuardar;

    
    // Etiqueta del título del formulario
     
    private JLabel lblTituloFormulario;

    /**
     * Constructor principal.
     *
     * @param subtitulo texto a mostrar debajo del encabezado principal
     * @param tituloFormulario texto que se mostrará encima del formulario
     */
    public PanelCompletarDatos(String subtitulo, String tituloFormulario) {
        super(subtitulo);
        inicializar(tituloFormulario);
    }

    /**
     * Inicializa los componentes visuales del panel.
     *
     * @param tituloFormulario título del bloque de formulario
     */
    private void inicializar(String tituloFormulario) {
        setContenidoCentral(crearZonaCentral(tituloFormulario));
    }

    /**
     * Crea la zona central (dividida en lista izquierda y formulario derecho).
     *
     * @param tituloFormulario texto superior del formulario
     * @return el componente central del panel
     */
    private JSplitPane crearZonaCentral(String tituloFormulario) {
        // ----------------------------------------
        //  Lado izquierdo: lista de mascotas
        // ----------------------------------------
        modeloLista = new DefaultListModel<>();
        listaMascotas = new JList<>(modeloLista);
        listaMascotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaMascotas.setFont(TemaVisual.FUENTE_TEXTO);
        listaMascotas.setForeground(TemaVisual.TEXTO);
        listaMascotas.setBackground(TemaVisual.PANEL_LISTA_BG);
        listaMascotas.setBorder(BorderFactory.createTitledBorder("Mascotas con datos faltantes"));

        JScrollPane scrollLista = new JScrollPane(listaMascotas);
        scrollLista.setPreferredSize(new Dimension(260, 0));
        scrollLista.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ----------------------------------------
        //  Lado derecho: título + tarjeta blanca con formulario
        // ----------------------------------------
        panelFormulario = new PanelFormularioMascota();

        btnGuardar = new BotonPersonalizado(
                "Guardar",
                TemaVisual.BOTON_FONDO,
                TemaVisual.BOTON_HOVER,
                TemaVisual.BOTON_TEXTO
        );
        btnGuardar.setPreferredSize(new Dimension(180, 35));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelBoton.setOpaque(false);
        panelBoton.add(btnGuardar);

        lblTituloFormulario = new JLabel(tituloFormulario, SwingConstants.CENTER);
        lblTituloFormulario.setFont(TemaVisual.FUENTE_TITULO.deriveFont(Font.BOLD, 18f));
        lblTituloFormulario.setForeground(TemaVisual.PRIMARIO_OSCURO);
        lblTituloFormulario.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        JPanel tarjeta = crearTarjetaFormulario();
        tarjeta.add(panelFormulario, BorderLayout.CENTER);
        tarjeta.add(panelBoton, BorderLayout.SOUTH);

        JPanel contenedorDerecha = new JPanel(new BorderLayout());
        contenedorDerecha.setOpaque(false);
        contenedorDerecha.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel wrapperTarjeta = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapperTarjeta.setOpaque(false);
        wrapperTarjeta.add(tarjeta);

        contenedorDerecha.add(lblTituloFormulario, BorderLayout.NORTH);
        contenedorDerecha.add(wrapperTarjeta, BorderLayout.CENTER);

        // ----------------------------------------
        //  Ensamblar panel dividido
        // ----------------------------------------
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLista, contenedorDerecha);
        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        return splitPane;
    }

    /**
     * Crea la tarjeta blanca que contiene el formulario y el botón.
     *
     * @return panel estilizado de fondo blanco
     */
    private JPanel crearTarjetaFormulario() {
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
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tarjeta.setPreferredSize(new Dimension(520, 430));
        return tarjeta;
    }

    /**
     * Devuelve el modelo de lista, para que el controlador pueda actualizarlo.
     *
     * @return modelo de la lista
     */
    public DefaultListModel<String> getModeloLista() {
        return modeloLista;
    }

    /**
     * Actualiza el contenido de la lista de mascotas mostradas en pantalla.
     * <p>
     * Limpia la lista actual y carga los nombres de las mascotas incompletas
     * proporcionadas desde el controlador.
     * </p>
     *
     * @param mascotas lista de mascotas incompletas que deben mostrarse
     */
    public void actualizarLista(java.util.List<udistrital.avanzada.primerparcial.Modelo.MascotaVO> mascotas) {
        modeloLista.clear();
        if (mascotas != null) {
            for (var mascota : mascotas) {
                modeloLista.addElement(mascota.getNombreComun() != null && !mascota.getNombreComun().isEmpty()
                        ? mascota.getNombreComun()
                        : "(Sin nombre)");
            }
        }
        listaMascotas.repaint();
    }

    /**
     * Devuelve la lista de mascotas visibles.
     *
     * @return componente JList
     */
    public JList<String> getListaMascotas() {
        return listaMascotas;
    }

    /**
     * Devuelve el panel de formulario de mascota.
     *
     * @return panel del formulario
     */
    public PanelFormularioMascota getPanelFormulario() {
        return panelFormulario;
    }

    /**
     * Devuelve el botón principal de guardado.
     *
     * @return botón guardar
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    /**
     * Cambia dinámicamente el título del formulario.
     *
     * @param texto nuevo texto del título
     */
    public void setTituloFormulario(String texto) {
        lblTituloFormulario.setText(texto);
    }
}
