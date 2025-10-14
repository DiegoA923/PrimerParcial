package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.util.Properties;

/**
 * Interfaz que define el contrato con los metodos que deben ser implementados 
 * para interactuar con la fuente de datos que es un objeto {@link Properties}.
 *
 *
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IPropertiesDAO {

    /**
     * Metodo para obtener el objeto {@link Properties} desde el archivo.
     *
     * @return objeto {@link Properties} con los datos del archivo
     */
    Properties obtenerProperties();
}
