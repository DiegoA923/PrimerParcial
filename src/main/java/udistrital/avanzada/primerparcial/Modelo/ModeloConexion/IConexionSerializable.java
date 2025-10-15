package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import udistrital.avanzada.primerparcial.Modelo.IArchivoExiste;
import udistrital.avanzada.primerparcial.Modelo.IArchivoModificar;

/**
 *
 * Interfaz para las clases que realicen la conexion a archivos seriliazados
 *
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IConexionSerializable extends IConectar, IDesconectar, IConexionSobrescribible, IArchivoExiste, IArchivoModificar {

    /**
     * Metodo para obtener el objeto para la lectura
     *
     * @return el objeto para leer
     */
    ObjectInputStream getEntrada();

    /**
     * Metodo para obtener el objeto de escritura
     *
     * @return el objeto para escribir
     */
    ObjectOutputStream getSalida();
}
