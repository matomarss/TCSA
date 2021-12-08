import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.HashMap;
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

    @Override
    public Pair<Time, StopName> nextStop(Time startTime)
    {
        return new Pair<>(new Time(startTime.getTime()+timeToNextStop.getTime()), nextStop.getName());
    }

    @Override
    public Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime)
    {
        boolean hasSeatAvailable = false;
        if(numberOfPassengers.get(new Time(startTime.getTime()+ timeToNextStop.getTime())) < capacity)
        {
            nextStop.updateReachableAt(new Time(startTime.getTime()+timeToNextStop.getTime()), Optional.of(lineName));
            hasSeatAvailable = true;
        }
        return new Triplet<>(new Time(startTime.getTime()+timeToNextStop.getTime()), nextStop.getName(), hasSeatAvailable);
    }

    @Override
    public void incrementNumberOfPassengers(Time time)
    {
        if(numberOfPassengers.get(time) >= capacity) throw new RuntimeException();

        numberOfPassengers.put(time, numberOfPassengers.get(time) + 1);
    }

    @Override
    public Integer getPassengers(Time time)
    {
        return numberOfPassengers.get(time);
    }

    @Override
    public Map<Time, Integer> getPassengers()
    {
        return new HashMap<>(numberOfPassengers);
    }
}
