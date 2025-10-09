package udistrital.avanzada.primerparcial.Modelo;

/**
 * Clase MascotaVO.
 * <p>
 * Descripción: Clase para aplicar el patron DAO.
 * </p>
 *
 * @author diego
 * @version 1.0
 */
public class MascotaVO extends Animal {

    private int id;
    private String nombre;
    private String apodo;
    private String tipoAlimento;

    public MascotaVO(String nombre, String apodo, String tipoAlimento, String clasificacion, String familia, String genero, String especie) {
        super(clasificacion, familia, genero, especie);
        this.nombre = nombre;
        this.apodo = apodo;
        this.tipoAlimento = tipoAlimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getTipoAlimento() {
        return tipoAlimento;
    }

    public void setTipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }

}
