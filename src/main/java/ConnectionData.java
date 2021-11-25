import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectionData
{
    List<Triplet<StopName,Time, Optional<LineName>>> route = new ArrayList<>();

    public void addToRoute(StopName stopName, Time time, Optional<LineName> lineName)
    {
        route.add(new Triplet<>(stopName,time,lineName));
    }
}
