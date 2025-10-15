package udistrital.avanzada.primerparcial.Modelo;

/**
 * Representa una mascota exótica dentro del sistema.
 * <p>
 * Esta clase hereda de {@link Animal} y actúa como un objeto de valor (VO) para
 * aplicar el patrón DAO. Contiene los atributos propios de una mascota, como su
 * identificador y apodo.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-09
 * @modified Diego - 2025-10-10: Se adaptó la clase para usar enums en lugar de
 * Strings.
 */
public class MascotaVO extends Animal {

    private int id;
    private String apodo;

    // clave en configuracion.properties
    private String claveProperties;

    /**
     * Constructor vacío de la clase MascotaVO.
     * <p>
     * Permite crear una instancia de MascotaVO sin inicializar sus atributos,
     * útil cuando los valores se asignan posteriormente mediante los métodos
     * setter, como en el proceso de consulta con el patrón DAO.
     */
    public MascotaVO() {

    }

    /**
     * Constructor principal de la clase MascotaVO.
     *
     * @param id identificador único de la mascota
     * @param apodo apodo de la mascota
     * @param nombreComun nombre común de la mascota
     * @param clasificacion clasificación biológica de la mascota
     * @param familia familia taxonómica
     * @param genero género biológico
     * @param especie especie biológica
     * @param tipoAlimento tipo de alimento principal
     */
    public MascotaVO(int id, String apodo, String nombreComun, Clasificacion clasificacion, String familia, String genero, String especie, TipoAlimento tipoAlimento) {
        super(nombreComun, clasificacion, familia, genero, especie, tipoAlimento);
        this.id = id;
        this.apodo = apodo;
    }

    /**
     * Verifica si la mascota tiene todos sus datos principales completos.
     * <p>
     * Este método no valida la coherencia de los datos, solo verifica que los
     * campos obligatorios no estén vacíos o nulos.
     * </p>
     *
     * @return {@code true} si todos los campos están completos; {@code false}
     * en caso contrario
     *
     * @modification Diego - 2025-10-14: Añadido método de validación para
     * verificar completitud de datos en la entidad MascotaVO.
     */
    public boolean tieneDatosCompletos() {
        return nombreComun != null && !nombreComun.isBlank()
                && apodo != null && !apodo.isBlank()
                && familia != null && !familia.isBlank()
                && genero != null && !genero.isBlank()
                && especie != null && !especie.isBlank()
                && clasificacion != null
                && tipoAlimento != null;
    }

    /**
     * Obtiene el identificador único de la mascota.
     *
     * @return identificador de la mascota
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna un nuevo identificador a la mascota.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el apodo de la mascota.
     *
     * @return apodo de la mascota
     */
    public String getApodo() {
        return apodo;
    }

    /**
     * Asigna un nuevo apodo a la mascota.
     *
     * @param apodo nuevo apodo
     */
    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getClaveProperties() {
        return claveProperties;
    } 

    public void setClaveProperties(String claveProperties) {
        this.claveProperties = claveProperties;
    }

}
