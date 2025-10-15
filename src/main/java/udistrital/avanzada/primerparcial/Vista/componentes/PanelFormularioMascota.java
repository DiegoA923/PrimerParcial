package udistrital.avanzada.primerparcial.Vista.componentes;

import javax.swing.*;
import java.awt.*;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;

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

    /**
     * Carga los datos de una mascota existente en el formulario.
     * <p>
     * Este método recibe un objeto {@link MascotaVO} y actualiza los campos de
     * texto y listas desplegables del formulario con los valores
     * correspondientes. Es útil cuando se desea mostrar o editar la información
     * de una mascota seleccionada desde la tabla del menú principal.
     * </p>
     *
     * @param mascota objeto {@link MascotaVO} que contiene la información de la
     * mascota a mostrar. Si es {@code null}, el método no realiza ninguna
     * acción.
     */
    public void cargarDatosMascota(MascotaVO mascota) {
        if (mascota == null) {
            return;
        }

        campoNombre.getCampoTexto().setText(mascota.getNombreComun());
        campoApodo.getCampoTexto().setText(mascota.getApodo());
        campoFamilia.getCampoTexto().setText(mascota.getFamilia());
        campoGenero.getCampoTexto().setText(mascota.getGenero());
        campoEspecie.getCampoTexto().setText(mascota.getEspecie());

        comboClasificacion.setSelectedItem(mascota.getClasificacion());
        comboTipoAlimento.setSelectedItem(mascota.getTipoAlimento());
    }

    /**
     * Obtiene los valores ingresados actualmente en el formulario y los
     * encapsula en un nuevo objeto {@link MascotaVO}.
     * <p>
     * Este método se utiliza normalmente cuando se desea registrar o actualizar
     * los datos de una mascota en la base de datos. La información capturada se
     * devuelve lista para ser procesada por la capa de control o el DAO
     * correspondiente.
     * </p>
     *
     * @return objeto {@link MascotaVO} construido a partir de los valores
     * ingresados en el formulario.
     */
    public MascotaVO obtenerDatosMascota() {
        MascotaVO mascota = new MascotaVO();

        mascota.setNombreComun(campoNombre.getCampoTexto().getText().trim());
        mascota.setApodo(campoApodo.getCampoTexto().getText().trim());
        mascota.setFamilia(campoFamilia.getCampoTexto().getText().trim());
        mascota.setGenero(campoGenero.getCampoTexto().getText().trim());
        mascota.setEspecie(campoEspecie.getCampoTexto().getText().trim());
        mascota.setClasificacion((Clasificacion) comboClasificacion.getSelectedItem());
        mascota.setTipoAlimento((TipoAlimento) comboTipoAlimento.getSelectedItem());

        return mascota;
    }

}
