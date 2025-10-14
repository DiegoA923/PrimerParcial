package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

/**
 * Clase auxiliar para las operaciones de guardado de mascota en archivos
 * aletorios que requieren tamaños fijos para su operacion
 * 
 * @author Mauricio
 * @since 12/10/2025
 */

public class LongitudesMascota {
    public static final int ID = 4;
    public static final int NOMBRE = 25;
    public static final int APODO = 25;
    public static final int CLASIFICACION = 25;
    public static final int FAMILIA = 15;
    public static final int GENERO = 10;
    public static final int ESPECIE = 15;
    public static final int TIPO_ALIMENTO = 15;
    
    public static final int TAM_REGISTRO =
        (NOMBRE + APODO + CLASIFICACION + FAMILIA + GENERO + ESPECIE + TIPO_ALIMENTO) * 2 + ID;
}
