import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;
import java.util.Optional;

public class LineSegment
{
    private TimeDiff timeToNextStop;
    private Map<Time, Integer> numberOfPassengers;
    private int  capacity; // je to dobre?
    private LineName lineName;
    private Stop nextStop;

    public Pair<Time, StopName> nextStop(Time startTime)
    {
        return new Pair<>(new Time(startTime.getTime()+timeToNextStop.getTime()), nextStop.getName());
    }

    public Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime)
    {
        boolean hasSeatAvailable = false;
        if(numberOfPassengers.get(startTime) < capacity)
        {
            nextStop.updateReachableAt(new Time(startTime.getTime()+timeToNextStop.getTime()), Optional.of(lineName));
            hasSeatAvailable = true;
        }
        return new Triplet<>(new Time(startTime.getTime()+timeToNextStop.getTime()), nextStop.getName(), hasSeatAvailable);
    }

    public void incrementNumberOfPassengers(Time startTime)
    {

    }
}
