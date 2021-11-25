import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

public class Stops {
    private Map<StopName, Stop> stops;
    private Stop startingStop;

    public Optional<Pair<StopName, Time>> earliestReachableStopAfterTime(Time time)
    {
        return Optional.empty();
    }

    public boolean setStartingStop(StopName stopName, Time time)
    {
        if(stops.containsKey(stopName)) startingStop = stops.get(stopName);
        else
        {
            // asi nie to, co tu ma byt
            startingStop = new Stop(stopName);
            stops.put(stopName,startingStop);
        }

        startingStop.updateReachableAt(time, Optional.empty());
        return  true;
    }

    public Vector<LineName> getLines(StopName stopName)
    {
        if(stops.containsKey(stopName)) return stops.get(stopName).getLines();
        else
        {
            // tu by mal byt nejaky lazyLoading
        }
        return null;
    }

    public Pair<Time,LineName> getReachableAt(StopName stop)
    {
        return null;
    }

}
