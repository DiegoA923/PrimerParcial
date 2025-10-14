package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * Clase {@code EncabezadoSeccion}.
 * <p>
 * Componente visual reutilizable que representa un encabezado decorativo para
 * las distintas secciones o paneles de la aplicación. Incluye un ícono, un
 * título principal y un subtítulo opcional, todos con estilos definidos por la
 * clase {@link TemaVisual}.
 * </p>
 *
 * <p>
 * Este componente extiende {@link JPanel} y aplica un fondo con degradado de
 * color, adaptándose al tamaño del contenedor. Es utilizado principalmente en
 * los paneles de la interfaz gráfica para mantener consistencia visual entre
 * secciones.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class EncabezadoSeccion extends JPanel {

    private JLabel titulo;
    private JLabel subtitulo;
    private JLabel icono;

    /**
     * Constructor de la clase {@code EncabezadoSeccion}.
     * <p>
     * Inicializa el encabezado con un ícono, un título y un subtítulo,
     * aplicando los estilos definidos en {@link TemaVisual}.
     * </p>
     *
     * @param iconoTexto texto o emoji que se mostrará como ícono
     * @param tituloTexto texto principal del encabezado
     * @param subtituloTexto texto descriptivo o informativo bajo el título
     */
    public EncabezadoSeccion(String iconoTexto, String tituloTexto, String subtituloTexto) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(900, 100));
        setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        setOpaque(false);

        this.icono = new JLabel(iconoTexto, SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        add(icono, BorderLayout.WEST);

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);

        titulo = new JLabel(tituloTexto);
        titulo.setFont(TemaVisual.FUENTE_TITULO.deriveFont(Font.BOLD, 26f));
        titulo.setForeground(Color.WHITE);

        subtitulo = new JLabel(subtituloTexto);
        subtitulo.setFont(TemaVisual.FUENTE_TEXTO.deriveFont(Font.PLAIN, 16f));
        subtitulo.setForeground(new Color(255, 255, 255, 200));

        textos.add(titulo);
        textos.add(subtitulo);
        add(textos, BorderLayout.CENTER);
    }

    /**
     * Método sobrescrito para pintar el fondo del encabezado.
     * <p>
     * Dibuja un degradado horizontal basado en los colores definidos en
     * {@link TemaVisual#ENCABEZADO}.
     * </p>
     *
     * @param g objeto {@link Graphics} usado para dibujar el componente
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint grad = new GradientPaint(
                0, 0, TemaVisual.ENCABEZADO.brighter(),
                getWidth(), getHeight(), TemaVisual.ENCABEZADO.darker()
        );
        g2.setPaint(grad);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Actualiza el texto del subtítulo mostrado en el encabezado.
     *
     * @param nuevoTexto nuevo texto que reemplazará al subtítulo actual
     */
    public void actualizarSubtitulo(String nuevoTexto) {
        subtitulo.setText(nuevoTexto);
    }
}
