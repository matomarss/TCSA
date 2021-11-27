import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Vector;

public interface LineInterface
{
    void updateReachable(Time time, StopName stopName);

    StopName updateCapacityAndGetPreviousStop(StopName stop, Time time);

    void register(LinesInterface lines);

    LineName getName();
}
