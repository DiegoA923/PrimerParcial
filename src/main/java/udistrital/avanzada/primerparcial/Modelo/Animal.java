package udistrital.avanzada.primerparcial.Modelo;

/**
 * Super clase animal para la sustitucion de liskov
 *
 * @author mauricio
 */
public abstract class Animal {

    protected String nombreComun;
    protected String clasificacion;
    protected String familia;
    protected String genero;
    protected String especie;
    protected String tipoAlimento;

    public Animal(String nombreComun, String clasificacion, String familia, String genero, String especie, String tipoAlimento) {
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

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
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

    public String getTipoAlimento() {
        return tipoAlimento;
    }

    public void setTipoAlimento(String tipoAlimento) {
        this.tipoAlimento = tipoAlimento;
    }

}
