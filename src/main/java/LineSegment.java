import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;

public class LineSegment
{
    private TimeDiff timeToNextStop;
    private Map<Time, Integer> numberOfPassengers;
    private int capacity;
    private LineName lineName;
    private Stop nextStop;

    public Pair<Time, StopName> nextStop(Time startTime)
    {
        return null;
    }

    public Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime)
    {
        return null;
    }

    public void incrementNumberOfPassengers(Time startTime)
    {

    }
}
