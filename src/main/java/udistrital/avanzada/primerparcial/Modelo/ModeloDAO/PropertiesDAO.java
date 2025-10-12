/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.util.Properties;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IConexionProperties;

/**
 * Clase PropertiesDAO.
 * <p>
 * DAO que gestiona el acceso al archivo de propiedades.
 * Aplica el patrón DAO para encapsular el acceso a la fuente de datos (archivo properties).
 * </p>
 * <p>
 * Responsabilidad única: Obtener el objeto Properties desde la conexión.
 * NO realiza procesamiento de datos ni ciclos. Esa responsabilidad es del Control.
 * </p>
 *
 * @author sebas
 * @version 2.0
 * @since 2025-10-11
 */
public class PropertiesDAO {

    /**
     * Interfaz de conexión al archivo properties, inyectada desde el exterior.
     */
    private final IConexionProperties conexionProperties;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param conexionProperties objeto que implementa {@link IConexionProperties}
     *                          y gestiona el acceso al archivo
     */
    public PropertiesDAO(IConexionProperties conexionProperties) {
        this.conexionProperties = conexionProperties;
    }

    /**
     * Obtiene el objeto Properties desde el archivo.
     * <p>
     * Este método únicamente delega la lectura del archivo a la conexión
     * y retorna el objeto Properties sin procesarlo.
     * </p>
     * <p>
     * La responsabilidad de iterar, parsear y validar los datos
     * corresponde a la capa de Control.
     * </p>
     *
     * @return objeto {@link Properties} con los datos del archivo
     * @throws RuntimeException si ocurre un error al leer el archivo
     */
    public Properties obtenerProperties() {
        try {
            return conexionProperties.cargarProperties();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener properties: " + e.getMessage(), e);
        }
    }
}