package udistrital.avanzada.primerparcial.Control;

import java.util.*;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.PropertiesDAO;

/**
 * Clase GestorArchivoProperties.
 * 
 * Lee el archivo .properties estructurado por secciones (mascota1, mascota2, ...)
 * y lo convierte en una lista de objetos {@link MascotaVO}.
 * 
 * @author sebas
 * @since 14/10/2025
 */
public class GestorArchivoProperties {

    private final PropertiesDAO propertiesDAO;

    public GestorArchivoProperties(PropertiesDAO propertiesDAO) {
        this.propertiesDAO = propertiesDAO;
    }

    public List<MascotaVO> cargarMascotasDesdeProperties() {
        List<MascotaVO> mascotas = new ArrayList<>();
        Properties props = propertiesDAO.obtenerProperties();

        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(props.getProperty("cantidad", "0").trim());
        } catch (NumberFormatException ignored) {}

        for (int i = 1; i <= cantidad; i++) {
            String prefix = "mascota" + i + ".";
            MascotaVO mascota = new MascotaVO();

            mascota.setNombreComun(props.getProperty(prefix + "nombre", "").trim());
            mascota.setApodo(props.getProperty(prefix + "apodo", "").trim());
            mascota.setFamilia(props.getProperty(prefix + "familia", "").trim());
            mascota.setGenero(props.getProperty(prefix + "genero", "").trim());
            mascota.setEspecie(props.getProperty(prefix + "especie", "").trim());

            // Clasificación
            String clasifStr = props.getProperty(prefix + "clasificacion", "").trim().toUpperCase();
            try {
                mascota.setClasificacion(Clasificacion.valueOf(clasifStr));
            } catch (Exception e) {
                mascota.setClasificacion(null);
            }

            // Tipo de alimento
            String alimStr = props.getProperty(prefix + "alimento", "").trim().toUpperCase();
            try {
                mascota.setTipoAlimento(TipoAlimento.valueOf(alimStr));
            } catch (Exception e) {
                mascota.setTipoAlimento(null);
            }

            // Solo agregar si tiene nombre y clasificación
            if (!mascota.getNombreComun().isEmpty() && mascota.getClasificacion() != null) {
                mascotas.add(mascota);
            }
        }

        return mascotas;
    }
}
