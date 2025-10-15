package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.sql.Connection;

/**
 * Interfaz IConexion.
 * <p>
 * Define el contrato para las clases encargadas de gestionar la conexión con la base de datos.
 * Proporciona los métodos necesarios para establecer y cerrar una conexión, 
 * garantizando un acceso centralizado y controlado al recurso de base de datos.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-10
 */
public interface IConexion {

    /**
     * Establece y devuelve una conexión activa con la base de datos.
     *
     * @return un objeto {@link Connection} activo que representa la conexión a la base de datos
     */
    Connection getConexion();

    /**
     * Cierra la conexión actual con la base de datos.
     * <p>
     * Debe ser invocado una vez se hayan completado las operaciones sobre la base de datos,
     * con el fin de liberar recursos y evitar fugas de conexión.
     * </p>
     */
    void desconectar();
}


