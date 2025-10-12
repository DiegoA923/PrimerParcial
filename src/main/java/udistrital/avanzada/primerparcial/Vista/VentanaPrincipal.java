package udistrital.avanzada.primerparcial.Vista;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mauricio
 * @since 09/10/2025
 */
public class VentanaPrincipal {

    /**
     * Metodo para mostrar la ventana de eleccion de archivo
     *
     * @param descripcion Mensaje de lo que se requiere
     * @param extension Filtro de archivo por extension que se aplicara
     * @param modoSeleccion Que se puede seleccionar
     * @return JFileChooser
     */
    public JFileChooser getFileChoser(String descripcion, String extension, int modoSeleccion, String rutaPredeterminada) {
        JFileChooser fileChooser = new JFileChooser();
        //Carpeta donde se guardan archivos de precarga y poscarga
        File carpetaInicial = new File(rutaPredeterminada);
        // Que se abra en la carpeta data predeterminadamente
        fileChooser.setCurrentDirectory(carpetaInicial);
        // Filtro con el mensaje que aparecera y la extension del mismo
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(descripcion, extension);
        fileChooser.setFileFilter(filtro);
        fileChooser.setFileSelectionMode(modoSeleccion);
        return fileChooser;
    }

}
