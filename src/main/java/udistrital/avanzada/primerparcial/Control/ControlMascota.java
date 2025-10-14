package udistrital.avanzada.primerparcial.Control;

import java.util.ArrayList;
import java.util.List;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionBD;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.ConexionProperties;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IConexionProperties;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.MascotaDAO;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.PropertiesDAO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;

/**
 * Clase ControlMascota.
* <p>
 * Controlador encargado de gestionar las operaciones relacionadas con las
 * mascotas dentro del sistema. Actúa como intermediario entre la vista o el
 * controlador principal y la capa de acceso a datos ({@link MascotaDAO}),
 * aplicando el patrón DAO.
 * </p>
 * <p>
 * Permite realizar operaciones de inserción y consulta de mascotas en la base
 * de datos, usando objetos del tipo {@link MascotaVO} y las entidades
 * auxiliares {@link Clasificacion} y {@link TipoAlimento}.
 * </p>
 * @author Diego
 * @version 1.0
 * @since 2025-10-12
 */
public class ControlMascota {

    private MascotaDAO mascotaDAO;

    public ControlMascota() {

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
    /**
     * Carga las mascotas desde el archivo .properties y las inserta en la base de datos.
     */
    public void cargarDesdeProperties() {
        try {
            IConexionProperties conexion = new ConexionProperties();
            PropertiesDAO propertiesDAO = new PropertiesDAO(conexion);
            GestorArchivoProperties gestor = new GestorArchivoProperties(propertiesDAO);

            List<MascotaVO> listaMascotas = gestor.cargarMascotasDesdeProperties();

            // Insertar cada mascota leída desde el archivo
            for (MascotaVO mascota : listaMascotas) {
                mascotaDAO.insertarMascota(mascota);
            }

        } catch (Exception e) {
        }
    }
}
