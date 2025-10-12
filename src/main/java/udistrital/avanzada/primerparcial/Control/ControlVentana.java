package udistrital.avanzada.primerparcial.Control;

import java.io.File;
import javax.swing.JFileChooser;
import udistrital.avanzada.primerparcial.Vista.VentanaPrincipal;

/**
 * Clase ControlVentana.
 * <p>
 * Descripción: [Agrega aquí la descripción de la clase].
 * </p>
 *
 * @author diego
 * @version 1.0
 */
public class ControlVentana {

    private VentanaPrincipal ventana;
    private ControlPrincipal logica;

    public ControlVentana(ControlPrincipal logica) {
        this.ventana = new VentanaPrincipal();
        this.logica = logica;    
    }
    
    /**
     * Metodo para obternerArchivoSerializado
     * @param ruta donde se abre la carpeta
     */
    public void obtenerArchivoSerializado(String ruta) {
        JFileChooser chooser = ventana.getFileChoser(
                "Eliga el archivo serializado",
                "bin",
                JFileChooser.FILES_ONLY,
                ruta
        );
        int seleccion = chooser.showOpenDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = chooser.getSelectedFile();
            logica.procesarArchivoSerializado(archivoSeleccionado);
        }
    }
}
