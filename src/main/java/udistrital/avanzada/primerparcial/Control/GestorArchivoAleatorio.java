package udistrital.avanzada.primerparcial.Control;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.AleatorioDAO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.LongitudesMascota;

/**
 * Gestor de Archivo aleatorio
 * 
 * Se encarga de la escritura y lectura de mascotas en un archivo aleatorio
 * usa la calse AleatorioDAO para sus operaciones según corresponda
 * 
 * @author Mauricio
 * @since 2025-10-12
 */
public class GestorArchivoAleatorio {

    private AleatorioDAO aleatorioDAO;

    public GestorArchivoAleatorio() {
        aleatorioDAO = new AleatorioDAO();
    }
    
    /**
     * Metodo para obtener una lista de string con todos los registros
     * 
     * @return lista string con los registros
     */
    public List<String> leerMascotas() {
        List<String> mascotas = new ArrayList<>();
        RandomAccessFile raf = aleatorioDAO.listaDeMascotas();
        try {
            //Nos posicionamos en el primer registro
            raf.seek(0);
            //Leemos cada uno y lo agregamos a la lista
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                String nombre = leerCampo(raf, LongitudesMascota.NOMBRE);
                String apodo = leerCampo(raf, LongitudesMascota.APODO);
                String clasificacion = leerCampo(raf, LongitudesMascota.CLASIFICACION);
                String familia = leerCampo(raf, LongitudesMascota.FAMILIA);
                String genero = leerCampo(raf, LongitudesMascota.GENERO);
                String especie = leerCampo(raf, LongitudesMascota.ESPECIE);
                String tipoAlimento = leerCampo(raf, LongitudesMascota.TIPO_ALIMENTO);                
                mascotas.add(
                        id+", "+
                        nombre+", "+
                        apodo+", "+
                        clasificacion+", "+
                        familia+", "+
                        genero+", "+
                        especie+", "+
                        tipoAlimento
                );
            }
        } catch (Exception e) {
            
        } finally {
            // Llamos a descontectar 
            aleatorioDAO.desconectar();
        }
        return mascotas;
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
    /**
     * Metodo para cambiar la ruta del archivo aleatorio
     * 
     * @param ruta 
     */
    public void setRutaArchivo(String ruta) {
        //Se delega a la clase dao
        aleatorioDAO.setArchivoAleatorio(ruta);
    }
    
    /**
     * metodo para insertar una mascota en el archivo aleatorio al final
     * del mismo
     * 
     * @param mascota 
     */
    public void insertarMascota(MascotaVO mascota) {
        // Delegamos a la clase dao
        aleatorioDAO.insertarMascota(mascota);       
    }
    
    /**
     * Metodo para insertar mascotas en el archivo aleatorio
     * 
     * @param mascotas 
     */
    public void insertarMascotas(ArrayList<MascotaVO> mascotas) {
        for (MascotaVO mascota : mascotas) {
            insertarMascota(mascota);
        }
    }    
}
