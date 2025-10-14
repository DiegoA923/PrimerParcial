package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.io.IOException;
import java.io.RandomAccessFile;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionAleatorio;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IConexionAleatorio;

/**
 * Clase DAO
 * 
 * Gestiona las operaciones de crear y listar las mascotas en un archivo
 * aleatorio
 *
 * @author Mauricio
 * @since 2025-10-12
 */
public class AleatorioDAO implements IAleatorioDAO {

    private IConexionAleatorio conexion;

    
    public AleatorioDAO() {
        //Obtenemos la instancia de la conexion
        this.conexion = ConexionAleatorio.getInstancia();
    }

    /**
     * Metodo para escribir una mascota
     *
     * @param mascota MascotaVO
     */
    @Override
    public void insertarMascota(MascotaVO mascota) {
        try {
            conexion.conectar();
            RandomAccessFile raf = conexion.getArchivo();
            //Ir al final del archivo
            raf.seek(raf.length());
            raf.writeInt(mascota.getId());
            escribirCampo(raf, mascota.getNombreComun(), LongitudesMascota.NOMBRE);
            escribirCampo(raf, mascota.getApodo(), LongitudesMascota.APODO);
            escribirCampo(raf, mascota.getClasificacion().name(), LongitudesMascota.CLASIFICACION);
            escribirCampo(raf, mascota.getFamilia(), LongitudesMascota.FAMILIA);
            escribirCampo(raf, mascota.getGenero(), LongitudesMascota.GENERO);
            escribirCampo(raf, mascota.getEspecie(), LongitudesMascota.ESPECIE);
            escribirCampo(raf, mascota.getTipoAlimento().name(), LongitudesMascota.TIPO_ALIMENTO);
        } catch (IOException ex) {
            throw new RuntimeException("Error al escribir: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Metodo para obtener la lista de mascotas
     *
     * @return RandomAccessFile con la lista de mascotas
     */
    @Override
    public RandomAccessFile listaDeMascotas() {
        RandomAccessFile raf = null;
        try {
            conexion.conectar();
            raf = conexion.getArchivo();
        } catch (RuntimeException ex) {
            
        }
        return raf;
    }

    /**
     * Metodo auxiliar para escribir un campo String en el archivo aleatorio
     *
     * @param raf RandomAccessFile de donde se escribe
     * @param texto Texto a escribir
     * @param longitud Limite de caracteres permitidos
     */    
    private void escribirCampo(RandomAccessFile raf, String texto, int longitud) {
        // Limitamos la cantidad de caracteres para escribir correctamente
        String valor = String.format("%-" + longitud + "s", texto);
        try {
            raf.writeChars(valor);
        } catch (IOException ex) {
            throw new RuntimeException("Error al escribir: " + ex.getMessage(), ex);
        }

    }    
    
    /**
     * Metodo para asignar la ruta del archivo aleatorio a la conexion
     * 
     * @param ruta 
     */
    @Override
    public void setArchivoAleatorio(String ruta) {
        // Conexion asigna la ruta
        this.conexion.setRuta(ruta);
    }
    
    
    // Metodo para cerrar conexion de archivo
    @Override
    public void desconectar() {
        // lo maneja la conexion directamente
        conexion.desconectar();
    }
}
