package jmcd.tfg.persistencia;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.junit5.DBUnitExtension;
import jmcd.tfg.persistencia.crud.EntityCRUD;
import jmcd.tfg.persistencia.dao.UsuarioDAO;
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
public class UsuarioDaoTest {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioDaoTest.class);

    private final String usuarioCrear = "usuarioCrear";
    private final String claveCrear = "12345";

    private final String usuarioModificaciones = "usuarioModificaciones";
    private final String claveModificaciones = "1234";

    private ConnectionHolder connectionHolder = () -> instance("testPersistencia").connection();

    @Autowired
    private UsuarioDAO usuarioDAO;

    @BeforeAll
    public static void init() {
        LOG.info("Iniciando los tests de la clase UsuarioDAO");
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
        usuarioDAO.crearUsuario(usuarioModificaciones, claveModificaciones);
    }

    @AfterEach
    public void closeTest() {
        usuarioDAO.borrarUsuario(usuarioModificaciones);
    }

    @Test
    public void crearUsuario() {
        LOG.info("Comprobando la funcion UsuarioDAO#crearUsuario");
        usuarioDAO.crearUsuario(usuarioCrear, claveCrear);
        Assertions.assertTrue(usuarioDAO.existe(usuarioCrear, claveCrear));
    }

    @Test
    public void existeUsuario() {
        LOG.info("Comprobando la funcion UsuarioDAO#existe");
        Assertions.assertTrue(usuarioDAO.existe(usuarioModificaciones, claveModificaciones));
    }

    @Test
    public void cambiarClave() {
        LOG.info("Comprobando la funcion UsuarioDAO#cambiarClave");
        usuarioDAO.cambiarClave(usuarioModificaciones, claveCrear);
        Assertions.assertTrue(usuarioDAO.existe(usuarioModificaciones, claveCrear));
    }

    @Test
    public void borrarUsuario() {
        LOG.info("Comprobando la funcion UsuarioDAO#borrarUsuario");
        usuarioDAO.borrarUsuario(usuarioModificaciones);
        Assertions.assertFalse(usuarioDAO.existe(usuarioModificaciones, claveModificaciones));
    }
}
