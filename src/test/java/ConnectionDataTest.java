import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ConnectionDataTest
{
    ConnectionData cd;
    public void setUp()
    {
        cd = new ConnectionData();
    }

    @Test
    public void testAddAndGet1()
    {
        setUp();
        cd.addToRoute(new StopName("A"), new Time(1), Optional.of(new LineName("1")));

        assertEquals(new StopName("A"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(0).getValue1());
        assertEquals(new LineName("1"),cd.getRoute().get(0).getValue2().get());

        cd.addToRoute(new StopName("A"), new Time(1), Optional.of(new LineName("1")));

        assertEquals(new StopName("A"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(0).getValue1());
        assertEquals(new LineName("1"),cd.getRoute().get(0).getValue2().get());

    }
    @Test
    public void testAddAndGet2()
    {
        setUp();
        cd.addToRoute(new StopName("A"), new Time(1), Optional.of(new LineName("1")));
        cd.addToRoute(new StopName("B"), new Time(2), Optional.of(new LineName("2")));

        assertEquals(new StopName("A"),cd.getRoute().get(1).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(1).getValue1());
        assertEquals(new LineName("1"),cd.getRoute().get(1).getValue2().get());

        assertEquals(new StopName("B"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(2),cd.getRoute().get(0).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(0).getValue2().get());

    }

    @Test
    public void testAddAndGet3()
    {
        setUp();
        cd.addToRoute(new StopName("A"), new Time(1), Optional.of(new LineName("1")));
        cd.addToRoute(new StopName("B"), new Time(2), Optional.of(new LineName("2")));
        cd.addToRoute(new StopName("C"), new Time(3), Optional.of(new LineName("3")));

        assertEquals(new StopName("A"),cd.getRoute().get(2).getValue0());
        assertEquals(new Time(1),cd.getRoute().get(2).getValue1());
        assertEquals(new LineName("1"),cd.getRoute().get(2).getValue2().get());

        assertEquals(new StopName("B"),cd.getRoute().get(1).getValue0());
        assertEquals(new Time(2),cd.getRoute().get(1).getValue1());
        assertEquals(new LineName("2"),cd.getRoute().get(1).getValue2().get());

        assertEquals(new StopName("C"),cd.getRoute().get(0).getValue0());
        assertEquals(new Time(3),cd.getRoute().get(0).getValue1());
        assertEquals(new LineName("3"),cd.getRoute().get(0).getValue2().get());
    }
}
