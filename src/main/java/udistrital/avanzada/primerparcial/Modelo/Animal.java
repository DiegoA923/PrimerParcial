package udistrital.avanzada.primerparcial.Modelo;

/**
 * Super clase animal para la sustitucion de liskov
 * @author mauricio
 */
public abstract class Animal {
    protected String clasificacion;
    protected String familia;
    protected String genero;
    protected String especie;

    public Animal(String clasificacion, String familia, String genero, String especie) {
        this.clasificacion = clasificacion;
        this.familia = familia;
        this.genero = genero;
        this.especie = especie;
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
    
}
