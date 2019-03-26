package jmcd.tfg.persistencia;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import jmcd.tfg.persistencia.crud.EntityCRUD;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
import jmcd.tfg.persistencia.dao.VotacionDAO;
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
//        EntityCRUD.initPersistencia();
        usuarioDAO.crearUsuario(usuario1, clave);
    }

    @AfterEach
    public void closeTest() {
//        EntityCRUD.closePersistencia();
    }

    //    @Test
    public void crearVotacion() {
        LOG.info("Comprobando la funcion votacionDAO#crearVotacion");
        votacionDAO.crearVotacion(votacion1, usuario1);
        LOG.info("Votacion turutu: " + votacionDAO.getVotacion(votacion1, usuario1).getNombreVotacion());
        Assertions.assertTrue(true);
    }

//    @Test
    public void getVotacion() {
        LOG.info("Comprobando la funcion votacionDAO#getVotacion");
        Assertions.assertTrue(votacionDAO.getVotacion(votacion1, usuario1).getIdVotacion().equals("1"));
    }

    //    @Test
    public void borrarUsuario() {
        LOG.info("Comprobando la funcion UsuarioDAO#borrarUsuario");
//        usuarioDAO.borrarUsuario(pepe);
//        Assertions.assertFalse(usuarioDAO.existe(pepe, clave2));
    }
}
