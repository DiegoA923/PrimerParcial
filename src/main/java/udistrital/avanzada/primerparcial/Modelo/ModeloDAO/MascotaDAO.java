package udistrital.avanzada.primerparcial.Modelo.ModeloDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Modelo.ModeloConexion.IConexion;

/**
 * Clase MascotaDAO.
 * <p>
 * Gestiona las operaciones CRUD relacionadas con las mascotas en la base de
 * datos.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-10-10
 */
public class MascotaDAO {

    // Interfaz de conexión a la base de datos, inyectada desde el exterior.
    private final IConexion conexion;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param conexion objeto que implementa {@link IConexion} y gestiona la
     * conexión a la base de datos
     */
    public MascotaDAO(IConexion conexion) {
        this.conexion = conexion;
    }

    /**
     * Inserta una nueva mascota exótica en la base de datos.
     * <p>
     * Este método recibe un objeto {@link MascotaVO} con todos los datos
     * necesarios y los inserta en la tabla <b>Mascotas</b>. Antes de realizar
     * la inserción, se verifica si ya existe una mascota con exactamente las
     * mismas características (nombre común, apodo, clasificación, familia,
     * género, especie y tipo de alimento). Si ya existe, la inserción se
     * rechaza y el método devuelve {@code false}.
     * </p>
     *
     * @param mascota objeto {@link MascotaVO} que contiene la información de la
     * mascota
     * @return {@code true} si la inserción fue exitosa, {@code false} si ya
     * existía
     * @throws RuntimeException si ocurre un error al ejecutar la consulta SQL
     */
    public boolean insertarMascota(MascotaVO mascota) {
        String verificarSql = """
            SELECT COUNT(*) FROM mascotas 
            WHERE nombre_comun = ? AND apodo = ? AND clasificacion = ? 
            AND familia = ? AND genero = ? AND especie = ? AND tipo_alimento = ?
            """;

        String insertarSql = """
            INSERT INTO mascotas (nombre_comun, apodo, clasificacion, familia, genero, especie, tipo_alimento)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = conexion.getConexion(); PreparedStatement verificarPs = con.prepareStatement(verificarSql); PreparedStatement insertarPs = con.prepareStatement(insertarSql)) {

            // Verificar si ya existe la mascota
            verificarPs.setString(1, mascota.getNombreComun());
            verificarPs.setString(2, mascota.getApodo());
            verificarPs.setString(3, mascota.getClasificacion().name());
            verificarPs.setString(4, mascota.getFamilia());
            verificarPs.setString(5, mascota.getGenero());
            verificarPs.setString(6, mascota.getEspecie());
            verificarPs.setString(7, mascota.getTipoAlimento().name());

            ResultSet rs = verificarPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Ya existe una mascota igual
                return false;
            }

            // Insertar nueva mascota
            insertarPs.setString(1, mascota.getNombreComun());
            insertarPs.setString(2, mascota.getApodo());
            insertarPs.setString(3, mascota.getClasificacion().name());
            insertarPs.setString(4, mascota.getFamilia());
            insertarPs.setString(5, mascota.getGenero());
            insertarPs.setString(6, mascota.getEspecie());
            insertarPs.setString(7, mascota.getTipoAlimento().name());

            insertarPs.executeUpdate();
            return true;

        } catch (SQLException e) {
            // Propagación del error para que sea manejado por la capa Control
            throw new RuntimeException("Error al insertar la mascota: " + e.getMessage(), e);
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Obtiene la lista completa de mascotas registradas en la base de datos.
     * <p>
     * Recupera todos los registros almacenados en la tabla {@code Mascotas},
     * construyendo una lista con objetos {@link MascotaVO} que contienen la
     * información completa de cada mascota.
     * </p>
     *
     * @return lista de objetos {@link MascotaVO} con la información de cada
     * mascota, o una lista vacía si no existen registros
     * @throws RuntimeException si ocurre un error al realizar la consulta
     */
    public ArrayList<MascotaVO> listaDeMascotas() {
        ArrayList<MascotaVO> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MascotaVO mascota = new MascotaVO();
                mascota.setId(rs.getInt("id"));
                mascota.setApodo(rs.getString("apodo"));
                mascota.setNombreComun(rs.getString("nombre_comun"));
                mascota.setClasificacion(Clasificacion.valueOf(rs.getString("clasificacion").toUpperCase()));
                mascota.setFamilia(rs.getString("familia"));
                mascota.setGenero(rs.getString("genero"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setTipoAlimento(TipoAlimento.valueOf(rs.getString("tipo_alimento").toUpperCase()));
                mascotas.add(mascota);
            }

        } catch (SQLException ex) {
            // Propaga el error para ser manejado por la capa de control
            throw new RuntimeException("Error al obtener la lista de mascotas: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }

        return mascotas;
    }

    /**
     * Consulta las mascotas que coinciden con un apodo dado.
     * <p>
     * Permite buscar por coincidencia parcial del apodo, devolviendo todas las
     * mascotas cuyo apodo contenga la cadena especificada.
     * </p>
     *
     * @param apodo cadena de texto usada como criterio de búsqueda
     * @return lista de objetos {@link MascotaVO} que coinciden con el apodo
     * dado, o una lista vacía si no se encuentran resultados
     * @throws RuntimeException si ocurre un error al realizar la consulta
     */
    public ArrayList<MascotaVO> consultarPorApodo(String apodo) {
        ArrayList<MascotaVO> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE apodo LIKE ?";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + apodo + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MascotaVO mascota = new MascotaVO();
                mascota.setId(rs.getInt("id"));
                mascota.setApodo(rs.getString("apodo"));
                mascota.setNombreComun(rs.getString("nombre_comun"));
                mascota.setClasificacion(Clasificacion.valueOf(rs.getString("clasificacion").toUpperCase()));
                mascota.setFamilia(rs.getString("familia"));
                mascota.setGenero(rs.getString("genero"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setTipoAlimento(TipoAlimento.valueOf(rs.getString("tipo_alimento").toUpperCase()));
                mascotas.add(mascota);
            }

        } catch (SQLException ex) {
            // Propaga el error para que lo maneje el controlador o la capa superior
            throw new RuntimeException("Error al consultar por apodo: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }

        return mascotas;
    }

    /**
     * Consulta las mascotas que pertenecen a una clasificación específica.
     * <p>
     * Este método permite obtener todas las mascotas cuya clasificación
     * coincida con la especificada. Utiliza el tipo enumerado
     * {@link Clasificacion} para garantizar la integridad de los valores
     * posibles (por ejemplo, MAMIFERO, REPTIL, AVE, etc.).
     * </p>
     *
     * @param clasificacion tipo de clasificación de la mascota según
     * {@link Clasificacion}
     * @return lista de objetos {@link MascotaVO} que pertenecen a la
     * clasificación indicada, o una lista vacía si no se encuentran registros
     * @throws RuntimeException si ocurre un error durante la consulta a la base
     * de datos
     */
    public ArrayList<MascotaVO> consultarPorClasificacion(Clasificacion clasificacion) {
        ArrayList<MascotaVO> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE clasificacion = ?";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, clasificacion.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MascotaVO mascota = new MascotaVO();
                mascota.setId(rs.getInt("id"));
                mascota.setApodo(rs.getString("apodo"));
                mascota.setNombreComun(rs.getString("nombre_comun"));
                mascota.setClasificacion(Clasificacion.valueOf(rs.getString("clasificacion").toUpperCase()));
                mascota.setFamilia(rs.getString("familia"));
                mascota.setGenero(rs.getString("genero"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setTipoAlimento(TipoAlimento.valueOf(rs.getString("tipo_alimento").toUpperCase()));
                mascotas.add(mascota);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Error al consultar por clasificación: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }

        return mascotas;
    }

    /**
     * Consulta las mascotas que pertenecen a una familia específica.
     * <p>
     * Permite obtener todas las mascotas cuya familia coincida parcial o
     * totalmente con el valor proporcionado. La búsqueda no distingue entre
     * mayúsculas y minúsculas.
     * </p>
     *
     * @param familia nombre o fragmento de texto correspondiente a la familia
     * de la mascota
     * @return lista de objetos {@link MascotaVO} que pertenecen a la familia
     * indicada, o una lista vacía si no se encuentran coincidencias
     * @throws RuntimeException si ocurre un error al realizar la consulta
     */
    public ArrayList<MascotaVO> consultarPorFamilia(String familia) {
        ArrayList<MascotaVO> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE LOWER(familia) LIKE LOWER(?)";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + familia + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MascotaVO mascota = new MascotaVO();
                mascota.setId(rs.getInt("id"));
                mascota.setApodo(rs.getString("apodo"));
                mascota.setNombreComun(rs.getString("nombre_comun"));
                mascota.setClasificacion(Clasificacion.valueOf(rs.getString("clasificacion").toUpperCase()));
                mascota.setFamilia(rs.getString("familia"));
                mascota.setGenero(rs.getString("genero"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setTipoAlimento(TipoAlimento.valueOf(rs.getString("tipo_alimento").toUpperCase()));
                mascotas.add(mascota);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Error al consultar por familia: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }

        return mascotas;
    }

    /**
     * Consulta las mascotas según su tipo de alimento.
     * <p>
     * Permite obtener todas las mascotas cuyo tipo de alimento coincida con el
     * valor especificado. Utiliza el tipo enumerado {@link TipoAlimento} para
     * garantizar la validez del criterio de búsqueda (por ejemplo, CARNES,
     * FRUTAS, CEREALES, etc.).
     * </p>
     *
     * @param tipoAlimento tipo de alimento de la mascota según
     * {@link TipoAlimento}
     * @return lista de objetos {@link MascotaVO} que coinciden con el tipo de
     * alimento, o una lista vacía si no se encuentran resultados
     * @throws RuntimeException si ocurre un error al ejecutar la consulta SQL
     */
    public ArrayList<MascotaVO> consultarPorTipoAlimento(TipoAlimento tipoAlimento) {
        ArrayList<MascotaVO> mascotas = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE tipo_alimento = ?";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tipoAlimento.name());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MascotaVO mascota = new MascotaVO();
                mascota.setId(rs.getInt("id"));
                mascota.setApodo(rs.getString("apodo"));
                mascota.setNombreComun(rs.getString("nombre_comun"));
                mascota.setClasificacion(Clasificacion.valueOf(rs.getString("clasificacion").toUpperCase()));
                mascota.setFamilia(rs.getString("familia"));
                mascota.setGenero(rs.getString("genero"));
                mascota.setEspecie(rs.getString("especie"));
                mascota.setTipoAlimento(TipoAlimento.valueOf(rs.getString("tipo_alimento").toUpperCase()));
                mascotas.add(mascota);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Error al consultar por tipo de alimento: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }

        return mascotas;
    }

    /**
     * Elimina una mascota exótica de la base de datos.
     * <p>
     * Este método recibe el identificador único de la mascota que se desea
     * eliminar. Antes de ejecutar la eliminación, se asume que la mascota ya
     * fue consultada y visualizada en la interfaz. Si la eliminación es
     * exitosa, devuelve {@code true}.
     * </p>
     *
     * @param id identificador único de la mascota a eliminar
     * @return {@code true} si la eliminación fue exitosa, {@code false} si no
     * existía el registro
     * @throws RuntimeException si ocurre un error al ejecutar la eliminación
     */
    public boolean eliminarMascota(int id) {
        String sql = "DELETE FROM mascotas WHERE id = ?";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            // Si se eliminó una fila, el proceso fue exitoso
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            throw new RuntimeException("Error al eliminar la mascota: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Modifica los datos de una mascota existente en la base de datos.
     * <p>
     * Actualiza los campos permitidos de una mascota (nombre común, apodo,
     * clasificación y tipo de alimento), identificándola mediante su ID. Los
     * campos <b>familia</b>, <b>género</b> y <b>especie</b> no pueden ser
     * modificados.
     * </p>
     *
     * @param mascota objeto {@link MascotaVO} que contiene los nuevos valores
     * de la mascota, incluyendo su identificador {@code id}
     * @return {@code true} si la modificación fue exitosa, {@code false} si no
     * se encontró el registro
     * @throws RuntimeException si ocurre un error durante la actualización
     */
    public boolean modificarMascota(MascotaVO mascota) {
        String sql = """
            UPDATE mascotas
            SET nombre_comun = ?, apodo = ?, clasificacion = ?, tipo_alimento = ?
            WHERE id = ?
            """;

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mascota.getNombreComun());
            ps.setString(2, mascota.getApodo());
            ps.setString(3, mascota.getClasificacion().name());
            ps.setString(4, mascota.getTipoAlimento().name());
            ps.setInt(5, mascota.getId());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            throw new RuntimeException("Error al modificar la mascota: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Verifica si una mascota ya existe en la base de datos.
     * <p>
     * Este método realiza una búsqueda en la tabla {@code mascotas},
     * comprobando si existe algún registro con los mismos valores clave que
     * identifican a la mascota: nombre común, apodo, clasificación, familia,
     * género, especie y tipo de alimento.
     * </p>
     * <p>
     * <b>Modificación (Diego - 2025-10-14):</b> Se añadió este método para
     * evitar duplicados durante la carga de mascotas desde el archivo
     * {@code configuracion.properties}. Esto garantiza que las mascotas
     * completas se inserten una sola vez en la base de datos y no vuelvan a
     * cargarse en futuras ejecuciones.
     * </p>
     *
     * @param mascota objeto {@link MascotaVO} con los datos a verificar
     * @return {@code true} si ya existe un registro igual en la base de datos;
     * {@code false} si no existe
     * @throws RuntimeException si ocurre un error durante la verificación
     */
    public boolean existeMascota(MascotaVO mascota) {
        String sql = """
            SELECT COUNT(*) FROM mascotas
            WHERE nombre_comun = ? AND apodo = ? AND clasificacion = ?
            AND familia = ? AND genero = ? AND especie = ? AND tipo_alimento = ?
            """;

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mascota.getNombreComun());
            ps.setString(2, mascota.getApodo());
            ps.setString(3, mascota.getClasificacion() != null ? mascota.getClasificacion().name() : null);
            ps.setString(4, mascota.getFamilia());
            ps.setString(5, mascota.getGenero());
            ps.setString(6, mascota.getEspecie());
            ps.setString(7, mascota.getTipoAlimento() != null ? mascota.getTipoAlimento().name() : null);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de mascota: " + e.getMessage(), e);
        } finally {
            conexion.desconectar();
        }

        return false;
    }

}
