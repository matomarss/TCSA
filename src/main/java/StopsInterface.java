import org.javatuples.Pair;

import java.util.Map;
import java.util.Optional;
import java.util.Vector;

public interface StopsInterface
{
    Optional<Pair<StopName, Time>> earliestReachableStopAfterTime(Time time);

    boolean setStartingStop(StopName stopName, Time time);

    Vector<LineName> getLines(StopName stopName);

    Pair<Time,Optional<LineName>> getReachableAt(StopName stop);

    StopInterface getStop(StopName stopName);

    void clean();
}
