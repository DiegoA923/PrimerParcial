package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.IArchivoModificar;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.IArchivoExiste;

/**
 *
 * @author Mauricio
 * @since 2025-10-2025
 */
public interface ISerializableDAO extends IListaMascotas, IArchivoExiste, IArchivoModificar {
    void guardar(ArrayList<MascotaVO> mascotas);
}
