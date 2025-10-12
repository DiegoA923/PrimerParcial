/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udistrital.avanzada.primerparcial.Modelo.interfaz;

import java.io.IOException;

/**
 *
 * @author user
 */
public interface IConexion {
    public void conectar()throws IOException;
    public void desconectar()throws IOException;
}
