package udistrital.avanzada.primerparcial.Control;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import udistrital.avanzada.primerparcial.Control.ControlDAO.ControlMascota;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.Config;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionProperties;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IConexionProperties;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.PropertiesDAO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.SerializableDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Controlador principal de la aplicación que coordina la inicialización
 * y el flujo general del sistema de gestión de mascotas exóticas.
 * </p>
 *
 * @author Diego
 * @version 1.0
 */
public class ControlPrincipal {
    
    private ControlVentana cVentana;
    private SerializableDAO serializableDAO;
    private ControlMascota cMascota;
    private PropertiesDAO propertiesDAO;
    
    /**
     * Constructor por defecto.
     */
    public ControlPrincipal() {
        this.serializableDAO = new SerializableDAO();
        this.cMascota = new ControlMascota();
        
        // Crear conexión y DAO para properties
        IConexionProperties conexionProps = new ConexionProperties();
        this.propertiesDAO = new PropertiesDAO(conexionProps);
        
        this.cVentana = new ControlVentana(this);
        
        // Verificar si el archivo serializado existe para saber si estamos en la
        // segunda ejecución del programa
        File archivoSerializado = new File(Config.RUTA_PREDETERMINADA_ARCHIVO_SERIALIZADO_ANIMALES);
        
        if (archivoSerializado.exists()) {            
            // Segunda ejecución: cargar desde serializado
            cVentana.obtenerArchivoSerializado(Config.RUTA_CARPETA_PRECARGA);
        } else {
            // Primera ejecución: cargar desde properties
            serializableDAO.setArchivo(archivoSerializado);
            cargarDatosDesdeProperties();
        }
    }
    
    /**
     * Carga los datos iniciales desde el archivo properties.
     * <p>
     * Lee el archivo properties, parsea cada línea y las inserta en la base de datos.
     * </p>
     */
    private void cargarDatosDesdeProperties() {
        try {
            // Obtener el objeto Properties desde el DAO
            Properties properties = propertiesDAO.obtenerProperties();
            
            if (properties.isEmpty()) {
                return;
            }
            
            // Iterar sobre cada propiedad y procesarla
            for (String key : properties.stringPropertyNames()) {
                String valor = properties.getProperty(key);
                
                try {
                    // Parsear la línea y crear el objeto MascotaVO
                    MascotaVO mascota = parsearMascota(valor);
                    
                    // Insertar en la base de datos
                    cMascota.insertarMascota(mascota);
                    
                } catch (IllegalArgumentException e) {
                    // Error al parsear, continuar con la siguiente
                } catch (RuntimeException e) {
                    // Error al insertar, continuar con la siguiente
                }
            }
            
        } catch (RuntimeException e) {
            // Error al cargar properties
        }
    }
    
    /**
     * Parsea una línea del archivo properties y crea un objeto MascotaVO.
     * <p>
     * Formato esperado: "NombreComun,Apodo,Clasificacion,Familia,Genero,Especie,TipoAlimento"
     * Ejemplo: "Burro,Pepe,MAMIFERO,Equidae,Equus,E. africanus,FORRAJE"
     * </p>
     *
     * @param linea cadena con los datos separados por comas
     * @return objeto {@link MascotaVO} con los datos parseados
     * @throws IllegalArgumentException si el formato es inválido
     */
    private MascotaVO parsearMascota(String linea) throws IllegalArgumentException {
        if (linea == null || linea.trim().isEmpty()) {
            throw new IllegalArgumentException("La línea está vacía o es nula");
        }
        
        String[] datos = linea.split(",");
        
        if (datos.length < 7) {
            throw new IllegalArgumentException("Formato inválido: se requieren 7 campos");
        }
        
        MascotaVO mascota = new MascotaVO();
        
        // Parsear cada campo (trim para quitar espacios)
        mascota.setNombreComun(datos[0].trim());
        mascota.setApodo(datos[1].trim());
        mascota.setClasificacion(Clasificacion.valueOf(datos[2].trim().toUpperCase()));
        mascota.setFamilia(datos[3].trim());
        mascota.setGenero(datos[4].trim());
        mascota.setEspecie(datos[5].trim());
        mascota.setTipoAlimento(TipoAlimento.valueOf(datos[6].trim().toUpperCase()));
        
        return mascota;
    }
    
    /**
     * Obtiene el controlador de mascotas.
     *
     * @return controlador de mascotas
     */
    public ControlMascota getControlMascota() {
        return cMascota;
    }
    
    /**
     * Procesa el archivo serializado seleccionado por el usuario.
     *
     * @param archivo archivo serializado a procesar
     */
    public void procesarArchivoSerializado(File archivo) {
        serializableDAO.setArchivo(archivo);
    }
    
    /**
     * Método para cargar las mascotas desde archivo serializado y subirlas a la
     * base de datos.
     */
    public void insertarDatosDesdeSerializador() {
        try {
            // Obtenemos mascotas a guardar
            ArrayList<MascotaVO> mascotas = serializableDAO.listaDeMascotas();
            
            if (mascotas != null && !mascotas.isEmpty()) {
                // Las guardamos en BD
                for (MascotaVO mascota : mascotas) {
                    cMascota.insertarMascota(mascota);
                }
            }
        } catch (RuntimeException e) {
            // Error al cargar
        }        
    }
    
    /**
     * Método llamado antes de salir de la aplicación.
     */
    public void salir() {
        // Mascotas a guardar actualizada desde la base de datos
        ArrayList<MascotaVO> mascotas = null;
        try {
            mascotas = cMascota.obtenerMascotas();
        } catch (Exception e) {
            // Error al obtener mascotas
        }
        
        // No hay mascotas, no se guarda nada
        if (mascotas == null || mascotas.isEmpty()) {
            return;
        }
        
        // Guardar mascotas en archivo serializado
        try {
            serializableDAO.guardar(mascotas);
        } catch (RuntimeException e) {
            // Error al guardar
        }
        
        // TODO Guardar lo traído desde la base datos en el archivo aleatorio
    }
}