import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DatabaseStopsFactory implements StopsFactoryInterface {
    private DatabaseManipulatorInterface db;

    public DatabaseStopsFactory(DatabaseManipulatorInterface database)
    {
        db = database;
    }

    @Override
    public StopInterface getStopByName(StopName stopName) {
        Vector<LineName> lines = db.getLinesToStop(stopName.getStopName());



        return new Stop(stopName, lines);
    }
}
