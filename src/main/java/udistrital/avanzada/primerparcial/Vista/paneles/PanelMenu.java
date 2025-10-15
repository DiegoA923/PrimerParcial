package udistrital.avanzada.primerparcial.Vista.paneles;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.primerparcial.Modelo.*;
import udistrital.avanzada.primerparcial.Vista.componentes.*;
import udistrital.avanzada.primerparcial.Vista.estilos.TemaVisual;

/**
 * PanelMenu.
 * <p>
 * Vista principal que muestra la tabla de mascotas exóticas con búsqueda,
 * filtrado y barra inferior de acciones (Insertar, Modificar, Eliminar, Salir).
 * Utiliza colores y estilos definidos en {@link TemaVisual}.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-13
 */
public class PanelMenu extends JPanel {

    // Componentes de búsqueda / tabla
    private PanelBaseSeccion base;
    private JTable tablaMascotas;
    private DefaultTableModel modeloTabla;
    private JTextField campoBusqueda;
    private JComboBox<String> comboFiltro;
    private JComboBox<Enum<?>> comboEnum;
    private JButton btnRefrescar;
    private List<MascotaVO> mascotas = new ArrayList<>();


    // Botones (barra inferior)
    private BotonPersonalizado btnInsertar;
    private BotonPersonalizado btnModificar;
    private BotonPersonalizado btnEliminar;
    private BotonPersonalizado btnSalir;

