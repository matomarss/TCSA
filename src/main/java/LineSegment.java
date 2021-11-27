import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;
import java.util.Optional;

public class LineSegment implements LineSegmentInterface
{
    private TimeDiff timeToNextStop;
    private Map<Time, Integer> numberOfPassengers;
    private int  capacity;
    private LineName lineName;
    private StopInterface nextStop;

    public LineSegment(TimeDiff timeToNextStop, Map<Time, Integer> numberOfPassengers, int capacity, LineName lineName, StopInterface nextStop) {
        this.timeToNextStop = timeToNextStop;
        this.numberOfPassengers = numberOfPassengers;
        this.capacity = capacity;
        this.lineName = lineName;
        this.nextStop = nextStop;
    }

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
        if(numberOfPassengers.get(startTime) >= capacity) throw new RuntimeException();

        numberOfPassengers.put(startTime, numberOfPassengers.get(startTime) + 1);
    }
}
