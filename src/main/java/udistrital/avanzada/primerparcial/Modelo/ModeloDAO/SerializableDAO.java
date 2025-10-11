package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionSerializable;

/**
 * Clase DAO para serializar y deserializar lista de animales
 *
 * @author Mauricio
 * @10/10/2025
 */
public class SerializableDAO {
    
    private ConexionSerializable conexionSerializable;
    
    public SerializableDAO() {       
        this.conexionSerializable = new ConexionSerializable();        
    }
    
    /**
     * Metodo para deserializar una lista de animales
     * 
     * @return un ArrayList de animales
     */
    public ArrayList<MascotaVO> cargar() {
        ArrayList<MascotaVO> mascotas = null;
        ObjectInputStream entrada = null;
        try {
            conexionSerializable.conectar();
            entrada = conexionSerializable.getEntrada();
            mascotas = (ArrayList<MascotaVO>) entrada.readObject();
        } catch (IOException | NullPointerException | ClassNotFoundException e) {            
        }
        finally {
            conexionSerializable.desconectar();
        }
        return mascotas;
    }
    
    /**
     * Metodo para serializar una lista de animales
     * 
     * @param mascotas lista de mascotas a serializar
     */
    public void guardar(ArrayList<MascotaVO> mascotas){
        ObjectOutputStream salida = null;
        try {
            conexionSerializable.conectarSobrescribible();
            salida = conexionSerializable.getSalida();
            salida.writeObject(mascotas);
            
        } catch (IOException | NullPointerException e) {

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
