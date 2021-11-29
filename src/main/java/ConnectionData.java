import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ConnectionData
{
    private final List<Triplet<StopName,Time, Optional<LineName>>> route = new ArrayList<>();
    private boolean foundRoute = false;

    public boolean foundRoute() {
        return foundRoute;
    }

    public void setFoundRoute(boolean foundRoute) {
        this.foundRoute = foundRoute;
    }

    public void addToRoute(StopName stopName, Time time, Optional<LineName> lineName)
    {
        route.add(new Triplet<>(stopName,time,lineName));
    }

    public List<Triplet<StopName,Time, Optional<LineName>>> getRoute()
    {
        List<Triplet<StopName,Time, Optional<LineName>>> toReturn = new ArrayList<>(route);
        Collections.reverse(toReturn);
        return toReturn;
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
