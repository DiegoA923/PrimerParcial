package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * CampoTextoConEtiqueta.
 * <p>
 * Componente que agrupa una etiqueta y un campo de texto con estilo
 * consistente. Provee accesores para manipular el JTextField desde
 * controladores y utilidades para fijar ancho de etiqueta y tamaño del campo
 * (para UI consistente).
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-12
 */
public class CampoTextoConEtiqueta extends JPanel {

    private JLabel etiqueta;
    private JTextField campo;

    public CampoTextoConEtiqueta(String textoEtiqueta, int columnas) {
        super(new BorderLayout(8, 0));
        etiqueta = new JLabel(textoEtiqueta);
        etiqueta.setFont(TemaVisual.FUENTE_TEXTO.deriveFont(Font.BOLD, 14f));
        etiqueta.setForeground(TemaVisual.TEXTO);

        campo = new JTextField(columnas);
        campo.setFont(TemaVisual.FUENTE_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(6, 8, 6, 8)
        ));
        campo.setBackground(Color.WHITE);

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        add(etiqueta, BorderLayout.WEST);
        add(campo, BorderLayout.CENTER);
    }

    
    // Devuelve el JTextField interno.
     
    public JTextField getCampo() {
        return campo;
    }

    
    // Alias para compatibilidad.
     
    public JTextField getCampoTexto() {
        return campo;
    }

    public String getTexto() {
        return campo.getText().trim();
    }

    public void setTexto(String texto) {
        campo.setText(texto);
    }

    public void setEditable(boolean editable) {
        campo.setEditable(editable);
        campo.setBackground(editable ? new Color(230, 255, 230) : new Color(240, 240, 240));
    }

    public void setHighlighted(boolean highlighted) {
        if (highlighted) {
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(130, 200, 130), 2, true),
                    new EmptyBorder(4, 8, 4, 8)
            ));
        } else {
            campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                    new EmptyBorder(4, 8, 4, 8)
            ));
        }
    }

    /**
     * Fija el ancho (px) de la etiqueta. Esto evita que etiquetas largas
     * empujen al campo.
     */
    public void setLabelWidth(int anchoPx) {
        Dimension d = new Dimension(anchoPx, etiqueta.getPreferredSize().height);
        etiqueta.setPreferredSize(d);
        etiqueta.setMinimumSize(d);
        etiqueta.setMaximumSize(d);
    }

    
    // Fija el tamaño preferido del campo de texto (JTextField).
     
    public void setFieldPreferredSize(Dimension d) {
        campo.setPreferredSize(d);
        campo.setMinimumSize(d);
        campo.setMaximumSize(d);
    }
}
