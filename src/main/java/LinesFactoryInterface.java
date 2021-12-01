import java.sql.SQLException;

public interface LinesFactoryInterface
{
    LineInterface getLineByName(LineName lineName) throws SQLException;

    void createSegment(LineName lineName, int index);

    void clean();


}
