import org.javatuples.Pair;
import org.javatuples.Triplet;

public interface LineInterface
{
    void updateReachable(Time time, StopName stopName);

    StopName updateCapacityAndGetPreviousStop(StopName stop, Time time);

}
