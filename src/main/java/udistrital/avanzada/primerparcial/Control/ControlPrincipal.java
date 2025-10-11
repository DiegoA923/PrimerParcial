package udistrital.avanzada.primerparcial.Control;

import java.io.File;
import udistrital.avanzada.primerparcial.Modelo.Config;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.SerializableDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Descripción: [Agrega aquí la descripción de la clase].
 * </p>
 *
 * @author diego
 * @version 1.0
 */
public class ControlPrincipal {

    private ControlVentana cVentana;
    private SerializableDAO serializableDAO;

    public ControlPrincipal() {
        this.serializableDAO = new SerializableDAO();
        this.cVentana = new ControlVentana(this);
        
        // Verificar si el archivo serializado exite para saber si estamos en la
        // segunda ejecucion del programa
        File archivoSerializado = new File(Config.RUTA_PREDETERMINADA_ARCHIVO_SERIALIZADO_ANIMALES);
        // Si exite pedirle al usuario que eliga un archivo serializado        
        // Si no configurar la conexion con el archivo en la ruta predeterminada
        if (archivoSerializado.exists()) {            
            // TODO Cambiar para Llamar a metodo de menu de seleccion de archivos
            cVentana.obtenerArchivoSerializado(Config.RUTA_CARPETA_PRECARGA);
        } else {
            serializableDAO.setArchivo(archivoSerializado);            
        }
    }    

    public void procesarArchivoSerializado(File archivo) {
        serializableDAO.setArchivo(archivo);
    }
    
    /**
     * Metodo llamando antes de salir de la aplicacion
     */
    public void salir() {
        // TODO Llamar a la base de datos para obtener datos actualizados
        // TODO Guardar lo traido desde la base datos en el archivo aletorio
        // y serializado
    }
}
