package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionAleatorio;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;

/**
 * Clase DAO
 * 
 * Gestiona las operaciones de crear y listar las mascotas en un archivo
 * aleatorio
 *
 * @author Mauricio
 * @since 12/10/2025
 */
public class AleatorioDAO {

    private ConexionAleatorio conexion;
    
    /**
     * Constructor
     * 
     * @param ruta del archivo aleatorio
     */
    public AleatorioDAO(String ruta) {
        this.conexion = ConexionAleatorio.getInstancia();
        // Asignar ruta del archivo a la conexion
        this.conexion.setRuta(ruta);
    }

    /**
     * Metodo para escribir una mascota
     *
     * @param mascota
     */
    public void escribirMascota(MascotaVO mascota) {
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
     * Metodo para obtener la lista de mascotas contenida en el archivo
     *
     * @return lista de MascotaVO
     */
    public ArrayList<MascotaVO> listaDeMascotas() {
        ArrayList<MascotaVO> mascotas = new ArrayList<>();
        try {
            conexion.conectar();
            RandomAccessFile raf = conexion.getArchivo();
            raf.seek(0);
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                String nombre = leerCampo(raf, LongitudesMascota.NOMBRE);
                String apodo = leerCampo(raf, LongitudesMascota.APODO);
                String clasificacion = leerCampo(raf, LongitudesMascota.CLASIFICACION);
                String familia = leerCampo(raf, LongitudesMascota.FAMILIA);
                String genero = leerCampo(raf, LongitudesMascota.GENERO);
                String especie = leerCampo(raf, LongitudesMascota.ESPECIE);
                String tipoAlimento = leerCampo(raf, LongitudesMascota.TIPO_ALIMENTO);
                MascotaVO mascota = new MascotaVO(
                        id,
                        apodo.trim(),
                        nombre.trim(),
                        Clasificacion.valueOf(clasificacion.trim().toUpperCase()),
                        familia.trim(),
                        genero.trim(),
                        especie.trim(),
                        TipoAlimento.valueOf(tipoAlimento.trim().toUpperCase())
                );
                mascotas.add(mascota);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error al leer: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }
        return mascotas;
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
     * Metodo auxiliar para leer un campo string en especifico dando su longitud
     *
     * @param raf RandomAccessFile de donde se lee
     * @param longitud cantidad caracteres que conforman el campo
     * @return
     */
    private String leerCampo(RandomAccessFile raf, int longitud) {
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < longitud; i++) {
                sb.append(raf.readChar());
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error al leer: " + ex.getMessage(), ex);
        }
        return sb.toString();
    }
}
