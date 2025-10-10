package udistrital.avanzada.primerparcial.Modelo;

/**
 * Clase abstracta que representa un animal genérico dentro del sistema.
 * <p>
 * Esta clase sirve como superclase para las mascotas exóticas y define las
 * características comunes que todas comparten. Se usa para aplicar el principio
 * de sustitución de Liskov (LSP).
 * </p>
 *
 * @author mauricio
 * @version 1.0
 * @since 2025-10-09
 * @modified Diego - 2025-10-10: Se reemplazaron los String por Enum para
 * clasificación y alimento.
 */
public abstract class Animal {

    protected String nombreComun;
    protected Clasificacion clasificacion;
    protected String familia;
    protected String genero;
    protected String especie;
    protected TipoAlimento tipoAlimento;

    /**
     * Constructor vacío de la clase Animal.
     * <p>
     * Permite crear una instancia de Animal sin inicializar sus atributos. Este
     * constructor es útil para permitir la herencia y la creación de objetos de
     * sus subclases (como MascotaVO) mediante el constructor por defecto,
     * especialmente cuando los valores se asignan posteriormente con métodos
     * setter o por procesos automáticos del patrón DAO.
     */
    public Animal() {

    }

    /**
     * Constructor principal del animal.
     *
     * @param nombreComun nombre común del animal
     * @param clasificacion clasificación taxonómica del animal
     * @param familia familia biológica del animal
     * @param genero género biológico
     * @param especie especie biológica
     * @param tipoAlimento tipo de alimento principal
     */
    public Animal(String nombreComun, Clasificacion clasificacion, String familia, String genero, String especie, TipoAlimento tipoAlimento) {
        this.nombreComun = nombreComun;
        this.clasificacion = clasificacion;
        this.familia = familia;
        this.genero = genero;
        this.especie = especie;
        this.tipoAlimento = tipoAlimento;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public TipoAlimento getTipoAlimento() {
        return tipoAlimento;
    }

    public void setTipoAlimento(TipoAlimento tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }
}
