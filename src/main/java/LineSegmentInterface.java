import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;

public interface LineSegmentInterface
{
    Pair<Time, StopName> nextStop(Time startTime);

    Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime);

    void incrementNumberOfPassengers(Time time);

    Integer getPassengers(Time time);

    Map<Time, Integer> getPassengers();
}
