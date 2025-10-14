package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * BotónPersonalizado.
 * <p>
 * Botón estilizado con colores, bordes redondeados y efectos hover.
 * </p>
 *
 * @author Diego
 * @version 1.1
 * @since 2025-10-11
 */
public class BotonPersonalizado extends JButton {

    private Color colorFondo;
    private Color colorHover;
    private Color colorTexto;

    /**
     * Crea un botón con colores personalizados.
     *
     * @param texto texto a mostrar
     * @param fondo color de fondo normal
     * @param hover color al pasar el ratón
     * @param textoColor color del texto
     */
    public BotonPersonalizado(String texto, Color fondo, Color hover, Color textoColor) {
        super(texto);
        this.colorFondo = fondo;
        this.colorHover = hover;
        this.colorTexto = textoColor;

        setForeground(colorTexto);
        setBackground(colorFondo);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(130, 40));

        // Redondeado + efecto hover
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(colorFondo);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
        g2.dispose();
    }
}
