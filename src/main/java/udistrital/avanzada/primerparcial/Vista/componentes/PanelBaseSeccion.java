package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * Clase {@code PanelBaseSeccion}.
 * <p>
 * Componente base que define la estructura visual común de todas las secciones
 * de la aplicación. Proporciona un encabezado con ícono, título principal y
 * subtítulo dinámico, junto con un contenedor central intercambiable donde
 * pueden insertarse otros paneles específicos.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class PanelBaseSeccion extends JPanel {

    private JLabel lblSubtitulo;
    private JPanel contenedorCentral;

    /**
     * Constructor de la clase {@code PanelBaseSeccion}.
     * <p>
     * Inicializa el panel base con el color de fondo definido en
     * {@link TemaVisual}, agrega el encabezado superior y el contenedor central
     * vacío.
     * </p>
     *
     * @param subtitulo texto que se mostrará como subtítulo en el encabezado
     */
    public PanelBaseSeccion(String subtitulo) {
        setLayout(new BorderLayout());
        setBackground(TemaVisual.FONDO);

        add(crearEncabezado(subtitulo), BorderLayout.NORTH);
        add(crearContenedorCentral(), BorderLayout.CENTER);
    }

    /**
     * Crea el encabezado común de la sección, que incluye un ícono decorativo,
     * el título principal de la aplicación y el subtítulo dinámico.
     *
     * @param subtitulo texto inicial del subtítulo mostrado
     * @return panel configurado que representa el encabezado
     */
    private JPanel crearEncabezado(String subtitulo) {
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint grad = new GradientPaint(
                        0, 0, TemaVisual.ENCABEZADO.brighter(),
                        getWidth(), getHeight(), TemaVisual.ENCABEZADO.darker());
                g2.setPaint(grad);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(900, 100));
        header.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JLabel icono = new JLabel("🐾", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        header.add(icono, BorderLayout.WEST);

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);

        JLabel titulo = new JLabel("Mascotas Exóticas", SwingConstants.LEFT);
        titulo.setFont(TemaVisual.FUENTE_TITULO.deriveFont(Font.BOLD, 26f));
        titulo.setForeground(Color.WHITE);

        lblSubtitulo = new JLabel(subtitulo, SwingConstants.LEFT);
        lblSubtitulo.setFont(TemaVisual.FUENTE_TEXTO.deriveFont(Font.PLAIN, 16f));
        lblSubtitulo.setForeground(new Color(255, 255, 255, 200));

        textos.add(titulo);
        textos.add(lblSubtitulo);
        header.add(textos, BorderLayout.CENTER);

        return header;
    }

    /**
     * Crea el panel central vacío, destinado a contener los componentes
     * específicos de cada sección. Este contenedor puede ser reemplazado
     * dinámicamente con {@link #setContenidoCentral(JComponent)}.
     *
     * @return panel central vacío
     */
    private JPanel crearContenedorCentral() {
        contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setOpaque(false);
        return contenedorCentral;
    }

    /**
     * Establece el contenido del área central del panel.
     * <p>
     * Reemplaza cualquier componente anterior con el nuevo elemento indicado,
     * actualizando la vista de forma inmediata.
     * </p>
     *
     * @param componente componente Swing que se insertará en el área central
     */
    public void setContenidoCentral(JComponent componente) {
        contenedorCentral.removeAll();
        contenedorCentral.add(componente, BorderLayout.CENTER);
        contenedorCentral.revalidate();
        contenedorCentral.repaint();
    }

    /**
     * Cambia dinámicamente el texto del subtítulo mostrado en el encabezado.
     *
     * @param texto nuevo texto a mostrar como subtítulo
     */
    public void setSubtitulo(String texto) {
        lblSubtitulo.setText(texto);
    }

    /**
     * Obtiene la referencia al {@link JLabel} que muestra el subtítulo.
     *
     * @return etiqueta que contiene el subtítulo actual
     */
    public JLabel getLblSubtitulo() {
        return lblSubtitulo;
    }
}
