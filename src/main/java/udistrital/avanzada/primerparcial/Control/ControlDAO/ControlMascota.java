package udistrital.avanzada.primerparcial.Control.ControlDAO;

import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionBD;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.MascotaDAO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;

/**
 * Clase ControlMascota.
 * <p>
 * Descripción: [Agrega aquí la descripción de la clase].
 * </p>
 *
 * @author diego
 * @version 1.0
 */
public class ControlMascota {

    private MascotaDAO mascotaDAO;

    /**
     * Constructor
     */
    public ControlMascota() {
        //Solo la clase DAO deberia instanciar la conexion?
        //Inicializar el atributo mascotaDAO
        this.mascotaDAO = new MascotaDAO(ConexionBD.getInstancia());
    }

    /**
     * Metodo para obtener la lista de mascotas
     *
     * @return lista de mascotas
     */
    public ArrayList<MascotaVO> obtenerMascotas() {
        ArrayList<MascotaVO> mascotas = mascotaDAO.listaDeMascotas();
        return mascotas;
    }

    /**
     * Metodo para isertar una mascota en la base de datos con los parametros de
     * la mascota
     *
     * @param id
     * @param nombreComun
     * @param clasificacion
     * @param familia
     * @param genero
     * @param especie
     * @param tipoAlimento
     * @return true si se inserto correctamente si no false
     */
    public boolean insertarMascota(int id, String nombreComun, Clasificacion clasificacion, String familia, String genero, String especie, TipoAlimento tipoAlimento) {
        MascotaVO mascota = new MascotaVO(id, genero, nombreComun, clasificacion, familia, genero, especie, tipoAlimento);        
        boolean res = mascotaDAO.insertarMascota(mascota);        
        return res;
    }

    /**
     * Insertar una mascota en la base de datos con el objeto MascotaVO
     * 
     * @param mascota mascota a insertar
     * @return true si se inserto correctamente si no false
     */
    public boolean insertarMascota(MascotaVO mascota) {        
        boolean res = mascotaDAO.insertarMascota(mascota);
        return res;
    }
}
