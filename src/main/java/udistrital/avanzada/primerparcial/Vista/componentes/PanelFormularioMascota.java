package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * Clase {@code PanelFormularioMascota}.
 * <p>
 * Panel reutilizable que contiene los campos de entrada y selección básicos
 * para registrar o editar una mascota exótica. Se utiliza en diferentes vistas
 * como <i>Insertar</i>, <i>Modificar</i> y <i>Completar Datos</i>, manteniendo
 * una apariencia uniforme y coherente en toda la aplicación.
 * </p>
 *
 * <p>
 * Incluye campos de texto personalizados mediante
 * {@link CampoTextoConEtiqueta}, así como listas desplegables para las
 * enumeraciones {@link Clasificacion} y {@link TipoAlimento}. Los componentes
 * se distribuyen verticalmente con espaciado uniforme y estilo visual definido
 * por {@link TemaVisual}.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class PanelFormularioMascota extends JPanel {

    private CampoTextoConEtiqueta campoNombre;
    private CampoTextoConEtiqueta campoApodo;
    private CampoTextoConEtiqueta campoFamilia;
    private CampoTextoConEtiqueta campoGenero;
    private CampoTextoConEtiqueta campoEspecie;
    private JComboBox<Clasificacion> comboClasificacion;
    private JComboBox<TipoAlimento> comboTipoAlimento;

    /**
     * Constructor principal.
     * <p>
     * Configura el diseño vertical del formulario, crea los campos de texto y
     * combos desplegables, y aplica espaciado coherente entre componentes.
     * </p>
     */
    public PanelFormularioMascota() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        int anchoEtiqueta = 130;
        Dimension dCampo = new Dimension(260, 32);

        campoNombre = new CampoTextoConEtiqueta("Nombre común:", 10);
        campoApodo = new CampoTextoConEtiqueta("Apodo:", 10);
        campoFamilia = new CampoTextoConEtiqueta("Familia:", 10);
        campoGenero = new CampoTextoConEtiqueta("Género:", 10);
        campoEspecie = new CampoTextoConEtiqueta("Especie:", 10);

        CampoTextoConEtiqueta[] campos = {
            campoNombre, campoApodo, campoFamilia, campoGenero, campoEspecie
        };

        for (CampoTextoConEtiqueta c : campos) {
            c.setLabelWidth(anchoEtiqueta);
            c.setFieldPreferredSize(dCampo);
            c.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(c);
            add(Box.createVerticalStrut(8));
        }

        comboClasificacion = new JComboBox<>(Clasificacion.values());
        comboTipoAlimento = new JComboBox<>(TipoAlimento.values());

        comboClasificacion.setPreferredSize(dCampo);
        comboTipoAlimento.setPreferredSize(dCampo);

        comboClasificacion.setFont(TemaVisual.FUENTE_TEXTO);
        comboTipoAlimento.setFont(TemaVisual.FUENTE_TEXTO);

        comboClasificacion.setBackground(Color.WHITE);
        comboTipoAlimento.setBackground(Color.WHITE);

        JPanel filaClasificacion = crearFilaCombo("Clasificación:", comboClasificacion, anchoEtiqueta);
        JPanel filaAlimento = crearFilaCombo("Tipo de Alimento:", comboTipoAlimento, anchoEtiqueta);

        add(Box.createVerticalStrut(5));
        add(filaClasificacion);
        add(Box.createVerticalStrut(10));
        add(filaAlimento);
        add(Box.createVerticalStrut(5));
    }

    /**
     * Crea una fila con una etiqueta y un combo desplegable alineados
     * horizontalmente.
     *
     * @param texto texto de la etiqueta
     * @param combo componente desplegable asociado
     * @param anchoEtiqueta ancho fijo de la etiqueta
     * @return panel configurado que contiene ambos elementos
     */
    private JPanel crearFilaCombo(String texto, JComboBox<?> combo, int anchoEtiqueta) {
        JPanel fila = new JPanel(new BorderLayout(8, 0));
        fila.setOpaque(false);

        JLabel lbl = new JLabel(texto);
        lbl.setFont(TemaVisual.FUENTE_TEXTO.deriveFont(Font.BOLD, 14f));
        lbl.setForeground(TemaVisual.TEXTO);
        lbl.setPreferredSize(new Dimension(anchoEtiqueta, 32));

        fila.add(lbl, BorderLayout.WEST);
        fila.add(combo, BorderLayout.CENTER);

        fila.setMaximumSize(new Dimension(400, 32));
        fila.setAlignmentX(Component.CENTER_ALIGNMENT);

        return fila;
    }

    /**
     * Métodos de acceso para obtener los campos y combos del formulario.
     * <p>
     * Se proporcionan getters para cada componente (campos de texto y listas
     * desplegables) que permiten recuperar o modificar sus valores desde la
     * capa de control.
     * </p>
     */
    public CampoTextoConEtiqueta getCampoNombre() {
        return campoNombre;
    }

    public CampoTextoConEtiqueta getCampoApodo() {
        return campoApodo;
    }

    public CampoTextoConEtiqueta getCampoFamilia() {
        return campoFamilia;
    }

    public CampoTextoConEtiqueta getCampoGenero() {
        return campoGenero;
    }

    public CampoTextoConEtiqueta getCampoEspecie() {
        return campoEspecie;
    }

    public JComboBox<Clasificacion> getComboClasificacion() {
        return comboClasificacion;
    }

    public JComboBox<TipoAlimento> getComboTipoAlimento() {
        return comboTipoAlimento;
    }
}
