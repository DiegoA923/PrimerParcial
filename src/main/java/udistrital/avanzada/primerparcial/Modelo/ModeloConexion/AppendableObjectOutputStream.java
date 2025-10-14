package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.io.*;

/**
 * Subclase de {@link ObjectOutputStream} que omite la escritura de la cabecera de serialización.
 * 
 * Se utiliza cuando se desea agregar (append) objetos serializados a un archivo
 * que ya contiene una cabecera válida, evitando así que se escriba una cabecera duplicada,
 * lo cual puede corromper el archivo y hacer que no se pueda leer correctamente.
 * 
 * @author Mauricio
 * @since 2025-10-11
 */

public class AppendableObjectOutputStream extends ObjectOutputStream {

    public AppendableObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }
    
    /**
     * Metodo que sobreescrito para que no se escriba un nuevo 
     * encabezado en el archivo evitando su corrupcion
     * 
     * @throws IOException 
     */
    @Override
    protected void writeStreamHeader() throws IOException {
        // No escribe un nuevo encabezado (previene corrupción del archivo)
        reset();
    }
}
