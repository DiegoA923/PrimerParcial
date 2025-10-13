package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase de conexion para operaciones de serializacion Su funcion es
 * proporcionar los canalesde entrada y salida correspondientes al archivo que
 * se le pase
 *
 * @author Mauricio
 * @since 10/10/2025
 */
//TODO que la clase implemente la interfaz IConexion
public class ConexionSerializable implements IConexionSobrescribible {

    //Canal de salida para escribir en el archivo de serializacion
    private FileOutputStream fileOut;
    private ObjectOutputStream salida;

    //Canal de entrada para para leer archivo de serializacion
    private FileInputStream fileIn;
    private ObjectInputStream entrada;

    //Archivo fuente
    private File archivo;
    
    private static final ConexionSerializable instancia = new ConexionSerializable();

    /**
     * Contructor ConexionSerializable vacio
     *
     */
    private ConexionSerializable() {
        this.fileIn = null;
        this.fileOut = null;
        this.entrada = null;
        this.salida = null;
        this.archivo = null;
    }
    
    public static ConexionSerializable getInstancia() {
        return instancia;
    }    

    /**
     * Metodo para conectar a los canales de salida y entrada
     *
     */
    public void conectar() {
        try {
            //Para escribir            
            fileOut = new FileOutputStream(archivo, true);
            // Se usa la clase auxiliar para que no se sobreescriba la cabecera
            // del archivo y se corrompa
            // Asi entonces se pueden agregar más objetos serializados si se requiere
            salida = new AppendableObjectOutputStream(fileOut);
            //Para leer
            fileIn = new FileInputStream(archivo);
            entrada = new ObjectInputStream(fileIn);
        } catch (FileNotFoundException ex) {
            //Propagacion para que lo maneje capa control
            throw new RuntimeException("Archivo no encontrado: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            //Propagacion para que lo maneje capa control
            throw new RuntimeException("Entrada/salida no puenden configurarse: " + ex.getMessage(), ex);
        }
    }

    /**
     * Metodo para cerrar la conexion con los canales
     *
     */
    public void desconectar() {
        //Intetamos cerrar cada Stream
        try {
            entrada.close();
        } catch (NullPointerException nue) {
        } catch (IOException ioe) {
        }
        try {
            salida.close();
        } catch (NullPointerException nue) {
        } catch (IOException ioe) {
        }
        try {
            fileIn.close();
        } catch (NullPointerException nue) {
        } catch (IOException ioe) {
        }
        try {
            fileOut.close();
        } catch (NullPointerException nue) {
        } catch (IOException ioe) {
        }
    }

    public ObjectOutputStream getSalida() {
        return salida;
    }

    public ObjectInputStream getEntrada() {
        return entrada;
    }

    /**
     * Metodo para asignar el archivo de origen con el cual queremos trabajar
     *
     * @param archivo archivo con extension .bin
     */
    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }

    /**
     * Metodo para conectar de forma que se sobrescriba el archivo serializado
     *
     */
    @Override
    public void conectarSobrescribible() {
        try {
            //Para escribir sobreescribiendo el archivo de salida          
            fileOut = new FileOutputStream(archivo, false);
            salida = new ObjectOutputStream(fileOut);
            //Para leer
            fileIn = new FileInputStream(archivo);
            entrada = new ObjectInputStream(fileIn);
        } catch (FileNotFoundException ex) {
            //Propagacion para que lo maneje capa control
            throw new RuntimeException("Archivo no encontrado: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            //Propagacion para que lo maneje capa control
            throw new RuntimeException("Entrada/salida no puenden configurarse: " + ex.getMessage(), ex);
        }
    }
    
    /**
     * Metodo para saber si archivo serializado existe
     * 
     * @return true si existe, false si no
     */
    public boolean archivoExiste() {
        try {
            return archivo.exists() && archivo.isFile();
        } catch (Exception e) {
            // Si hay excepcion es que no exite
            return false;
        }        
    }
}
