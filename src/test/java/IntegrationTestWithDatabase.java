import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntegrationTestWithDatabase
{
    private ConnectionSearch cs;
    private ConnectionSearch cs2;
    private ConnectionData cd;
    DatabaseManipulator db;
    DatabaseManipulator db2;
    public void setUp()
    {
        db = new DatabaseManipulator("TCSA.db");
        db.executeSQLFile("TCSA.sql");

        Stops stops = new Stops(new DatabaseStopsFactory(db));
        Lines lines = new Lines(new DatabaseLinesFactory(stops, db));

        cs = new ConnectionSearch(stops, lines);


        db2 = new DatabaseManipulator("TCSA2.db");
        db2.executeSQLFile("TCSA.sql");
        db2.executeSQLFile("TCSA2.sql");

        Stops stops2 = new Stops(new DatabaseStopsFactory(db2));
        Lines lines2 = new Lines(new DatabaseLinesFactory(stops2, db2));

        cs2 = new ConnectionSearch(stops2, lines2);
    }

    @Test
    public void test1()
    {
        setUp();

        assertEquals(Integer.valueOf(9), db.getPassengersToSegment("2",2).get(new Time(7)));
        assertEquals(Integer.valueOf(9), db.getPassengersToSegment("2",1).get(new Time(5)));

        cd = cs.search(new StopName("B12"), new StopName("C12"), new Time(1));
        assertTrue(cd.foundRoute());

        assertEquals(new StopName("C12"),cd.getRoute().get(2).getValue0());
        assertEquals(new Time(7),cd.getRoute().get(2).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(2).getValue2().get());

        assertEquals(new StopName("Q2"),cd.getRoute().get(1).getValue0());
        assertEquals(new Time(5),cd.getRoute().get(1).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(1).getValue2().get());

        assertEquals(new StopName("B12"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(0).getValue1());
        assertEquals(Optional.empty(),cd.getRoute().get(0).getValue2());

        assertEquals(Integer.valueOf(10), db.getPassengersToSegment("2",2).get(new Time(7)));
        assertEquals(Integer.valueOf(10), db.getPassengersToSegment("2",1).get(new Time(5)));

        cd = cs.search(new StopName("B12"), new StopName("C12"), new Time(1));
        assertTrue(cd.foundRoute());

        assertEquals(new StopName("C12"),cd.getRoute().get(2).getValue0());
        assertEquals(new Time(9),cd.getRoute().get(2).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(2).getValue2().get());

        assertEquals(new StopName("Q2"),cd.getRoute().get(1).getValue0());
        assertEquals(new Time(7),cd.getRoute().get(1).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(1).getValue2().get());

        assertEquals(new StopName("B12"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(0).getValue1());
        assertEquals(Optional.empty(),cd.getRoute().get(0).getValue2());
    }

    @Test
    public void test2()
    {
        setUp();

        cd = cs.search(new StopName("A1"), new StopName("C12"), new Time(1));

        assertTrue(cd.foundRoute());

        assertEquals(new StopName("C12"),cd.getRoute().get(3).getValue0());
        assertEquals(new Time(9),cd.getRoute().get(3).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(3).getValue2().get());

        assertEquals(new StopName("Q2"),cd.getRoute().get(2).getValue0());
        assertEquals(new Time(7),cd.getRoute().get(2).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(2).getValue2().get());

        assertEquals(new StopName("B12"),cd.getRoute().get(1).getValue0());
        assertEquals(new Time(6),cd.getRoute().get(1).getValue1());
        assertEquals(new LineName("1"),cd.getRoute().get(1).getValue2().get());

        assertEquals(new StopName("A1"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(0).getValue1());
        assertEquals(Optional.empty(),cd.getRoute().get(0).getValue2());
    }

    @Test
    public void test3blockedSegment()
    {
        setUp();

        cd = cs2.search(new StopName("A1"), new StopName("C12"), new Time(1));

        assertTrue(cd.foundRoute());

        assertEquals(new StopName("C12"),cd.getRoute().get(2).getValue0());
        assertEquals(new Time(11),cd.getRoute().get(2).getValue1());
        assertEquals(new LineName("1"),cd.getRoute().get(2).getValue2().get());

        assertEquals(new StopName("B12"),cd.getRoute().get(1).getValue0());
        assertEquals(new Time(6),cd.getRoute().get(1).getValue1());
        assertEquals(new LineName("1"),cd.getRoute().get(1).getValue2().get());

        assertEquals(new StopName("A1"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(0).getValue1());
        assertEquals(Optional.empty(),cd.getRoute().get(0).getValue2());
    }
}

