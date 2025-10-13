package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * Clase EtiquetaBonita.
 * <p>
 * Etiqueta con estilo visual coherente con el tema general.
 * Proporciona un diseño atractivo y consistente para los campos de texto.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-11
 */
public class Etiqueta extends JLabel {

    public Etiqueta(String texto) {
        super(texto);
        setFont(TemaVisual.FUENTE_TEXTO.deriveFont(Font.BOLD, 15f));
        setForeground(TemaVisual.TEXTO);
    }
}


