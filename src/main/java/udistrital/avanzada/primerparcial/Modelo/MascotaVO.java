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
    private String apodo;

    public MascotaVO(int id, String apodo, String nombreComun, String clasificacion, String familia, String genero, String especie, String tipoAlimento) {
        super(nombreComun, clasificacion, familia, genero, especie, tipoAlimento);
        this.id = id;
        this.apodo = apodo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

}
