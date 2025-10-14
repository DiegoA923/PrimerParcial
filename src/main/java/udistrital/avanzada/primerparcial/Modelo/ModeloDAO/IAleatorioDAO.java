package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.io.RandomAccessFile;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IDesconectar;

/**
 * Interfaz que define el contratos para clases que accedan y manipulen
 * entidades MascotaVO que se encuentren en un archivo aleatorio
 *
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IAleatorioDAO extends IInsertarMascota, IDesconectar {

    /**
     * Metodo para obtener archivo origen
     *
     * @return RandomAccessFile utilizado para las operaciones
     */
    RandomAccessFile listaDeMascotas();

    /**
     * Metodo para cambiar la ruta del archivo fuente de tipo aleatorio
     *
     * @param ruta
     */
    void setArchivoAleatorio(String ruta);
}
