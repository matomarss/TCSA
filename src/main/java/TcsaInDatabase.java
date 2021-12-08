public class TcsaInDatabase extends Tcsa
{
    public TcsaInDatabase()
    {
        DatabaseManipulator db = new DatabaseManipulator("TCSA.db");
        db.executeSQLFile("TCSA.sql");

        Stops stops = new Stops(new DatabaseStopsFactory(db));
        Lines lines = new Lines(new DatabaseLinesFactory(stops, db));

        connectionSearch = new ConnectionSearch(stops, lines);
    }
}
