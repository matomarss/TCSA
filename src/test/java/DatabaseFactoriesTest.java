import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DatabaseFactoriesTest
{
    private DatabaseLinesFactory linesFactory;
    private Stops stops;

    private void setUp()
    {
        DatabaseManipulator dm = new DatabaseManipulator("TCSA.db");
        dm.executeSQLFile("TCSA.sql");

        stops = new Stops(new DatabaseStopsFactory(dm));
        linesFactory = new DatabaseLinesFactory(stops, dm);

    }

    @Test
    public void testGetLineByName()
    {
        setUp();
        LineInterface line = linesFactory.getLineByName(new LineName("2"));

        assertEquals(new LineName("2"), line.getName());
        line.updateReachable(new Time(1), new StopName("A2"));
        assertEquals(new Time(4), stops.getStop(new StopName("B12")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("B12")).getReachableAt().getValue1().get());
        assertEquals(new Time(5), stops.getStop(new StopName("Q2")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("Q2")).getReachableAt().getValue1().get());
        assertEquals(new Time(7), stops.getStop(new StopName("C12")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("C12")).getReachableAt().getValue1().get());
    }

    /*@Test
    public void testCreateSegment()
    {
        setUp();
        LineInterface line = linesFactory.getLineByName(new LineName("2"));

        assertEquals(new LineName("2"), line.getName());
        line.updateReachable(new Time(1), new StopName("A2"));
        assertEquals(new Time(4), stops.getStop(new StopName("B12")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("B12")).getReachableAt().getValue1().get());
        assertEquals(new Time(5), stops.getStop(new StopName("Q2")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("Q2")).getReachableAt().getValue1().get());
        assertEquals(new Time(7), stops.getStop(new StopName("C12")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("C12")).getReachableAt().getValue1().get());
    }*/
}
