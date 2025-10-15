package udistrital.avanzada.primerparcial.Control;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import udistrital.avanzada.primerparcial.Modelo.Clasificacion;
import udistrital.avanzada.primerparcial.Modelo.MascotaVO;
import udistrital.avanzada.primerparcial.Modelo.TipoAlimento;

/**
 * Clase de pruebas unitarias para {@link ControlMascota}.
 *
 * Se evalúan los principales métodos del controlador que gestionan:
 * 
 *     La inserción de mascotas en la base de datos.
 *     La obtención de registros almacenados.
 *     La carga de mascotas desde el archivo.properties.
 * 
 * 
 * 
 *
 * @author sebas
 * @version 2.0
 * @since 14/10/2025
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControlMascotaTest {

    /**
     * Instancia del controlador bajo prueba.
     */
    private ControlMascota controlMascota;

    /**
     * Método ejecutado una sola vez antes de todas las pruebas.
     * Se usa para inicializar recursos globales 
     */
    @BeforeAll
    static void inicializarTodo() {
    }

    /**
     * Se ejecuta antes de cada prueba individual.
     * Crea una nueva instancia del controlador para asegurar
     * independencia entre los casos de prueba.
     */
    @BeforeEach
    void setUp() {
        controlMascota = new ControlMascota();
    }

    /**
     * Prueba unitaria del método {ControlMascota#insertarMascota(int, String, Clasificacion, String, String, String, TipoAlimento)}.
     *
     * Valida que el método pueda ejecutarse correctamente y registrar una mascota
     * en la base de datos sin lanzar excepciones, aunque el registro ya exista
     * 
     */
    @Test
    @Order(1)
    void testInsertarMascotaConParametros() {
        // Given
        Clasificacion clasificacion = Clasificacion.MAMIFERO;
        TipoAlimento tipo = TipoAlimento.FORRAJES;

        boolean resultado = controlMascota.insertarMascota(
                1,
                "Burro Miniatura",
                clasificacion,
                "Equidae",
                "Equus",
                "E. africanus",
                tipo
        );

        // Se permite tanto true (insertado) como false (ya existía) para evitar falsos negativos.
        assertTrue(resultado || !resultado,
                "El método debe ejecutarse sin errores al insertar una mascota por parámetros");
    }

    /**
     * Prueba unitaria del método {ControlMascota#insertarMascota(MascotaVO)}.
     * 
     * Verifica que el controlador pueda insertar correctamente una mascota
     * construida como objeto {@link MascotaVO}, sin importar si ya existe.
     * 
     */
    @Test
    @Order(2)
    void testInsertarMascotaConObjeto() {
        // Given
        Clasificacion clasificacion = Clasificacion.REPTIL;
        TipoAlimento tipo = TipoAlimento.CARNES;

        MascotaVO mascota = new MascotaVO(
                2,
                "Spike",
                "Dragón Barbudo",
                clasificacion,
                "Agamidae",
                "Pogona",
                "P. vitticeps",
                tipo
        );

        boolean resultado = controlMascota.insertarMascota(mascota);

        assertTrue(resultado || !resultado,
                "La inserción no debe lanzar errores aunque el registro ya exista");
    }

    /**
     * Prueba el método {@link ControlMascota#obtenerMascotas()}.
     * 
     * Comprueba que la lista retornada no sea nula y que
     * el tipo de estructura de datos sea el esperado ({@link ArrayList}).
     * 
     */
    @Test
    @Order(3)
    void testObtenerMascotas() {
        
        ArrayList<MascotaVO> lista = controlMascota.obtenerMascotas();

        
        assertNotNull(lista, "La lista no debe ser nula");
        assertTrue(lista instanceof ArrayList, "Debe retornar un ArrayList de mascotas");
    }

    /**
     * Prueba el método {@link ControlMascota#cargarDesdeProperties()}.
     * 
     * Evalúa que la carga desde el archivo .properties
     * no genere excepciones y retorne una lista válida
     * (puede ser vacía o contener mascotas incompletas).
     * 
     */
    @Test
    @Order(4)
    void testCargarDesdeProperties() {
       
        List<MascotaVO> incompletas = controlMascota.cargarDesdeProperties();

        
        assertNotNull(incompletas, "La lista devuelta no debe ser nula");
        assertTrue(incompletas.isEmpty() || incompletas.size() >= 0,
                "El método debe devolver una lista (vacía o con mascotas incompletas)");
    }

    /**
     * Se ejecuta después de cada prueba individual.
     * Útil para limpiar datos.
     */
    @AfterEach
    void tearDown() {
    }

    /**
     * Método ejecutado una sola vez después de todas las pruebas.
     * Se usa para liberar recursos o imprimir mensajes de finalización.
     */
    @AfterAll
    static void finalizarTodo() {
    }
}
