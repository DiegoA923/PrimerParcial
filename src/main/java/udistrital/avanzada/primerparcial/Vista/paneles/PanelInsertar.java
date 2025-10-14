package udistrital.avanzada.primerparcial.Vista.paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import udistrital.avanzada.primerparcial.Vista.componentes.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * PanelInsertar
 * <p>
 * Vista dedicada al registro de una nueva mascota exótica. Extiende
 * {@link PanelBaseSeccion} e integra un formulario desplazable con barra de
 * scroll personalizada, además de una barra inferior con botones de acción.
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
     * Contiene el título superior y una tarjeta blanca con el formulario
     * desplazable dentro de un {@link JScrollPane}.
     * </p>
     *
     * @return componente que representa la sección central del panel
     */
    private JComponent crearZonaCentral() {
        lblTituloFormulario = new JLabel("Registrar Nueva Mascota", SwingConstants.CENTER);
        lblTituloFormulario.setFont(TemaVisual.FUENTE_TITULO.deriveFont(Font.BOLD, 18f));
        lblTituloFormulario.setForeground(TemaVisual.PRIMARIO_OSCURO);
        lblTituloFormulario.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        panelFormulario = new PanelFormularioMascota();

        // Envolver formulario en un panel que permita crecer verticalmente
        JPanel wrapperFormulario = new JPanel(new BorderLayout());
        wrapperFormulario.setOpaque(false);
        wrapperFormulario.add(panelFormulario, BorderLayout.NORTH);

        JScrollPane scroll = crearScrollFormulario(wrapperFormulario);

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
        tarjeta.setPreferredSize(new Dimension(520, 450));
        tarjeta.add(scroll, BorderLayout.CENTER);

        JPanel wrapperTarjeta = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        wrapperTarjeta.setOpaque(false);
        wrapperTarjeta.add(tarjeta);

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
     * Crea un {@link JScrollPane} con estilo personalizado.
     * <p>
     * Define un diseño minimalista con una barra de desplazamiento vertical que
     * cambia de color al pasar el cursor (efecto hover).
     * </p>
     *
     * @param contenido componente que se mostrará dentro del área desplazable
     * @return {@link JScrollPane} estilizado
     */
    private JScrollPane crearScrollFormulario(JComponent contenido) {
        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar verticalBar = scroll.getVerticalScrollBar();
        verticalBar.setUnitIncrement(16);
        verticalBar.setPreferredSize(new Dimension(10, 0));
        verticalBar.setBackground(new Color(255, 255, 255, 180));

        // Personalización avanzada con efecto hover
        verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            private Color thumbBase = TemaVisual.PRIMARIO.darker();
            private Color thumbHover = TemaVisual.PRIMARIO.brighter();
            private boolean isHovering = false;

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = thumbBase;
                this.trackColor = new Color(255, 255, 255, 120);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(isHovering ? thumbHover : thumbBase);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return crearBotonInvisible();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return crearBotonInvisible();
            }

            private JButton crearBotonInvisible() {
                JButton boton = new JButton();
                boton.setPreferredSize(new Dimension(0, 0));
                boton.setVisible(false);
                return boton;
            }

            @Override
            protected void installListeners() {
                super.installListeners();
                scrollbar.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        isHovering = true;
                        scrollbar.repaint();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        isHovering = false;
                        scrollbar.repaint();
                    }
                });
            }
        });

        return scroll;
    }

    /**
     * Métodos de acceso a los componentes principales del panel.
     * <p>
     * Permiten al controlador interactuar con los botones y el formulario (por
     * ejemplo, para manejar eventos o recuperar datos del usuario).
     * </p>
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
