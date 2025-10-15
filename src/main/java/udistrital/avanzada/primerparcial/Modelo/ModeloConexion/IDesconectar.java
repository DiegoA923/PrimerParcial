package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

/**
 * Define contrato para las clases que necesiten cerrar una conexion
 *
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IDesconectar {

    /**
     * Metodo para cerrar una conexion a un recurso
     */
    void desconectar();
}
