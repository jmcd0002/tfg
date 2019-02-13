package jmcd.tfg.persistencia;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.database.rider.core.util.EntityManagerProvider.instance;

/**
 * Tests for the structure on the database.
 */
@ExtendWith(DBUnitExtension.class)
public final class DBConnectionTest {

    private ConnectionHolder connectionHolder = () -> instance("testPersistencia").connection();

    @Test
    @DataSet(value = "datasets/xml/data.xml")
    public void cleanDB() {
    }

}
