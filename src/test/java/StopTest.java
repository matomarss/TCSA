import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StopTest
{
    private Stop stop;

    public void setUp()
    {
        stop = new Stop(new StopName("A"), new Vector<>(Arrays.asList(new LineName("1"), new LineName("2"))));
    }

    @Test
    public void testGetName()
    {
        setUp();
        assertEquals(new StopName("A"), stop.getName());
    }
    @Test
    public void testGetLines()
    {
        setUp();
        assertEquals(new LineName("1"), stop.getLines().get(0));
        assertEquals(new LineName("2"), stop.getLines().get(1));
    }

    @Test
    public void testReachables()
    {
        setUp();
        assertEquals(Optional.empty(), stop.getReachableAt().getValue1());
        assertNull(stop.getReachableAt().getValue0());

        stop.updateReachableAt(new Time(5), Optional.of(new LineName("1")));
        assertEquals(new LineName("1"), stop.getReachableAt().getValue1().get());
        assertEquals(new Time(5), stop.getReachableAt().getValue0());

        stop.updateReachableAt(new Time(10), Optional.of(new LineName("2")));
        assertEquals(new LineName("1"), stop.getReachableAt().getValue1().get());
        assertEquals(new Time(5), stop.getReachableAt().getValue0());

        stop.updateReachableAt(new Time(4), Optional.of(new LineName("2")));
        assertEquals(new LineName("2"), stop.getReachableAt().getValue1().get());
        assertEquals(new Time(4), stop.getReachableAt().getValue0());
    }
}
