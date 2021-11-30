import java.sql.SQLException;

public interface StopsFactoryInterface
{
    StopInterface getStopByName(StopName stopName) throws SQLException;
}
