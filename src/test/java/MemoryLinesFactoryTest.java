import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

class StubStopsFactory implements StopsFactoryInterface
{
    List<Stop> stops;

    public StubStopsFactory(List<Stop> stops) {
        this.stops = stops;
    }

    @Override
    public StopInterface getStopByName(StopName stopName) {
        for (Stop stop:stops
             ) {
            if(stop.getName().equals(stopName)) return stop;
        }
        return null;
    }
}
public class MemoryLinesFactoryTest
{
    private InMemoryLinesFactory factory;
    private List<Quartet<LineName, StopName, Vector<Time>, List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>>>> linesList;
    private List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>> lineSegments;
    private Stops stops;

    private void setUp()
    {
        stops = new Stops(new StubStopsFactory(Arrays.asList(new Stop(new StopName("A"), new Vector<>()), new Stop(new StopName("B"), new Vector<>()))));
        linesList = new ArrayList<>();
        lineSegments = new ArrayList<>();


        Map<Time, Integer> passengers23 = new HashMap<>();
        passengers23.put(new Time(5), 8);
        passengers23.put(new Time(6), 6);
        lineSegments.add(new Quintet<>(new TimeDiff(2), passengers23, 10, new LineName("2"), new StopName("B")));
        linesList.add(new Quartet<>(new LineName("2"), new StopName("A"), new Vector<>(Arrays.asList(new Time(3), new Time(4))), lineSegments));
        factory = new InMemoryLinesFactory(stops, linesList);

    }

    @Test
    public void testGetLineByName()
    {
        setUp();
        LineInterface line =factory.getLineByName(new LineName("2"));

        assertEquals(new LineName("2"), line.getName());
        line.updateReachable(new Time(1), new StopName("A"));
        assertEquals(new Time(5), stops.getStop(new StopName("B")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("B")).getReachableAt().getValue1().get());
    }

    /*@Test
    public void testCreateSegment()
    {
        setUp();
        LineInterface line =factory.getLineByName(new LineName("2"));

        assertEquals(new LineName("2"), line.getName());
        line.updateReachable(new Time(1), new StopName("A"));
        assertEquals(new Time(5), stops.getStop(new StopName("B")).getReachableAt().getValue0());
        assertEquals(new LineName("2"), stops.getStop(new StopName("B")).getReachableAt().getValue1().get());
    }*/
}
