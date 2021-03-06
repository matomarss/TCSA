import org.javatuples.Pair;

import java.util.*;

public class Stops implements StopsInterface{
    private Map<StopName, StopInterface> stops;

    private StopsFactoryInterface stopsFactory;


    public Stops(StopsFactoryInterface stopsFactory)
    {
        this.stopsFactory = stopsFactory;
        stops = new HashMap<>();
    }

    @Override
    public Optional<Pair<Vector<StopName>, Time>> earliestReachableStopAfterTime(Time boundaryTime)
    {
        Vector<StopName> earliestAfter = new Vector<>();
        Time earliestAfterTime = new Time(Integer.MAX_VALUE);

        for (Map.Entry<StopName,StopInterface> e : stops.entrySet())
        {
            StopInterface stop = e.getValue();
            Time stopArrivalTime = stop.getReachableAt().getValue0();
            if(stopArrivalTime.getTime() > boundaryTime.getTime())
            {
                if(stopArrivalTime.getTime() < earliestAfterTime.getTime())
                {
                    earliestAfter = new Vector<>();

                    earliestAfterTime = stopArrivalTime;
                    earliestAfter.add(stop.getName());
                }
                else if(stopArrivalTime.getTime() == earliestAfterTime.getTime())
                {
                    earliestAfter.add(stop.getName());
                }
            }
        }

        if(earliestAfter.isEmpty()) return Optional.empty();
        else
        {
            return Optional.of(new Pair<>(earliestAfter, earliestAfterTime));
        }
    }

    @Override
    public boolean setStartingStop(StopName stopName, Time time)
    {
        StopInterface startingStop = getStopByName(stopName);

        startingStop.updateReachableAt(time, Optional.empty());

        return true;
    }

    @Override
    public Vector<LineName> getLines(StopName stopName)
    {
        StopInterface stop = getStopByName(stopName);

        return stop.getLines();
    }

    @Override
    public Pair<Time,Optional<LineName>> getReachableAt(StopName stop)
    {
        return getStopByName(stop).getReachableAt();
    }

    @Override
    public StopInterface getStop(StopName stopName) {
        return getStopByName(stopName);
    }

    @Override
    public void clean() {
        stops = new HashMap<>();
    }



    private StopInterface getStopByName(StopName stopName)
    {
        StopInterface stop;
        if(stops.containsKey(stopName)) stop = stops.get(stopName);
        else
        {
            stop = Load(stopName);
        }

        return stop;
    }
    private StopInterface Load(StopName stopName) {
        StopInterface stop = stopsFactory.getStopByName(stopName);
        stops.put(stopName, stop);

        return stop;
    }

}
