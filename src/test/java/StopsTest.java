import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StopsTest
{
    private Stops stops;
    private Stop stopA;
    private Stop stopB;

    public void setUp()
    {
        stopA = new Stop(new StopName("A"), new Vector<>(Arrays.asList(new LineName("1"), new LineName("2"))));
        stopB = new Stop(new StopName("B"), new Vector<>());


        stops = new Stops(new StubStopsFactory(Arrays.asList(stopA,stopB)));

    }
    @Test
    public void testGetStop()
    {
        setUp();
        assertEquals(new StopName("A"), stops.getStop(new StopName("A")).getName());
        assertEquals(new StopName("B"), stops.getStop(new StopName("B")).getName());
        assertNull(stops.getStop(new StopName("C")));
    }

    @Test
    public void testGetReachableAt()
    {
        setUp();

        stopA.updateReachableAt(new Time(2), Optional.of(new LineName("1")));
        stopB.updateReachableAt(new Time(23), Optional.of(new LineName("5")));

        assertEquals(new LineName("1"), stops.getReachableAt(new StopName("A")).getValue1().get());
        assertEquals(new LineName("5"), stops.getReachableAt(new StopName("B")).getValue1().get());
        assertEquals(new Time(2), stops.getReachableAt(new StopName("A")).getValue0());
        assertEquals(new Time(23), stops.getReachableAt(new StopName("B")).getValue0());
    }

    @Test
    public void testEarliestAfter()
    {
        setUp();

        assertEquals(Optional.empty(), stops.earliestReachableStopAfterTime(new Time(1)));

        stops.getStop(new StopName("A")).updateReachableAt(new Time(2), Optional.of(new LineName("1")));
        stops.getStop(new StopName("B")).updateReachableAt(new Time(23), Optional.of(new LineName("5")));

        assertEquals(Arrays.asList(new StopName("A")),stops.earliestReachableStopAfterTime(new Time(1)).get().getValue0());
        assertEquals(new Time(2),stops.earliestReachableStopAfterTime(new Time(1)).get().getValue1());

        assertEquals(Arrays.asList(new StopName("B")),stops.earliestReachableStopAfterTime(new Time(2)).get().getValue0());
        assertEquals(new Time(23),stops.earliestReachableStopAfterTime(new Time(2)).get().getValue1());

        assertEquals(Arrays.asList(new StopName("B")),stops.earliestReachableStopAfterTime(new Time(15)).get().getValue0());
        assertEquals(new Time(23),stops.earliestReachableStopAfterTime(new Time(15)).get().getValue1());

        assertEquals(Optional.empty(),stops.earliestReachableStopAfterTime(new Time(23)));

        assertEquals(Optional.empty(),stops.earliestReachableStopAfterTime(new Time(100)));

        assertEquals(Arrays.asList(new StopName("A")),stops.earliestReachableStopAfterTime(new Time(1)).get().getValue0());
    }

    @Test
    public void testSetStartStop()
    {
        setUp();
        stops.setStartingStop(new StopName("A"), new Time(5));

        assertEquals(new Time(5), stopA.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopA.getReachableAt().getValue1());
    }

    @Test
    public void testGetLines()
    {
        setUp();
        assertEquals(new LineName("1"),stops.getLines(new StopName("A")).get(0));
        assertEquals(new LineName("2"),stops.getLines(new StopName("A")).get(1));
    }
}
