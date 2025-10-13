package udistrital.avanzada.primerparcial.Control;

/**
 * Clase Launcher.
 * <p>
 * Punto de entrada del programa. Su única responsabilidad es
 * instanciar la clase {@link ControlPrincipal}.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-11
 */
public class Launcher {

    /**
     * Método principal del programa.
     * <p>
     * Crea una instancia de {@link ControlPrincipal} para inicializar
     * la aplicación y manejar el flujo general.
     * </p>
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        new ControlPrincipal();
    }
}
