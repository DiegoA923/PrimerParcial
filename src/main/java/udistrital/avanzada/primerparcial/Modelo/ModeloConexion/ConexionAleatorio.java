package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase que gestiona la conexión a un archivo de acceso aleatorio
 *
 * @author Mauricio
 * @since 2025-10-11
 */
public class ConexionAleatorio {

    //Instanciar sin condicionales
    private static final ConexionAleatorio instancia = new ConexionAleatorio();
    private RandomAccessFile archivo;
    private String ruta;

    //Contructor privado para singleton
    private ConexionAleatorio() {
    }

    /**
     * Metodo para obtener la unica instancia en la aplicacion
     *
     * @return ConexionAletorio
     */
    public static ConexionAleatorio getInstancia() {
        return instancia;
    }

    
    // Conectar al archivo aleatorio
     
    public void conectar() {
        try {
            archivo = new RandomAccessFile(ruta, "rw");
        //Pasar excepciones a capa control
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Archivo no se puede abrir: " + ex.getMessage(), ex);
        }
    }

    
    // Desconectar del archivo aleatorio
     
    public void desconectar() {
        try {
            archivo.close();
            archivo = null;
        //Pasar excepciones a capa control
        } catch (IOException ex) {
            throw new RuntimeException("Archivo no se puede cerrar: " + ex.getMessage(), ex);
        } catch (NullPointerException ex) {
            throw new RuntimeException("Archivo es nulo: " + ex.getMessage(), ex);
        }
    }

    /**
     * Metodo para asiganar la ruta del archivo aleatorio con el cual se va a
     * trabajar
     *
     * @param ruta
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    
    // Obtener archivo de trabajo despues de llamar metodo conectar
    
    public RandomAccessFile getArchivo() {
        return archivo;
    }
}
