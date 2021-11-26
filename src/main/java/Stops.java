import org.javatuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

public class Stops {
    private Map<StopName, Stop> stops;

    public Optional<Pair<StopName, Time>> earliestReachableStopAfterTime(Time time)
    {
        Stop earliestAfter = null;
        Time earliestTimeAfter = new Time(Integer.MAX_VALUE);

        for (Map.Entry<StopName,Stop> e : stops.entrySet())
        {
            Time stopTime = e.getValue().getReachableAt().getValue0();
            if(stopTime.getTime() > time.getTime()) // nema byt aj rovny?
            {
                if(stopTime.getTime() < earliestTimeAfter.getTime())
                {
                    earliestTimeAfter = stopTime;
                    earliestAfter = e.getValue();
                }
            }
        }

        if(earliestAfter == null) return Optional.empty();
        else
        {
            return Optional.of(new Pair<>(earliestAfter.getName(), earliestTimeAfter));
        }
    }

    public boolean setStartingStop(StopName stopName, Time time)
    {
        Stop startingStop;

        if(stops.containsKey(stopName)) startingStop = stops.get(stopName);
        else
        {
            // asi nie to, co tu ma byt - skor lazy loading?
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

    public Pair<Time,Optional<LineName>> getReachableAt(StopName stop)
    {
        return stops.get(stop).getReachableAt();
    }

}
