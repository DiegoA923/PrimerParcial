package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import udistrital.avanzada.primerparcial.Modelo.MascotaVO;

/**
 * Interfaz para los dao que necesetiten persistir una mascota
 *
 * @author Mauricio
 * @since 2025-10-14
 */
public interface IInsertarMascota {

    /**
     * Metodo para insertar una mascota
     *
     * @param mascota MascotaVO a insertar
     */
    void insertarMascota(MascotaVO mascota);
}
