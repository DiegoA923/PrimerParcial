package udistrital.avanzada.primerparcial.Vista;

import java.awt.*;
import javax.swing.*;
import udistrital.avanzada.primerparcial.Control.ControlVentana;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelCompletarDatos;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelInsertar;
import udistrital.avanzada.primerparcial.Vista.paneles.PanelMenu;

/**
 * Clase VentanaPrincipal.
 * <p>
 * Ventana principal de la aplicación. Gestiona los diferentes paneles mediante
 * un {@link CardLayout} y actúa como punto de entrada del módulo visual.
 * </p>
 *
 * <p>
 * Implementa el patrón MVC en conjunto con {@link ControlVentana}. Los paneles
 * se definen como constantes estáticas para facilitar su acceso.
 * </p>
 *
 * @author Diego
 * @version 1.1
 * @since 2025-10-13
 */
public class VentanaPrincipal extends JFrame {

    // ───────── Constantes de nombres de paneles ─────────
    public static final String PANEL_MENU = "PANEL_MENU";
    public static final String PANEL_COMPLETAR = "PANEL_COMPLETAR";
    public static final String PANEL_INSERTAR = "PANEL_INSERTAR";

    // ───────── Atributos de vista ─────────
    private final CardLayout cardLayout;
    private final JPanel panelContenedor;

    private final PanelMenu panelMenu;
    private final PanelCompletarDatos panelCompletarDatos;
    private final PanelInsertar panelInsertar;

    /**
     * Constructor de la clase VentanaPrincipal.
     */
    public VentanaPrincipal() {
        super("Mascotas Exóticas - Gestión");

        // ───────── Configuración base de la ventana ─────────
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // ───────── CardLayout ─────────
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(Color.WHITE);

        // ───────── Instanciación de paneles ─────────
        panelMenu = new PanelMenu();
        panelCompletarDatos = new PanelCompletarDatos(
                "Completar Datos Faltantes",
                "Datos de la Mascota Seleccionada"
        );
        panelInsertar = new PanelInsertar();

        // ───────── Registro de paneles ─────────
        panelContenedor.add(panelMenu, PANEL_MENU);
        panelContenedor.add(panelCompletarDatos, PANEL_COMPLETAR);
        panelContenedor.add(panelInsertar, PANEL_INSERTAR);

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

    // ───────── Getters para el controlador ─────────
    public PanelMenu getPanelMenu() {
        return panelMenu;
    }

    public PanelCompletarDatos getPanelCompletarDatos() {
        return panelCompletarDatos;
    }

    public PanelInsertar getPanelInsertar() {
        return panelInsertar;
    }
}
