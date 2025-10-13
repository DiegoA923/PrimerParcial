package udistrital.avanzada.primerparcial.Vista.estilos;

import java.awt.*;
import javax.swing.*;

/**
 * TemaVisual.
 * <p>
 * Centraliza los colores, fuentes y utilidades de estilo para toda la
 * aplicación. Cambiar el valor de la constante TEMA aplica un aspecto diferente
 * globalmente.
 * </p>
 *
 * Temas disponibles:
 * <ul>
 * <li>"océano" 🌊 — azul suave y limpio (valor por defecto)</li>
 * <li>"selva" 🌿 — verdes naturales</li>
 * <li>"tierra" 🪵 — cálido y terroso</li>
 * <li>"sakura" 🌸 — pastel rosado y gris claro</li>
 * </ul>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-15
 */
public class TemaVisual {

    // Cambia aquí el tema (usar valores: "océano", "selva", "tierra", "sakura")
    private static final String TEMA = "océano";

    // Colores principales (expuestos para que todas las vistas los utilicen)
    public static final Color FONDO;
    public static final Color ENCABEZADO;
    public static final Color TEXTO;
    public static final Color PRIMARIO;
    public static final Color PRIMARIO_OSCURO;

    // Botones
    public static final Color BOTON_FONDO;
    public static final Color BOTON_HOVER;
    public static final Color BOTON_TEXTO;

    // Paneles / áreas
    public static final Color PANEL_IZQUIERDO_BG;
    public static final Color PANEL_DERECHO_BG;
    public static final Color PANEL_LISTA_BG;

    // Tipografías
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_TEXTO = new Font("Segoe UI", Font.PLAIN, 14);

    static {
        switch (TEMA.toLowerCase()) {

            // ----------------- SELVA -----------------
            case "selva":
                FONDO = new Color(0xEAF2E3);
                ENCABEZADO = new Color(0x4F704F);
                TEXTO = new Color(0x1E2A1E);
                PRIMARIO = new Color(0x7FB77E);
                PRIMARIO_OSCURO = new Color(0x557A46);

                BOTON_FONDO = PRIMARIO;
                BOTON_HOVER = new Color(0x6AA56A);
                BOTON_TEXTO = Color.WHITE;

                PANEL_IZQUIERDO_BG = new Color(0xE2EEDC);
                PANEL_DERECHO_BG = new Color(0xFAFDF8);
                PANEL_LISTA_BG = new Color(0xEDF7EA);
                break;

            // ----------------- OCÉANO (por defecto) -----------------
            case "océano":
            default:
                FONDO = new Color(0xE6F3F8);
                ENCABEZADO = new Color(0x2E5C7B);
                TEXTO = new Color(0x1E2A38);
                PRIMARIO = new Color(0x3A87B9);
                PRIMARIO_OSCURO = new Color(0x2B6D96);

                BOTON_FONDO = PRIMARIO;
                BOTON_HOVER = new Color(0x56A9D8);
                BOTON_TEXTO = Color.WHITE;

                PANEL_IZQUIERDO_BG = new Color(0xD8EBF3);
                PANEL_DERECHO_BG = new Color(0xF7FBFC);
                PANEL_LISTA_BG = new Color(0xE1F2FA);
                break;

            // ----------------- TIERRA -----------------
            case "tierra":
                FONDO = new Color(0xF3EDE0);
                ENCABEZADO = new Color(0x8B6F47);
                TEXTO = new Color(0x3E2C1A);
                PRIMARIO = new Color(0xB9925E);
                PRIMARIO_OSCURO = new Color(0x7B5935);

                BOTON_FONDO = PRIMARIO;
                BOTON_HOVER = new Color(0xA67E4E);
                BOTON_TEXTO = Color.WHITE;

                PANEL_IZQUIERDO_BG = new Color(0xF7F1E6);
                PANEL_DERECHO_BG = new Color(0xFAF7F3);
                PANEL_LISTA_BG = new Color(0xEFE4D1);
                break;

            // ----------------- SAKURA -----------------
            case "sakura":
                FONDO = new Color(0xFFF8FA);
                ENCABEZADO = new Color(0xEAB5C9);
                TEXTO = new Color(0x3F3F3F);
                PRIMARIO = new Color(0xF7C7DB);
                PRIMARIO_OSCURO = new Color(0xCE9EB6);

                BOTON_FONDO = PRIMARIO;
                BOTON_HOVER = new Color(0xE5AFC8);
                BOTON_TEXTO = new Color(60, 60, 60);

                PANEL_IZQUIERDO_BG = new Color(0xFFF1F5);
                PANEL_DERECHO_BG = new Color(0xFFFDFE);
                PANEL_LISTA_BG = new Color(0xFFF5F8);
                break;
        }
    }

    /**
     * Aplica estilo coherente a un JButton (uso centralizado para evitar
     * duplicar).
     * <p>
     * Se recomienda llamar a este método justo después de crear el botón.
     * </p>
     *
     * @param boton JButton a estilizar
     */
    public static void aplicarEstiloBoton(JButton boton) {
        boton.setBackground(BOTON_FONDO);
        boton.setForeground(BOTON_TEXTO);
        boton.setFont(FUENTE_TEXTO.deriveFont(Font.BOLD, 14f));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(BOTON_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(BOTON_FONDO);
            }
        });
    }

    // Evitar instanciación
    private TemaVisual() {
    }
}
