import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class InMemoryStopsFactory implements StopsFactoryInterface
{
    private List<Pair<StopName, Vector<LineName>>> stops;

    public InMemoryStopsFactory(List<Pair<StopName, Vector<LineName>>> stops) {
        this.stops = stops;
    }

    @Override
    public StopInterface getStopByName(StopName stopName)
    {
        for (Pair<StopName, Vector<LineName>>  stop : stops) {
            if(stop.getValue0().equals(stopName))
            {
                return new Stop(stop.getValue0(), stop.getValue1());
            }
        }
        return null;
    }
}
