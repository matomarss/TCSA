import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Optional;

public interface LineSegmentInterface
{
    Pair<Time, StopName> nextStop(Time startTime);

    Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime);

    void incrementNumberOfPassengers(Time startTime);
}
