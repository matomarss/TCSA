import org.javatuples.Pair;

import java.util.Optional;
import java.util.Vector;

public class ProxyStop implements StopInterface
{
    private StopsInterface stops;
    private StopName stopName;

    public ProxyStop(StopsInterface stops, StopName stopName) {
        this.stops = stops;
        this.stopName = stopName;
    }

    @Override
    public void updateReachableAt(Time time, Optional<LineName> line)
    {
        stops.getStop(stopName).updateReachableAt(time, line);
    }

    @Override
    public Pair<Time, Optional<LineName>> getReachableAt() {
        return stops.getStop(stopName).getReachableAt();
    }

    @Override
    public Vector<LineName> getLines() {
        return stops.getStop(stopName).getLines();
    }

    @Override
    public StopName getName() {
        return stopName;
    }
}
