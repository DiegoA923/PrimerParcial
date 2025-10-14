package udistrital.avanzada.primerparcial.Vista.paneles;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Vista.componentes.BotonPersonalizado;
import udistrital.avanzada.primerparcial.Vista.componentes.PanelBaseSeccion;
import udistrital.avanzada.primerparcial.Vista.componentes.PanelFormularioMascota;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * PanelCompletarDatos.
 * <p>
 * Permite completar los datos faltantes de las mascotas exóticas. Utiliza el
 * encabezado general de {@link PanelBaseSeccion} y un formulario reutilizable
 * de {@link PanelFormularioMascota}.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class PanelCompletarDatos extends PanelBaseSeccion {

    private JList<String> listaMascotas;
    private DefaultListModel<String> modeloLista;
    private PanelFormularioMascota panelFormulario;
    private BotonPersonalizado btnGuardar;
    private JLabel lblTituloFormulario;

    /**
     * Constructor principal.
     *
     * @param subtitulo texto a mostrar debajo del encabezado principal
     * @param tituloFormulario texto que se mostrará encima del formulario
     * (fuera de la tarjeta)
     */
    public PanelCompletarDatos(String subtitulo, String tituloFormulario) {
        super(subtitulo);
        inicializar(tituloFormulario);
    }

    /**
     * Inicializa los componentes y construye la zona central del panel.
     */
    private void inicializar(String tituloFormulario) {
        setContenidoCentral(crearZonaCentral(tituloFormulario));
    }

    /**
     * Crea la zona central del panel (lista izquierda + formulario con
     * tarjeta).
     *
     * @param tituloFormulario título que se mostrará encima del formulario
     * @return componente central del panel
     */
    private JSplitPane crearZonaCentral(String tituloFormulario) {
        // ----------------------------------------
        //  Lado izquierdo: lista de mascotas
        // ----------------------------------------
        modeloLista = new DefaultListModel<>();
        List<String> mascotas = List.of(
                "Loro Azul - incompleto",
                "Iguana Verde - incompleto",
                "Tarántula Roja - incompleto",
                "Serpiente Real - incompleto"
        );
        mascotas.forEach(modeloLista::addElement);

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
        //  Lado derecho: título + tarjeta blanca
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

        // Título encima del formulario
        lblTituloFormulario = new JLabel(tituloFormulario, SwingConstants.CENTER);
        lblTituloFormulario.setFont(TemaVisual.FUENTE_TITULO.deriveFont(Font.BOLD, 18f));
        lblTituloFormulario.setForeground(TemaVisual.PRIMARIO_OSCURO);
        lblTituloFormulario.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));

        // Tarjeta con fondo blanco y bordes redondeados
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

        tarjeta.add(panelFormulario, BorderLayout.CENTER);
        tarjeta.add(panelBoton, BorderLayout.SOUTH);

        // Contenedor derecho que mantiene título arriba y tarjeta centrada debajo
        JPanel contenedorDerecha = new JPanel(new BorderLayout());
        contenedorDerecha.setOpaque(false);
        contenedorDerecha.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel wrapperTarjeta = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapperTarjeta.setOpaque(false);
        wrapperTarjeta.add(tarjeta);

        contenedorDerecha.add(lblTituloFormulario, BorderLayout.NORTH);
        contenedorDerecha.add(wrapperTarjeta, BorderLayout.CENTER);

        // Split principal 
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLista, contenedorDerecha);
        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(5);
        splitPane.setBorder(null);
        splitPane.setOpaque(false);

        listaMascotas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                simularCargaDeDatos();
            }
        });

        return splitPane;
    }

    // Simula la carga de datos de una mascota seleccionada.
    private void simularCargaDeDatos() {
        int index = listaMascotas.getSelectedIndex();
        if (index < 0) {
            return;
        }

        boolean alterno = index % 2 == 0;

        configurarCampo(panelFormulario.getCampoNombre(), alterno ? "" : "Ejemplo Nombre", alterno);
        configurarCampo(panelFormulario.getCampoApodo(), alterno ? "" : "Apodito", alterno);
        configurarCampo(panelFormulario.getCampoFamilia(), alterno ? "" : "Familia Test", alterno);
        configurarCampo(panelFormulario.getCampoGenero(), alterno ? "" : "Masculino", alterno);
        configurarCampo(panelFormulario.getCampoEspecie(), alterno ? "" : "Reptilia", alterno);

        JComboBox<Clasificacion> comboClasificacion = panelFormulario.getComboClasificacion();
        JComboBox<TipoAlimento> comboAlimento = panelFormulario.getComboTipoAlimento();

        comboClasificacion.setSelectedIndex(alterno ? -1 : 0);
        comboClasificacion.setEnabled(alterno);
        comboAlimento.setSelectedIndex(alterno ? -1 : 1);
        comboAlimento.setEnabled(alterno);
    }

    // Configura visualmente un campo editable o bloqueado.
    private void configurarCampo(
            udistrital.avanzada.primerparcial.Vista.componentes.CampoTextoConEtiqueta campo,
            String valor,
            boolean editable
    ) {
        campo.setTexto(valor);
        campo.setEditable(editable);
        campo.setHighlighted(editable);
    }

    /**
     * Métodos de acceso a los principales componentes visuales del panel,
     * permitiendo su manipulación desde el controlador.
     */
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JList<String> getListaMascotas() {
        return listaMascotas;
    }

    public PanelFormularioMascota getPanelFormulario() {
        return panelFormulario;
    }

    /**
     * Cambia dinámicamente el título superior del formulario.
     *
     * @param texto nuevo texto del título
     */
    public void setTituloFormulario(String texto) {
        lblTituloFormulario.setText(texto);
    }
}
