package udistrital.avanzada.primerparcial.Vista.paneles;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.componentes.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * PanelInsertar
 * <p>
 * Vista dedicada al registro de una nueva mascota exótica. Extiende
 * {@link PanelBaseSeccion} e integra un formulario, además de una
 * barra inferior con botones de acción.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class PanelInsertar extends PanelBaseSeccion {

    private PanelFormularioMascota panelFormulario;
    private JLabel lblTituloFormulario;
    private BotonPersonalizado btnGuardar;
    private BotonPersonalizado btnLimpiar;
    private BotonPersonalizado btnInicio;

    /**
     * Constructor principal.
     * <p>
     * Inicializa el panel de inserción con el subtítulo predefinido y configura
     * todos los componentes gráficos.
     * </p>
     */
    public PanelInsertar() {
        super("Registro de nueva mascota exótica");
        inicializar();
    }

    /**
     * Inicializa la estructura principal del panel.
     * <p>
     * Agrega la zona central (formulario) y la barra inferior (botones).
     * </p>
     */
    private void inicializar() {
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.setOpaque(false);

        contenedorPrincipal.add(crearZonaCentral(), BorderLayout.CENTER);
        contenedorPrincipal.add(crearBarraInferior(), BorderLayout.SOUTH);

        setContenidoCentral(contenedorPrincipal);
    }

    /**
     * Crea la zona central del panel.
     * <p>
     * Contiene el título superior y una tarjeta blanca con el formulario.
     * </p>
     *
     * @return componente que representa la sección central del panel
     */
   
    private JComponent crearZonaCentral() {
        // ----- Título -----
        lblTituloFormulario = new JLabel("Registrar Nueva Mascota", SwingConstants.CENTER);
        lblTituloFormulario.setFont(TemaVisual.FUENTE_TITULO.deriveFont(Font.BOLD, 18f));
        lblTituloFormulario.setForeground(TemaVisual.PRIMARIO_OSCURO);
        lblTituloFormulario.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        // ----- Formulario -----
        panelFormulario = new PanelFormularioMascota();
        panelFormulario.setOpaque(false);

        // ----- Tarjeta visual -----
        JPanel tarjeta = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 240)); // blanco translúcido
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(new Color(0, 0, 0, 40)); // borde sutil
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
                g2.dispose();
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        tarjeta.add(panelFormulario, BorderLayout.CENTER);

        // Wrapper para centrar la tarjeta
        JPanel wrapperTarjeta = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapperTarjeta.setOpaque(false);
        wrapperTarjeta.add(tarjeta);

        // Contenedor principal
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);
        contenedor.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        contenedor.add(lblTituloFormulario, BorderLayout.NORTH);
        contenedor.add(wrapperTarjeta, BorderLayout.CENTER);

        return contenedor;
    }

    /**
     * Crea la barra inferior del panel.
     * <p>
     * Incluye los botones principales de acción: Guardar, Limpiar e Inicio.
     * Aplica estilo visual coherente con {@link TemaVisual}.
     * </p>
     *
     * @return componente con los botones de acción
     */
    private JComponent crearBarraInferior() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 12));
        barra.setBackground(TemaVisual.PANEL_IZQUIERDO_BG);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TemaVisual.PRIMARIO_OSCURO));

        btnGuardar = new BotonPersonalizado("Guardar",
                TemaVisual.BOTON_FONDO, TemaVisual.BOTON_HOVER, TemaVisual.BOTON_TEXTO);
        btnLimpiar = new BotonPersonalizado("Limpiar",
                TemaVisual.BOTON_FONDO, TemaVisual.BOTON_HOVER, TemaVisual.BOTON_TEXTO);
        btnInicio = new BotonPersonalizado("Inicio",
                TemaVisual.PRIMARIO_OSCURO, TemaVisual.BOTON_HOVER, TemaVisual.BOTON_TEXTO);

        btnGuardar.setActionCommand("guardar");
        btnLimpiar.setActionCommand("limpiar");
        btnInicio.setActionCommand("inicio");

        Dimension dBtn = new Dimension(140, 38);
        btnGuardar.setPreferredSize(dBtn);
        btnLimpiar.setPreferredSize(dBtn);
        btnInicio.setPreferredSize(dBtn);

        barra.add(btnGuardar);
        barra.add(btnLimpiar);
        barra.add(btnInicio);

        return barra;
    }

    /**
     * Métodos de acceso a los componentes principales del panel.
     * <p>
     * Permiten al controlador interactuar con los botones y el formulario (por
     * ejemplo, para manejar eventos o recuperar datos del usuario).
     * </p> 
     * @return 
     */
    
    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public JButton getBtnInicio() {
        return btnInicio;
    }

    public PanelFormularioMascota getPanelFormulario() {
        return panelFormulario;
    }

    public void setTituloFormulario(String texto) {
        lblTituloFormulario.setText(texto);
    }
}
