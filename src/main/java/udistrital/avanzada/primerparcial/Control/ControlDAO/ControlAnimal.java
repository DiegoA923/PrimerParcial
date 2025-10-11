/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.primerparcial.Control.ControlDAO;

import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.Animal;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;

/**
 *
 * @author mauri
 */
public class ControlAnimal {
    private ArrayList<Animal> animales;

    public ControlAnimal() {
        this.animales = new ArrayList<>();
    }
    
    public void addMascota(int id, String apodo, String nombreComun, Clasificacion clasificacion, String familia, String genero, String especie, TipoAlimento tipoAlimento) {
        MascotaVO mascota = new MascotaVO(id, apodo, nombreComun, clasificacion, familia, genero, especie, tipoAlimento);
        animales.add(mascota);  
    }        
}
