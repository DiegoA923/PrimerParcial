package udistrital.avanzada.primerparcial.Modelo.ModeloConexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Clase ConexionBD.
 * <p>
 * Descripción: [Agrega aquí la descripción de la clase].
 * </p>
 *
 * @author diego
 * @version 1.0
 */
public class ConexionBD {
    private static Connection cn = null;
    private static String URLBD = "jdbc:mysql://localhost:3307/animal";
    private static String usuario = "root";
    private static String contrasena = "";

    public static Connection getConexion() {
        try {
cn = DriverManager.getConnection(URLBD, usuario, contrasena);
        } catch (SQLException ex) {
            System.out.println("No se puede cargar el controlado");
        }
        return cn;
    }

    public static void desconectar() {
        cn = null;
    }
}