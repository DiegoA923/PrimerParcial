package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import udistrital.avanzada.primerparcial.Modelo.IArchivoExiste;
import udistrital.avanzada.primerparcial.Modelo.IArchivoModificar;

/**
 *
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IConexionSerializable extends IConectar, IDesconectar, IConexionSobrescribible, IArchivoExiste, IArchivoModificar {
    ObjectInputStream getEntrada();
    ObjectOutputStream getSalida();
}
