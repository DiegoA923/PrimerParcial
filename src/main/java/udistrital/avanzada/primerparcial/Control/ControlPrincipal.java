package udistrital.avanzada.primerparcial.Control;

import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;
import java.io.File;
import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.Config;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.SerializableDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Controla el flujo general del programa desde el arranque, coordinando la
 * lógica del sistema, la interfaz gráfica y la persistencia de datos. Crea la
 * vista principal y delega los eventos generales a {@link ControlVentana}.
 * </p>
 *
 * @author Diego
 * @version 1.1
 * @since 2025-10-13
 *
 * <p>
 * <b>Modificación:</b> Se integró la inicialización de los componentes de
 * lógica ({@link ControlMascota}, {@link SerializableDAO}) y la creación de la
 * ventana principal. Además, se añadió la gestión del archivo serializado y
 * aleatorio para mantener la persistencia de las mascotas.
 * </p>
 */
public class ControlPrincipal {

    private final VentanaPrincipal vista;
    private final ControlVentana controlVentana;
    private final SerializableDAO serializableDAO;
    private final ControlMascota controlMascota;

    /**
     * Constructor principal del controlador general.
     * <p>
     * Inicializa la lógica del sistema, la vista principal y gestiona la carga
     * o creación del archivo serializado de mascotas.
     * </p>
     */
    public ControlPrincipal() {
        this.serializableDAO = new SerializableDAO();
        this.controlMascota = new ControlMascota();

        this.vista = new VentanaPrincipal();
        this.controlVentana = new ControlVentana(vista, this);

        inicializar();
    }

    
    // Inicializa el flujo de la aplicación verificando la existencia del archivo serializado.
     
    private void inicializar() {
        File archivoSerializado = new File(Config.RUTA_PREDETERMINADA_ARCHIVO_SERIALIZADO_ANIMALES);

        if (archivoSerializado.exists()) {
            // Si ya existe, se solicita al usuario seleccionar un archivo
            controlVentana.obtenerArchivoSerializado(Config.RUTA_CARPETA_PRECARGA);
        } else {
            serializableDAO.setArchivo(archivoSerializado);
        }

        vista.setVisible(true);
        vista.mostrarPanel(VentanaPrincipal.PANEL_COMPLETAR);
    }

    /**
     * Procesa el archivo serializado seleccionado por el usuario.
     *
     * @param archivo archivo serializado a procesar
     */
    public void procesarArchivoSerializado(File archivo) {
        serializableDAO.setArchivo(archivo);
    }

    
    // Carga las mascotas desde el archivo serializado y las inserta en la base de datos.
   
    public void insertarDatosDesdeSerializador() {
        try {
            ArrayList<MascotaVO> mascotas = serializableDAO.listaDeMascotas();
            for (MascotaVO mascota : mascotas) {
                controlMascota.insertarMascota(mascota);
            }
        } catch (RuntimeException e) {
            // Manejo de error opcional (log o mensaje)
        }
    }

    
    // Guarda los datos antes de salir de la aplicación.
    
    public void salir() {
        ArrayList<MascotaVO> mascotas;

        try {
            mascotas = controlMascota.obtenerMascotas();
        } catch (RuntimeException e) {
            return;
        }

        if (mascotas == null || mascotas.isEmpty()) {
            return;
        }

        try {
            serializableDAO.guardar(mascotas);
        } catch (RuntimeException e) {
            // Log o manejo de error
        }

        // Persistir los datos en archivo aleatorio
        GestorArchivoAleatorio gestor = new GestorArchivoAleatorio();
        gestor.setRutaArchivo(Config.RUTA_PREDETERMINADA_ARCHIVO_AlEATORIO_ANIMALES);
        try {
            gestor.insertarMascotas(mascotas);
        } catch (RuntimeException e) {
            // Log o manejo de error
        }
    }
}