import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class InMemoryStopsFactory implements StopsFactoryInterface
{
    //private Map<StopName, Stop> stops;
    private List<Pair<StopName, Vector<LineName>>> stops;


    public InMemoryStopsFactory(List<Pair<StopName, Vector<LineName>>> stops) {
        this.stops = stops;
    }

    /*public InMemoryStopsFactory(List<Stop> stops) {
        this.stops = new HashMap<>();
        for (Stop stop : stops) {
            this.stops.put(stop.getName(), stop);
        }
    }*/

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
