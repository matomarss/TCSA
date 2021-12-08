import com.j256.ormlite.stmt.query.In;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class LineTest
{
    private Line line1;
    private Line line2;
    private Stop stopA;
    private Stop stopB;
    private Stop stopC;
    private Stop stopD;
    private Line line3;

    private LineSegment lineSegment1;
    private LineSegment lineSegment2;
    private LineSegment lineSegment3;


    public void setUp()
    {
        Vector<LineName> lineD = new Vector<>();
        lineD.add(new LineName("1"));
        stopD = new Stop(new StopName("D"), lineD);

        Vector<LineName> linesA = new Vector<>();
        linesA.add(new LineName("1"));
        stopA = new Stop(new StopName("A"), linesA);

        Vector<LineName> linesB = new Vector<>();
        linesB.add(new LineName("1"));
        stopB = new Stop(new StopName("B"), linesB);

        Vector<LineName> linesC = new Vector<>();
        linesC.add(new LineName("1"));
        stopC = new Stop(new StopName("C"), linesC);


        List<LineSegmentInterface> lineSegmentList = new ArrayList<>();


        Map<Time, Integer> passengers = new HashMap<>();
        passengers.put(new Time(8), 4);
        lineSegmentList.add(lineSegment1 = new LineSegment(new TimeDiff(5),passengers, 10, new LineName("1"), stopB));

        Map<Time, Integer> passengers2 = new HashMap<>();
        passengers2.put(new Time(16), 5);
        lineSegmentList.add(lineSegment2 = new LineSegment(new TimeDiff(8),passengers2, 10, new LineName("1"), stopC));

        Map<Time, Integer> passengers3 = new HashMap<>();
        passengers3.put(new Time(17), 8);
        lineSegmentList.add(lineSegment3 = new LineSegment(new TimeDiff(1),passengers3, 10, new LineName("1"), stopD));

        Vector<Time> startingTimes = new Vector<>();
        startingTimes.add(new Time(3));
        line1 = new Line(new LineName("1"),new StopName("A"), lineSegmentList, startingTimes);

        List<LineSegmentInterface> lineSegmentList2 = new ArrayList<>();


        Map<Time, Integer> passengersL2 = new HashMap<>();
        passengersL2.put(new Time(5), 5);
        lineSegmentList2.add(new LineSegment(new TimeDiff(4),passengersL2, 10, new LineName("2"), stopD));

        Map<Time, Integer> passengersL22 = new HashMap<>();
        passengersL22.put(new Time(6), 5);
        lineSegmentList2.add(new LineSegment(new TimeDiff(1),passengersL22, 10, new LineName("2"), stopA));

        Map<Time, Integer> passengersL23 = new HashMap<>();
        passengersL23.put(new Time(11), 5);
        lineSegmentList2.add(new LineSegment(new TimeDiff(5),passengersL23, 10, new LineName("2"), stopC));

        Vector<Time> startingTimes2 = new Vector<>();
        startingTimes2.add(new Time(1));
        line2 = new Line(new LineName("2"),new StopName("B"), lineSegmentList2, startingTimes2);


        List<LineSegmentInterface> lineSegmentList3 = new ArrayList<>();


        Map<Time, Integer> passengersL3 = new HashMap<>();
        passengersL3.put(new Time(5), 4);
        lineSegmentList3.add(new LineSegment(new TimeDiff(4),passengersL3, 5, new LineName("3"), stopB));

        Map<Time, Integer> passengersL32 = new HashMap<>();
        passengersL32.put(new Time(6), 5);
        lineSegmentList3.add(new LineSegment(new TimeDiff(1),passengersL32, 5, new LineName("3"), stopC));

        Map<Time, Integer> passengersL33 = new HashMap<>();
        passengersL33.put(new Time(11), 4);
        lineSegmentList3.add(new LineSegment(new TimeDiff(5),passengersL33, 5, new LineName("3"), stopD));

        Vector<Time> startingTimes3 = new Vector<>();
        startingTimes3.add(new Time(1));
        line3 = new Line(new LineName("3"),new StopName("A"), lineSegmentList3, startingTimes3);
    }

    @Test
    public void simpleLineUpdateReachableTest()
    {
        setUp();

        assertNull(stopB.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopB.getReachableAt().getValue1());
        assertNull(stopC.getReachableAt().getValue0());
        assertEquals(Optional.empty(),stopC.getReachableAt().getValue1());

        line1.updateReachable(new Time(1), new StopName("A"));
        assertEquals(8, stopB.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(), stopB.getReachableAt().getValue1().get());
        assertEquals(16, stopC.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(),stopC.getReachableAt().getValue1().get());

        setUp();

        assertNull(stopB.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopB.getReachableAt().getValue1());
        assertNull(stopC.getReachableAt().getValue0());
        assertEquals(Optional.empty(),stopC.getReachableAt().getValue1());

        line1.updateReachable(new Time(1), new StopName("B"));

        assertNull(stopB.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopB.getReachableAt().getValue1());
        assertEquals(16, stopC.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(),stopC.getReachableAt().getValue1().get());

        setUp();

        assertNull(stopB.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopB.getReachableAt().getValue1());
        assertNull(stopC.getReachableAt().getValue0());
        assertEquals(Optional.empty(),stopC.getReachableAt().getValue1());

        line1.updateReachable(new Time(1), new StopName("C"));

        assertNull(stopB.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopB.getReachableAt().getValue1());
        assertNull(stopC.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopC.getReachableAt().getValue1());
        assertEquals(17, stopD.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(),stopD.getReachableAt().getValue1().get());
    }

    @Test
    public void moreLinesUpdateReachableTest()
    {
        setUp();

        line1.updateReachable(new Time(1), new StopName("A"));
        line2.updateReachable(new Time(1), new StopName("A"));

        assertNull(stopA.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopA.getReachableAt().getValue1());

        assertEquals(8, stopB.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(), stopB.getReachableAt().getValue1().get());
        assertEquals(17, stopD.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(),stopD.getReachableAt().getValue1().get());

        assertEquals(11, stopC.getReachableAt().getValue0().getTime());
        assertEquals(line2.getName(), stopC.getReachableAt().getValue1().get());

        setUp();

        line2.updateReachable(new Time(1), new StopName("A"));
        line1.updateReachable(new Time(1), new StopName("A"));

        assertNull(stopA.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopA.getReachableAt().getValue1());

        assertEquals(8, stopB.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(), stopB.getReachableAt().getValue1().get());
        assertEquals(17, stopD.getReachableAt().getValue0().getTime());
        assertEquals(line1.getName(),stopD.getReachableAt().getValue1().get());

        assertEquals(11, stopC.getReachableAt().getValue0().getTime());
        assertEquals(line2.getName(), stopC.getReachableAt().getValue1().get());
    }

    @Test
    public void testBlockedLineUpdateReachable()
    {
        setUp();
        line3.updateReachable(new Time(1), new StopName("A"));

        assertEquals(5, stopB.getReachableAt().getValue0().getTime());
        assertEquals(line3.getName(), stopB.getReachableAt().getValue1().get());

        assertNull(stopC.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopC.getReachableAt().getValue1());
        assertNull(stopD.getReachableAt().getValue0());
        assertEquals(Optional.empty(), stopD.getReachableAt().getValue1());

        setUp();
        line3.updateReachable(new Time(1), new StopName("C"));
        assertEquals(11, stopD.getReachableAt().getValue0().getTime());
        assertEquals(line3.getName(), stopD.getReachableAt().getValue1().get());
    }

    @Test
    public void testUpdateCapacity()
    {
        setUp();
        StopName prev = line1.updateCapacityAndGetPreviousStop(new StopName("D"), new Time(17));

        assertEquals(Integer.valueOf(9), lineSegment3.getPassengers(new Time(17)));
        assertEquals(new StopName("C"), prev);

        prev=line1.updateCapacityAndGetPreviousStop(prev, new Time(16));

        assertEquals(Integer.valueOf(6), lineSegment2.getPassengers(new Time(16)));
        assertEquals(new StopName("B"), prev);

        prev=line1.updateCapacityAndGetPreviousStop(prev, new Time(8));

        assertEquals(Integer.valueOf(5), lineSegment1.getPassengers(new Time(8)));
        assertEquals(new StopName("A"), prev);
    }
}
