package udistrital.avanzada.primerparcial.Control;

import java.io.IOException;
import java.util.*;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.PropertiesDAO;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.ModeloDAO.IPropertiesDAO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;

/**
 * Gestor del archivo configuracion.properties.
 * <p>
 * Lee, mantiene en memoria y persiste cambios al archivo de propiedades.
 * </p>
 *
 * Modificacion: Diego - 2025-10-14
 */
public class GestorArchivoProperties {

    private final IPropertiesDAO propertiesDAO;
    private final Properties cachedProps;

    /**
     * Constructor: carga el archivo una vez y lo mantiene en memoria.
     *
     * @param propertiesDAO DAO que maneja acceso físico al archivo
     */
    public GestorArchivoProperties(PropertiesDAO propertiesDAO) {
        this.propertiesDAO = propertiesDAO;
        // cargar y mantener en memoria
        this.cachedProps = propertiesDAO.obtenerProperties();
    }

    /**
     * Carga todas las mascotas definidas en el archivo. Cada clave debe ser
     * "AnimalN=..."
     *
     * @return lista de MascotaVO con id extraído de la clave (AnimalN -> id =
     * N)
     */
    public List<MascotaVO> cargarMascotasDesdeProperties() {
        List<MascotaVO> mascotas = new ArrayList<>();

        List<String> keys = new ArrayList<>(cachedProps.stringPropertyNames());
        keys.removeIf(k -> !k.toLowerCase().startsWith("animal"));
        keys.sort(Comparator.comparingInt(k -> {
            try {
                return Integer.parseInt(k.replaceAll("[^0-9]", ""));
            } catch (Exception e) {
                return Integer.MAX_VALUE;
            }
        }));

        for (String key : keys) {
            String linea = cachedProps.getProperty(key, "").trim();
            if (linea.isEmpty()) {
                continue;
            }

            String[] partes = linea.split(",", -1);
            if (partes.length < 7) {
                continue;
            }

            MascotaVO mascota = new MascotaVO();
            try {
                mascota.setId(Integer.parseInt(key.replaceAll("[^0-9]", "")));
            } catch (NumberFormatException ex) {
                mascota.setId(0);
            }

            mascota.setNombreComun(partes[0].trim());
            mascota.setApodo(partes[1].trim());
            try {
                mascota.setClasificacion(Clasificacion.valueOf(partes[2].trim().toUpperCase()));
            } catch (Exception ex) {
                mascota.setClasificacion(null);
            }
            mascota.setFamilia(partes[3].trim());
            mascota.setGenero(partes[4].trim());
            mascota.setEspecie(partes[5].trim());
            try {
                mascota.setTipoAlimento(TipoAlimento.valueOf(partes[6].trim().toUpperCase()));
            } catch (Exception ex) {
                mascota.setTipoAlimento(null);
            }

            // **Siempre asignar la clave para poder eliminarla luego**
            mascota.setClaveProperties(key);
            mascotas.add(mascota);
        }

        return mascotas;
    }

    /**
     * Elimina una entrada (clave) del Properties en memoria. No escribe en
     * disco hasta llamar a guardarCambios().
     *
     * @param clave p.ej. "Animal3"
     */
    public void eliminarMascotaDeProperties(String clave) {
        if (clave == null || clave.isEmpty()) {
            return;
        }
        cachedProps.remove(clave);
    }

    /**
     * Persiste en disco el estado actual del Properties en memoria. Llama a
     * PropertiesDAO.guardarProperties(...) para respetar la ruta.
     */
    public void guardarCambios() {
        try {
            propertiesDAO.guardarProperties(cachedProps);
        } catch (IOException e) {
            System.err.println("Error guardando configuracion.properties: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
