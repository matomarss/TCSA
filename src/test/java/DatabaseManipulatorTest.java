import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DatabaseManipulatorTest
{
    private DatabaseManipulator dm;
    private void setUp()
    {
        dm = new DatabaseManipulator("TCSA.db");
        dm.executeSQLFile("TCSA.sql");
    }

    @Test
    public void testGetLinesToStop()
    {
        setUp();
        Vector<LineName> l = dm.getLinesToStop("B12");
        assertTrue((l.get(0).equals(new LineName("1"))&&l.get(1).equals(new LineName("2")))||
                (l.get(0).equals(new LineName("2"))&&l.get(1).equals(new LineName("1"))));
    }

    @Test
    public void testGetLine()
    {
        setUp();

        Pair<LineName, StopName> l = dm.getLine("1");

        assertEquals(new LineName("1"), l.getValue0());
        assertEquals(new StopName("A1"), l.getValue1());
    }

    @Test
    public void testGetTimesToLine()
    {
        setUp();
        Vector<Time> times = dm.getTimesToLine("1");
        assertEquals(1,times.get(0).getTime());
        assertEquals(2,times.get(1).getTime());
    }

    @Test
    public void testGetNumOfSegments()
    {
        setUp();
        int num = dm.getNumOfSegments("1");
        assertEquals(2, num);

        num = dm.getNumOfSegments("2");
        assertEquals(3, num);
    }

    @Test
    public void testGetSegmentToLine()
    {
        setUp();

        Triplet<TimeDiff, Integer, StopName> s = dm.getSegmentToLine("1",0);

        assertEquals(5, s.getValue0().getTime());
        assertEquals(Integer.valueOf(7), s.getValue1());
        assertEquals(new StopName("B12"), s.getValue2());

        s = dm.getSegmentToLine("1",1);

        assertEquals(5, s.getValue0().getTime());
        assertEquals(Integer.valueOf(8), s.getValue1());
        assertEquals(new StopName("C12"), s.getValue2());
    }

    @Test
    public void testGetPassengersToSegment()
    {
        setUp();

        Map<Time, Integer> nump = dm.getPassengersToSegment("1", 0);

        assertEquals(Integer.valueOf(5), nump.get(new Time(6)));
        assertEquals(Integer.valueOf(6), nump.get(new Time(7)));

        nump = dm.getPassengersToSegment("1", 1);

        assertEquals(Integer.valueOf(6), nump.get(new Time(11)));
        assertEquals(Integer.valueOf(7), nump.get(new Time(12)));

        nump = dm.getPassengersToSegment("2", 0);

        assertEquals(Integer.valueOf(5), nump.get(new Time(4)));
        assertEquals(Integer.valueOf(6), nump.get(new Time(6)));

        nump = dm.getPassengersToSegment("2", 1);

        assertEquals(Integer.valueOf(9), nump.get(new Time(5)));
        assertEquals(Integer.valueOf(9), nump.get(new Time(7)));
    }

    @Test
    public void testUpdateSegments()
    {
        setUp();

        Map<Time,Integer> m = new HashMap<>();
        m.put(new Time(6), 1);
        m.put(new Time(7), 2);

        Map<Time,Integer> m2 = new HashMap<>();
        m2.put(new Time(5), 3);
        m2.put(new Time(7), 4);

        dm.updateSegments(new Vector<>(Arrays.asList(new Triplet<>(new LineName("1"), 0, m), new Triplet<>(new LineName("2"),1,m2))));

        Map<Time, Integer> p = dm.getPassengersToSegment("1",0);

        assertEquals(Integer.valueOf(1), p.get(new Time(6)));
        assertEquals(Integer.valueOf(2), p.get(new Time(7)));

        p = dm.getPassengersToSegment("2",1);
        assertEquals(Integer.valueOf(3), p.get(new Time(5)));
        assertEquals(Integer.valueOf(4), p.get(new Time(7)));
    }
}
