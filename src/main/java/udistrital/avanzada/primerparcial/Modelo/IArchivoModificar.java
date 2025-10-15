package udistrital.avanzada.primerparcial.Modelo;

import java.io.File;

/**
 * Interfaz con contrato para clases que necesiten modificar un archivo
 * que tengan en su estrutura
 * 
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IArchivoModificar {
    void setArchivo(File archivo);
}
