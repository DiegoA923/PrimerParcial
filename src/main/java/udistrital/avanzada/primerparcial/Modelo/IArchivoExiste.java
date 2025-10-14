package udistrital.avanzada.primerparcial.Modelo;

/**
 * Interfaz con contrato para clases que necesiten verificar que un archivo
 * exite
 * 
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IArchivoExiste {
    /**
     * Metodo para saber si archivo exite
     * 
     * @return true si existe, false si no
     */
    boolean archivoExiste();
}
