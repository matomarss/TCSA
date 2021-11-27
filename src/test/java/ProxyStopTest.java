import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ProxyStopTest
{
    private ProxyStop proxy1;

    public void setUp()
    {
        Stops stops = new Stops(new StubStopsFactory(Arrays.asList(new Stop(new StopName("A"), new Vector<>(Arrays.asList(new LineName("1"), new LineName("2")))))));
        proxy1 = new ProxyStop(stops, new StopName("A"));
    }

    @Test
    public void testGetName()
    {
        setUp();
        assertEquals(new StopName("A"), proxy1.getName());
    }
    @Test
    public void testGetLines()
    {
        setUp();
        assertEquals(new LineName("1"), proxy1.getLines().get(0));
        assertEquals(new LineName("2"), proxy1.getLines().get(1));
    }

    @Test
    public void testReachables()
    {
        setUp();
        assertEquals(Optional.empty(), proxy1.getReachableAt().getValue1());
        assertNull(proxy1.getReachableAt().getValue0());

        proxy1.updateReachableAt(new Time(5), Optional.of(new LineName("1")));
        assertEquals(new LineName("1"), proxy1.getReachableAt().getValue1().get());
        assertEquals(new Time(5), proxy1.getReachableAt().getValue0());

        proxy1.updateReachableAt(new Time(10), Optional.of(new LineName("2")));
        assertEquals(new LineName("1"), proxy1.getReachableAt().getValue1().get());
        assertEquals(new Time(5), proxy1.getReachableAt().getValue0());

        proxy1.updateReachableAt(new Time(4), Optional.of(new LineName("2")));
        assertEquals(new LineName("2"), proxy1.getReachableAt().getValue1().get());
        assertEquals(new Time(4), proxy1.getReachableAt().getValue0());
    }
}
