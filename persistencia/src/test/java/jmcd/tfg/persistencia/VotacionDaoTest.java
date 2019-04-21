package jmcd.tfg.persistencia;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import jmcd.tfg.persistencia.crud.EntityCRUD;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.dao.VotacionDAO;
import jmcd.tfg.persistencia.pojo.VotacionPojo;
import jmcd.tfg.persistencia.test.config.TestingConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Map;

import static com.github.database.rider.core.util.EntityManagerProvider.instance;

/**
 * Test class for the DAO classes
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(DBUnitExtension.class)
@ContextConfiguration(classes = TestingConfig.class, loader = AnnotationConfigContextLoader.class)
public class VotacionDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(VotacionDaoTest.class);

    private final String usuarioCrear = "usuarioCrear";
    private final String claveCrear = "12345";

    private final String usuarioModificaciones = "usuarioModificaciones";
    private final String claveModificaciones = "1234";

    private final String votacionCrear = "votacionCrear";
    private final String votacionModificaciones = "votacionModificaciones";

    private final String partidoCrear = "partidoCrear";
    private final int votosCrear = 10;

    private final String partidoModificaciones = "partidoModificaciones";
    private final int votosModificaciones = 20;


    private ConnectionHolder connectionHolder = () -> instance("testPersistencia").connection();

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private VotacionDAO votacionDAO;

    @BeforeAll
    public static void init() {
        LOG.info("Iniciando los tests de la clase VotacionDAO");
        EntityCRUD.setPersistencia("testPersistencia");
        EntityCRUD.initPersistencia();
    }

    @AfterAll
    public static void close() {
        LOG.info("Se acabaron los tests de las clases DAO");
        EntityCRUD.closePersistencia();
    }

    @BeforeEach
    public void initTest() {
        usuarioDAO.crearUsuario(usuarioCrear, claveCrear);
        usuarioDAO.crearUsuario(usuarioModificaciones, claveModificaciones);
        int idVotacionModificaciones = votacionDAO.crearVotacion(votacionModificaciones, usuarioModificaciones).getIdVotacion();
        votacionDAO.anadirPartidoVotos(idVotacionModificaciones, partidoModificaciones, votosModificaciones);
    }

    @AfterEach
    public void closeTest() {
        votacionDAO.borrarVotacion(votacionDAO.getIdDesdeNombreUsuario(votacionModificaciones, usuarioModificaciones));
        usuarioDAO.borrarUsuario(usuarioModificaciones);
        usuarioDAO.borrarUsuario(usuarioCrear);
    }

    @Test
    public void crearVotacion() {
        LOG.info("Comprobando la funcion votacionDAO#crearVotacion");
        VotacionPojo votacionPojo = votacionDAO.crearVotacion(votacionCrear, usuarioCrear);
        int id = votacionDAO.getIdDesdeNombreUsuario(votacionCrear, usuarioCrear);
        Assertions.assertTrue(votacionPojo.getIdVotacion() == id);
    }

    @Test
    public void getVotacion() {
        LOG.info("Comprobando la funcion votacionDAO#getVotacion");
        int id = votacionDAO.getIdDesdeNombreUsuario(votacionModificaciones, usuarioModificaciones);
        VotacionPojo votacionPojo = votacionDAO.getVotacion(id);
        Assertions.assertTrue(votacionPojo.getNombreVotacion().equals(votacionModificaciones));
    }

    //    @Test
    public void borrarVotacion() {
        LOG.info("Comprobando la funcion votacionDAO#borrarUsuario");
        int id = votacionDAO.getIdDesdeNombreUsuario(votacionModificaciones, usuarioModificaciones);
        votacionDAO.borrarVotacion(id);
//        Assertions.assert(votacionDAO.getVotacion(id) == null);
        Assertions.assertTrue(votacionDAO.getVotacion(id) == null);
    }

    @Test
    public void anadirPartidoVotos() {
        LOG.info("Comprobando la funcion votacionDAO#anadirPartidoVotos");
        int idVotacionModificaciones = votacionDAO.getIdDesdeNombreUsuario(votacionModificaciones, usuarioModificaciones);
        votacionDAO.anadirPartidoVotos(idVotacionModificaciones, partidoCrear, votosCrear);
        Assertions.assertTrue(
                votacionDAO.getPartidosVotos(idVotacionModificaciones).get(partidoCrear)
                        == votosCrear);
    }

    @Test
    public void getPartidosVotos() {
        LOG.info("Comprobando la funcion votacionDAO#getPartidosVotos");
        int idVotacionModificaciones = votacionDAO.getIdDesdeNombreUsuario(votacionModificaciones, usuarioModificaciones);
        Map<String, Integer> mapaPartidosVotos = votacionDAO.getPartidosVotos(idVotacionModificaciones);
        Assertions.assertTrue(mapaPartidosVotos.containsKey(partidoModificaciones));
        Assertions.assertTrue(mapaPartidosVotos.containsValue(votosModificaciones));
    }

    @Test
    public void modificarPartidoVotos() {
        LOG.info("Comprobando la funcion votacionDAO#modificarPartidoVotos");
        int idVotacionModificaciones = votacionDAO.getIdDesdeNombreUsuario(votacionModificaciones, usuarioModificaciones);
        votacionDAO.modificarPartidoVotos(idVotacionModificaciones, partidoModificaciones, votosCrear);
        Assertions.assertTrue(
                votacionDAO.getPartidosVotos(idVotacionModificaciones).get(partidoModificaciones)
                        == votosCrear);
    }

    @Test
    public void borrarPartidoVotos() {
        LOG.info("Comprobando la funcion votacionDAO#borrarPartidoVotos");
        int idVotacionModificaciones = votacionDAO.getIdDesdeNombreUsuario(votacionModificaciones, usuarioModificaciones);
        votacionDAO.borrarPartidoVotos(idVotacionModificaciones, partidoModificaciones);
        Assertions.assertNull(votacionDAO.getPartidosVotos(idVotacionModificaciones).get(partidoModificaciones));
    }

}
