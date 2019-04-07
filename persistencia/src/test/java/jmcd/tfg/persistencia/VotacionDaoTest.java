package jmcd.tfg.persistencia;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import jmcd.tfg.persistencia.crud.EntityCRUD;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.dao.VotacionDAO;
import jmcd.tfg.persistencia.model.Votacion;
import jmcd.tfg.persistencia.test.config.TestingConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static com.github.database.rider.core.util.EntityManagerProvider.instance;

/**
 * Test class for the DAO classes
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(DBUnitExtension.class)
@ContextConfiguration(classes = TestingConfig.class, loader = AnnotationConfigContextLoader.class)
public class VotacionDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(VotacionDaoTest.class);

    private final String usuario1 = "juan";
    private final String clave = "12345";

    private final String usuario2 = "pepe";
    private final String clave2 = "1234";

    private final String votacion1 = "votacion1";
    private final String votacion2 = "votacion2";


    private ConnectionHolder connectionHolder = () -> instance("testPersistencia").connection();

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private VotacionDAO votacionDAO;

    @BeforeAll
    public static void init() {
        LOG.info("Iniciando los tests de las clases DAO");
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

        if (!usuarioDAO.existe(usuario1, clave)) {
            usuarioDAO.crearUsuario(usuario1, clave);
        }
        if (!usuarioDAO.existe(usuario2, clave2)) {
            usuarioDAO.crearUsuario(usuario2, clave2);
        }
//        if (votacionDAO.getIdDesdeNombreUsuario(votacion2, usuario2) != null) {
        votacionDAO.crearVotacion(votacion2, usuario2);
//        }
    }

    @AfterEach
    public void closeTest() {
//        if (votacionDAO.getIdDesdeNombreUsuario(votacion2, usuario2) != null) {
        votacionDAO.borrarVotacion(votacionDAO.getIdDesdeNombreUsuario(votacion2, usuario2));
//        }
        if (usuarioDAO.existe(usuario2, clave2)) {
            usuarioDAO.borrarUsuario(usuario2);
        }
        if (usuarioDAO.existe(usuario1, clave)) {
            usuarioDAO.borrarUsuario(usuario1);
        }
    }

    @Test
    public void crearVotacion() {
        LOG.info("Comprobando la funcion votacionDAO#crearVotacion");
        Votacion votacion = votacionDAO.crearVotacion(votacion1, usuario1);
        int id = votacionDAO.getIdDesdeNombreUsuario(votacion1, usuario1);
        Assertions.assertTrue(votacion.getIdVotacion() == id);
    }

    @Test
    public void getVotacion() {
        LOG.info("Comprobando la funcion votacionDAO#getVotacion");
        int id = votacionDAO.getIdDesdeNombreUsuario(votacion2, usuario2);
        Votacion votacion = votacionDAO.getVotacion(id);
        Assertions.assertTrue(votacion.getNombreVotacion().equals(votacion2));
    }

//    @Test
//    public void borrarVotacion() {
//        LOG.info("Comprobando la funcion UsuarioDAO#borrarUsuario");
//        int id = votacionDAO.getIdDesdeNombreUsuario(votacion2, usuario2);
//        votacionDAO.borrarVotacion(id);
////        Assertions.assert(votacionDAO.getVotacion(id) == null);
//        Assertions.assertTrue(votacionDAO.getVotacion(id) == null);
//    }
}
