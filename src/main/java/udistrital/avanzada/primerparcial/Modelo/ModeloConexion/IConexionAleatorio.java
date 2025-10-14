package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.RandomAccessFile;

/**
 *
 * @author mauri
 */
public interface IConexionAleatorio extends IConectar, IDesconectar {
    RandomAccessFile getArchivo();
    void setRuta(String ruta);
}
