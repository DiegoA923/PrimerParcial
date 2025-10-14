package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.RandomAccessFile;

/**
 * Interfaz para las clases que realicen la conexion a archivos de acceso
 * aleatorio
 *
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IConexionAleatorio extends IConectar, IDesconectar {

    /**
     * Meotodo para obtener fuente
     *
     * @return RandomAccessFile con el cual trabaja la conexion
     */
    RandomAccessFile getArchivo();

    /**
     * Metodo para modificar la ruta del archivo fuente
     *
     * @param ruta ubicacion del archivo fuente
     */
    void setRuta(String ruta);
}
