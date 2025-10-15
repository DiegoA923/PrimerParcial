package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
/**
 * Interfaz que define las operaciones básicas de acceso a datos (DAO) 
 * para objetos de tipo {@link MascotaVO}.
 * 
 * Permite realizar operaciones CRUD (crear, leer, actualizar, eliminar),
 * así como consultas específicas por atributos.
 * 
 * @author Mauricio
 * @since 2025-10-15
 */
public interface IMascotaDAO {

    /**
     * Inserta una nueva mascota en el sistema o base de datos.
     * 
     * @param mascota Objeto {@link MascotaVO} que contiene los datos de la mascota a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    boolean insertarMascota(MascotaVO mascota);    

    /**
     * Obtiene una lista con todas las mascotas registradas.
     * 
     * @return Una lista de objetos {@link MascotaVO}, o una lista vacía si no hay registros.
     */
    ArrayList<MascotaVO> listaDeMascotas();

    /**
     * Consulta las mascotas cuyo apodo coincida (total o parcialmente) con el valor dado.
     * 
     * @param apodo El apodo a buscar.
     * @return Una lista de {@link MascotaVO} que coinciden con el apodo, o una lista vacía si no hay coincidencias.
     */
    ArrayList<MascotaVO> consultarPorApodo(String apodo);

    /**
     * Consulta las mascotas que pertenecen a la clasificación especificada.
     * 
     * @param clasificacion La clasificación a buscar
     * @return Lista de {@link MascotaVO} con la clasificación indicada, o una lista vacía si no hay coincidencias.
     */
    ArrayList<MascotaVO> consultarPorClasificacion(Clasificacion clasificacion);

    /**
     * Consulta las mascotas que pertenecen a la familia especificada.
     * 
     * @param familia El nombre de la familia a buscar.
     * @return Lista de {@link MascotaVO} con la familia indicada, o una lista vacía si no hay coincidencias.
     */
    ArrayList<MascotaVO> consultarPorFamilia(String familia);

    /**
     * Consulta las mascotas que consumen el tipo de alimento especificado.
     * 
     * @param tipoAlimento El tipo de alimento a buscar
     * @return Lista de {@link MascotaVO} que consumen ese tipo de alimento, o lista vacía si no hay coincidencias.
     */
    ArrayList<MascotaVO> consultarPorTipoAlimento(TipoAlimento tipoAlimento);

    /**
     * Elimina una mascota identificada por su ID.
     * 
     * @param id El identificador único de la mascota a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} si no se encontró la mascota.
     */
    boolean eliminarMascota(int id);

    /**
     * Modifica los datos de una mascota existente.
     * 
     * @param mascota Objeto {@link MascotaVO} con los datos actualizados. Debe contener un ID válido.
     * @return {@code true} si la modificación fue exitosa, {@code false} si no se encontró la mascota.
     */
    boolean modificarMascota(MascotaVO mascota);

    /**
     * Verifica si una mascota ya existe en el sistema. 
     * Esta comprobación puede basarse en su ID, nombre, o alguna regla definida por la implementación.
     * 
     * @param mascota Objeto {@link MascotaVO} con los datos de la mascota a verificar.
     * @return {@code true} si la mascota ya existe, {@code false} en caso contrario.
     */
    boolean existeMascota(MascotaVO mascota);  
}

