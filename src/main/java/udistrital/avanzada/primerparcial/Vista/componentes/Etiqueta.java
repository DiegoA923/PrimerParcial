package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * Clase {@code Etiqueta}.
 * <p>
 * Componente visual que extiende {@link JLabel} para mostrar texto con un
 * estilo coherente al tema gráfico general de la aplicación. Utiliza las
 * fuentes y colores definidos en {@link TemaVisual} para mantener uniformidad
 * visual en la interfaz.
 * </p>
 *
 * <p>
 * Esta clase se emplea principalmente en los paneles de la capa Vista,
 * acompañando a campos de texto u otros elementos informativos.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-11
 */
public class Etiqueta extends JLabel {

    /**
     * Constructor de la clase {@code Etiqueta}.
     * <p>
     * Inicializa la etiqueta con el texto indicado y aplica el estilo visual
     * definido en {@link TemaVisual}.
     * </p>
     *
     * @param texto texto que mostrará la etiqueta
     */
    public Etiqueta(String texto) {
        super(texto);
        setFont(TemaVisual.FUENTE_TEXTO.deriveFont(Font.BOLD, 15f));
        setForeground(TemaVisual.TEXTO);
    }
}
