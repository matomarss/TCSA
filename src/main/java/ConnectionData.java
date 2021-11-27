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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = route.size()-1; i>=0; i--)
        {
            stringBuilder.append(route.get(i));
            stringBuilder.append(" ");
        }

        return  stringBuilder.toString();
    }
}
