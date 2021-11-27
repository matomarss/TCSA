import org.javatuples.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class TestInMemoryStopsFactory
{
    private InMemoryStopsFactory factory;

    public void setUp()
    {
        factory = new InMemoryStopsFactory(Arrays.asList(
                new Pair<>(new StopName("A"), new Vector<>(Arrays.asList(new LineName("1"), new LineName("2")))),
                new Pair<>(new StopName("B"), new Vector<>())));
    }

    @Test
    public void testGetLineByName()
    {
        setUp();
        assertEquals(new StopName("A"),factory.getStopByName(new StopName("A")).getName());
        assertEquals(new StopName("B"),factory.getStopByName(new StopName("B")).getName());

        assertEquals(new LineName("1"),factory.getStopByName(new StopName("A")).getLines().get(0));
        assertEquals(new LineName("2"),factory.getStopByName(new StopName("A")).getLines().get(1));
    }
}
