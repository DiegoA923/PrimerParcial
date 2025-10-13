package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionSerializable;

/**
 * Clase DAO para serializar y deserializar lista de mascotas
 *
 * @author Mauricio
 * @10/10/2025
 */
public class SerializableDAO {

    private ConexionSerializable conexionSerializable;

    public SerializableDAO() {
        this.conexionSerializable = ConexionSerializable.getInstancia();
    }

    /**
     * Metodo para deserializar una lista de animales
     *
     * @return un ArrayList de MascotaVO
     */
    public ArrayList<MascotaVO> listaDeMascotas() {
        ArrayList<MascotaVO> mascotas = null;
        ObjectInputStream entrada = null;
        conexionSerializable.conectar();
        entrada = conexionSerializable.getEntrada();
        try {
            mascotas = (ArrayList<MascotaVO>) entrada.readObject();
            //Propagacion de errores para que los maneje capa control
        } catch (IOException ex) {
            throw new RuntimeException("Error en deserializacion: " + ex.getMessage(), ex);
        } catch (NullPointerException ex) {
            throw new RuntimeException("Entrada es nula: " + ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Clase no encontrada en archivo: " + ex.getMessage(), ex);
        } finally {
            conexionSerializable.desconectar();
        }
        return mascotas;
    }

    /**
     * Metodo para serializar una lista de mascotas
     *
     * @param mascotas lista de mascotas a serializar
     */
    public void guardar(ArrayList<MascotaVO> mascotas) {
        ObjectOutputStream salida = null;
        conexionSerializable.conectarSobrescribible();
        salida = conexionSerializable.getSalida();
        try {
            salida.writeObject(mascotas);
        } catch (NullPointerException ex) {
            //Propagacion para que lo maneje capa control
            throw new RuntimeException("Salida es nula : " + ex.getMessage(), ex);
        } catch (IOException ex) {
            //Propagacion para que lo maneje capa control
            throw new RuntimeException("No se pudo serializar : " + ex.getMessage(), ex);
        } finally {
            conexionSerializable.desconectar();
        }
    }

    /**
     * Metodo para configurar el archivo fuente que usara la conexion
     *
     * @param archivo archivo con extension .bin
     */
    public void setArchivo(File archivo) {
        this.conexionSerializable.setArchivo(archivo);
    }
}
