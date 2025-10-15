package udistrital.avanzada.primerparcial.Vista;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelCompletarDatos;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelInsertar;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelModificar;

/**
 * Clase VentanaPrincipal.
 * <p>
 * Ventana principal de la aplicación. Gestiona los diferentes paneles mediante
 * un {@link CardLayout} y actúa como punto de entrada del módulo visual.
 * </p>
 *
 * @author Diego
 * @version 1.2
 * @since 2025-10-13
 * 
 * <p>
 * <b>Modificación:</b> Se integró el método {@code getFileChoser(...)} proveniente
 * de la rama <i>develop</i> para permitir la selección de archivos dentro de la
 * interfaz, manteniendo la estructura original basada en {@code JFrame} y
 * el manejo de paneles con {@code CardLayout}.
 * </p>
 */
public class VentanaPrincipal extends JFrame {

    // Constantes de nombres de paneles
    public static final String PANEL_MENU = "PANEL_MENU";
    public static final String PANEL_COMPLETAR = "PANEL_COMPLETAR";
    public static final String PANEL_INSERTAR = "PANEL_INSERTAR";
    public static final String PANEL_MODIFICAR = "PANEL_MODIFICAR";
 
    // Atributos de vista
    private final CardLayout cardLayout;
    private final JPanel panelContenedor;

    private final PanelMenu panelMenu;
    private final PanelCompletarDatos panelCompletarDatos;
    private final PanelInsertar panelInsertar;
    private final PanelModificar panelModificar;

    
    // Constructor de la clase VentanaPrincipal.
    
    public VentanaPrincipal() {
        super("Mascotas Exóticas - Gestión");

        // Configuración base de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // CardLayout
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(Color.WHITE);

        // Instanciación de paneles
        panelMenu = new PanelMenu();
        panelCompletarDatos = new PanelCompletarDatos(
                "Completar Datos Faltantes",
                "Datos de la Mascota Seleccionada"
        );
        panelInsertar = new PanelInsertar();
        panelModificar = new PanelModificar();

        // Registro de paneles
        panelContenedor.add(panelMenu, PANEL_MENU);
        panelContenedor.add(panelCompletarDatos, PANEL_COMPLETAR);
        panelContenedor.add(panelInsertar, PANEL_INSERTAR);
        panelContenedor.add(panelModificar, PANEL_MODIFICAR);

        add(panelContenedor, BorderLayout.CENTER);
    }

    /**
     * Cambia la vista actual al panel indicado.
     *
     * @param key constante que identifica el panel a mostrar
     */
    public void mostrarPanel(String key) {
        cardLayout.show(panelContenedor, key);
    }

    // Getters para el controlador
    public PanelMenu getPanelMenu() {
        return panelMenu;
    }

    public PanelCompletarDatos getPanelCompletarDatos() {
        return panelCompletarDatos;
    }

    public PanelInsertar getPanelInsertar() {
        return panelInsertar;
    }
    
    public PanelModificar getPanelModificar(){
        return panelModificar;
    }

    /**
     * Muestra una ventana de selección de archivo personalizada.
     *
     * @param descripcion descripción del tipo de archivo a mostrar
     * @param extension extensión de archivo aceptada (sin punto)
     * @param modoSeleccion tipo de selección (por ejemplo,
     * {@link JFileChooser#FILES_ONLY})
     * @param rutaPredeterminada carpeta inicial donde abrir el explorador
     * @return instancia configurada de {@link JFileChooser}
     */
    public JFileChooser getFileChoser(String descripcion, String extension, int modoSeleccion, String rutaPredeterminada) {
        JFileChooser fileChooser = new JFileChooser();
        File carpetaInicial = new File(rutaPredeterminada);
        fileChooser.setCurrentDirectory(carpetaInicial);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(descripcion, extension);
        fileChooser.setFileFilter(filtro);
        fileChooser.setFileSelectionMode(modoSeleccion);
        return fileChooser;
    }
}
