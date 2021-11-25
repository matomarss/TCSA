import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;

import java.io.IOException;
import java.sql.SQLException;

public class Orm
{

    public Orm() throws SQLException, IOException {
        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource("jdbc:h2:mem:myDb");
        // work with the connectionSource
        connectionSource.close();
    }

}
