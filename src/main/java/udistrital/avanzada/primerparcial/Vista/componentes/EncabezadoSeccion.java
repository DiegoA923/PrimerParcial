package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

public class EncabezadoSeccion extends JPanel {
    private JLabel titulo;
    private JLabel subtitulo;
    private JLabel icono;

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

    public void actualizarSubtitulo(String nuevoTexto) {
        subtitulo.setText(nuevoTexto);
    }
}


