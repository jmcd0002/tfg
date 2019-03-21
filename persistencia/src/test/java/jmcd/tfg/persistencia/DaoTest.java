package jmcd.tfg.persistencia;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
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
public class DaoTest {

    private static final Logger LOG= LoggerFactory.getLogger(DaoTest.class);

    private final String josema = "Josema";
    private final String clave = "cucu";

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
    public static void close(){
        LOG.info("Se acabaron los tests de las clases DAO");
        EntityCRUD.closePersistencia();
    }

    @BeforeEach
    public void initTest() {
//        EntityCRUD.initPersistencia();
    }

    @AfterEach
    public void closeTest() {
 //       EntityCRUD.closePersistencia();
    }

    //@Test
    public void crearUsuario() {
        LOG.info("Comprobando la funcion UsuarioDAO#crearUsuario");
        usuarioDAO.crearUsuario(josema, clave);
        Assertions.assertTrue(usuarioDAO.existe(josema, clave));
    }

  //  @Test
    @DataSet(value = "datasets/xml/unusuario.xml")
    public void existeUsuario() {
        LOG.info("Comprobando la funcion UsuarioDAO#existe");
        Assertions.assertTrue(usuarioDAO.existe(josema, clave));
    }

    @Test
    @DataSet(value = "datasets/xml/unusuario.xml")
    public void borrarUsuario() {
        LOG.info("Comprobando la funcion UsuarioDAO#borrarUsuario");
        usuarioDAO.borrarUsuario(josema);
        Assertions.assertFalse(usuarioDAO.existe(josema,clave));
    }
}
