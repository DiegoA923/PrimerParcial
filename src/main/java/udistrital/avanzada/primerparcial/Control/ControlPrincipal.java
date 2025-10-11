package udistrital.avanzada.primerparcial.Control;

import java.io.File;
import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Control.ControlDAO.ControlMascota;
import udistrital.avanzada.primerparcial.Modelo.Config;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
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
    private ControlMascota cMascota;

    public ControlPrincipal() {
        this.serializableDAO = new SerializableDAO();
        this.cMascota = new ControlMascota();
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
     * Metodo para cargar las mascotas desde archivo serializado y subirlos a la
     * base de datos
     */
    public void insertarDatosDesdeSerializador() {
        try {
            // Obtenemos mascotas a guaradar
            ArrayList<MascotaVO> mascotas = serializableDAO.listaDeMascotas();
            // Las guardamos en BD
            for (MascotaVO mascota : mascotas) {
                cMascota.insertarMascota(mascota);
            }
        } catch (RuntimeException e) {
            
        }        
    }
    
    /**
     * Metodo llamado antes de salir de la aplicacion
     */
    public void salir() {
        // Macotas a guardar actualizada desde la base de datos
        ArrayList<MascotaVO> mascotas = null;
        try {
            mascotas = cMascota.obtenerMascotas();
        } catch (Exception e) {
        }
        // No hay mascotas no se guarda nada
        if (mascotas == null) {
            return;
        }
        // Guardar mascotas en archivo serializado
        try {
            serializableDAO.guardar(mascotas);
        } catch (RuntimeException e) {
            
        }
        // TODO Guardar lo traido desde la base datos en el archivo aletorio
    }
}