    public PanelMenu() {
        inicializar();
    }

    
    // Inicializa la interfaz del panel.
    private void inicializar() {
        setLayout(new BorderLayout());
        setBackground(TemaVisual.FONDO);

        // Panel base con encabezado (PanelBaseSeccion ya contiene título y subtítulo)
        base = new PanelBaseSeccion("Gestión de Mascotas Exóticas");
        base.setOpaque(false);

        // Bloque central que contiene búsqueda y tabla (visualmente integrados)
        JPanel bloqueCentral = new JPanel(new BorderLayout(8, 8));
        bloqueCentral.setOpaque(false);
        bloqueCentral.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        bloqueCentral.add(crearPanelBusqueda(), BorderLayout.NORTH);
        bloqueCentral.add(crearPanelTabla(), BorderLayout.CENTER);

        base.setContenidoCentral(bloqueCentral);
        add(base, BorderLayout.CENTER);

        // Barra inferior con acciones
        add(crearBarraInferior(), BorderLayout.SOUTH);
    }

    
    // Crea el panel superior de búsqueda (filtro inteligente + refrescar). 
    private JComponent crearPanelBusqueda() {
        JPanel caja = new JPanel(new GridBagLayout());
        caja.setOpaque(true);
        caja.setBackground(TemaVisual.PANEL_DERECHO_BG);
        caja.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(6, 8, 6, 8),
                BorderFactory.createLineBorder(TemaVisual.PRIMARIO_OSCURO, 1)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboFiltro = new JComboBox<>(new String[]{"Apodo", "Familia", "Clasificación", "Tipo de alimento"});
        comboFiltro.setFont(TemaVisual.FUENTE_TEXTO);
        comboFiltro.setBackground(Color.WHITE);

        campoBusqueda = new JTextField();
        campoBusqueda.setFont(TemaVisual.FUENTE_TEXTO);
        campoBusqueda.setBorder(BorderFactory.createLineBorder(TemaVisual.PRIMARIO_OSCURO, 1));

        comboEnum = new JComboBox<>();
        comboEnum.setFont(TemaVisual.FUENTE_TEXTO);
        comboEnum.setVisible(false);

        // Botón refrescar pequeño con símbolo unicode (más compatible)
        btnRefrescar = new JButton("\u21BB"); // ⟳
        btnRefrescar.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        btnRefrescar.setForeground(TemaVisual.BOTON_TEXTO);
        btnRefrescar.setBackground(TemaVisual.PRIMARIO_OSCURO);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        btnRefrescar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRefrescar.setToolTipText("Refrescar tabla (mostrar todos)");
        // aplicar estilo hover simple
        btnRefrescar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnRefrescar.setBackground(TemaVisual.PRIMARIO);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnRefrescar.setBackground(TemaVisual.PRIMARIO_OSCURO);
            }
        });

        // Listeners del filtro
        comboFiltro.addActionListener(e -> cambiarTipoEntrada());
        

        // Layout: filtro | input (texto o enum) | refrescar
        gbc.gridx = 0;
        gbc.weightx = 0.25;
        caja.add(comboFiltro, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        caja.add(campoBusqueda, gbc);
        // comboEnum se añade en la misma celda para ser reemplazo visual del campo
        caja.add(comboEnum, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.15;
        caja.add(btnRefrescar, gbc);

        return caja;
    }

    //  Crea y configura la tabla que muestra las mascotas.
    private JComponent crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        String[] columnas = {"ID", "Nombre Común", "Apodo", "Clasificación", "Familia", "Género", "Especie", "Tipo de alimento"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaMascotas = new JTable(modeloTabla);
        tablaMascotas.setFont(TemaVisual.FUENTE_TEXTO);
        tablaMascotas.setRowHeight(28);
        tablaMascotas.getTableHeader().setFont(TemaVisual.FUENTE_TEXTO.deriveFont(Font.BOLD, 13f));
        tablaMascotas.getTableHeader().setBackground(TemaVisual.PRIMARIO_OSCURO);
        tablaMascotas.getTableHeader().setForeground(Color.WHITE);

        // filas alternadas + selección con color del tema
        tablaMascotas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color even = TemaVisual.PANEL_LISTA_BG;
            private final Color odd = TemaVisual.PANEL_DERECHO_BG;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(TemaVisual.PRIMARIO);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? even : odd);
                    c.setForeground(TemaVisual.TEXTO);
                }
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tablaMascotas);
        scroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TemaVisual.PRIMARIO_OSCURO, 1),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // Crea la barra inferior con acciones principales.
    private JComponent crearBarraInferior() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 10));
        barra.setBackground(TemaVisual.PANEL_IZQUIERDO_BG);
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TemaVisual.PRIMARIO_OSCURO));

        btnInsertar = new BotonPersonalizado("Insertar", TemaVisual.BOTON_FONDO, TemaVisual.BOTON_HOVER, TemaVisual.BOTON_TEXTO);
        btnModificar = new BotonPersonalizado("Modificar", TemaVisual.BOTON_FONDO, TemaVisual.BOTON_HOVER, TemaVisual.BOTON_TEXTO);
        btnEliminar = new BotonPersonalizado("Eliminar", TemaVisual.BOTON_FONDO, TemaVisual.BOTON_HOVER, TemaVisual.BOTON_TEXTO);
        btnSalir = new BotonPersonalizado("Salir", TemaVisual.PRIMARIO_OSCURO, TemaVisual.BOTON_HOVER, TemaVisual.BOTON_TEXTO);

        btnInsertar.setPreferredSize(new Dimension(120, 38));
        btnModificar.setPreferredSize(new Dimension(120, 38));
        btnEliminar.setPreferredSize(new Dimension(120, 38));
        btnSalir.setPreferredSize(new Dimension(120, 38));

        barra.add(btnInsertar);
        barra.add(btnModificar);
        barra.add(btnEliminar);
        barra.add(btnSalir);

        return barra;
    }

    //Cambia el tipo de entrada (texto <-> enum) según el filtro seleccionado.
    private void cambiarTipoEntrada() {
        String filtro = (String) comboFiltro.getSelectedItem();
        boolean esEnum = "Clasificación".equals(filtro) || "Tipo de alimento".equals(filtro);

        campoBusqueda.setVisible(!esEnum);
        comboEnum.setVisible(esEnum);

        if (esEnum) {
            comboEnum.removeAllItems();
            if ("Clasificación".equals(filtro)) {
                for (Clasificacion c : Clasificacion.values()) {
                    comboEnum.addItem(c);
                }
            } else {
                for (TipoAlimento t : TipoAlimento.values()) {
                    comboEnum.addItem(t);
                }
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Refresca la tabla con la lista proporcionada desde el controlador.
     * <p>
     * Este método ahora es público para cumplir con el patrón MVC y permitir
     * que el controlador actualice directamente la vista tras consultas al DAO.
     * Ya no existen datos locales ni ejemplos en memoria.
     * </p>
     *
     * @param lista lista de mascotas obtenida desde la base de datos
     */
    public void refrescarTabla(List<MascotaVO> lista) {
        modeloTabla.setRowCount(0);
        if (lista == null || lista.isEmpty()) {
            return;
        }
        for (MascotaVO m : lista) {
            modeloTabla.addRow(new Object[]{
                m.getId(),
                m.getNombreComun(),
                m.getApodo(),
                m.getClasificacion(),
                m.getFamilia(),
                m.getGenero(),
                m.getEspecie(),
                m.getTipoAlimento()
            });
        }
        this.mascotas = lista; // Sincroniza la lista con la tabla
    }

    /**
     * Devuelve la mascota seleccionada en la tabla de registro.
     * <p>
     * Este método permite al controlador identificar qué mascota fue
     * seleccionada por el usuario para realizar operaciones como modificación o
     * eliminación. Si no hay ninguna fila seleccionada o la lista está vacía,
     * devuelve {@code null}.
     * </p>
     *
     * @return la instancia de {@link MascotaVO} correspondiente a la fila
     * seleccionada, o {@code null} si no hay selección válida.
     */
    public MascotaVO getMascotaSeleccionada() {
        int fila = tablaMascotas.getSelectedRow();

        // Validación: sin selección o lista inconsistente
        if (fila == -1 || mascotas == null || fila >= mascotas.size()) {
            return null;
        }

        // Retorna la mascota correspondiente a la fila seleccionada
        return mascotas.get(fila);
    }
    
    

    /**
     * Métodos de acceso a los botones, tabla y filtros del panel.
     * <p>
     * Permiten al controlador vincular eventos y actualizar los datos
     * mostrados.
     * </p>
     */
    public JButton getBtnInsertar() {
        return btnInsertar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    // Exponer tabla y filtros si el controlador necesita operar sobre ellos
    public JTable getTablaMascotas() {
        return tablaMascotas;
    }

    public JTextField getCampoBusqueda() {
        return campoBusqueda;
    }

    public JComboBox<String> getComboFiltro() {
        return comboFiltro;
    }

    public JComboBox<Enum<?>> getComboEnum() {
        return comboEnum;
    }

    public JButton getBtnRefrescar() {
        return btnRefrescar;
    }
}
