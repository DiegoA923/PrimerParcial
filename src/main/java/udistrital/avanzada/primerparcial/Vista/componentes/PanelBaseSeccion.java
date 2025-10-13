package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * PanelBaseSeccion.
 * <p>
 * Plantilla visual común para todas las secciones de la app.
 * Incluye encabezado con huella, título principal y subtítulo dinámico,
 * además de un contenedor central intercambiable.
 * </p>
 *
 * @author Diego
 * @version 1.0
 */
public class PanelBaseSeccion extends JPanel {

    private JLabel lblSubtitulo;
    private JPanel contenedorCentral;

    public PanelBaseSeccion(String subtitulo) {
        setLayout(new BorderLayout());
        setBackground(TemaVisual.FONDO);

        add(crearEncabezado(subtitulo), BorderLayout.NORTH);
        add(crearContenedorCentral(), BorderLayout.CENTER);
    }

    /**
     * Crea el encabezado común (huellita + títulos).
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
     * Crea un contenedor central que luego otros paneles pueden reemplazar.
     */
    private JPanel crearContenedorCentral() {
        contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setOpaque(false);
        return contenedorCentral;
    }

    /**
     * Permite establecer el contenido del área central.
     * @param componente
     */
    public void setContenidoCentral(JComponent componente) {
        contenedorCentral.removeAll();
        contenedorCentral.add(componente, BorderLayout.CENTER);
        contenedorCentral.revalidate();
        contenedorCentral.repaint();
    }

    /**
     * Cambia el subtítulo dinámicamente.
     * @param texto
     */
    public void setSubtitulo(String texto) {
        lblSubtitulo.setText(texto);
    }

    public JLabel getLblSubtitulo() { return lblSubtitulo; }
}


