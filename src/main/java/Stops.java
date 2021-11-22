import org.javatuples.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class Stops {
    private List<Stop> stops;

    public Optional<Pair<StopName, Time>> earliestReachableStopAfterTime(Time time)
    {
        return Optional.empty();
    }

    public boolean setStartingStop(StopName stop, Time time)
    {
        return  true;
    }

    public Vector<LineName> getLines(StopName stop)
    {
        return null;
    }

    public Pair<Time,LineName> getReachableStop(StopName stop)
    {
        return null;
    }

}
