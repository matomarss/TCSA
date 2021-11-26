import org.javatuples.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

public class ConnectionSearch {
    private Stops stops;
    private Lines lines;

    public ConnectionData search(StopName from, StopName to, Time time)
    {
        ConnectionData route = new ConnectionData();
        stops.setStartingStop(from, time);


        Vector<LineName> fromLines = stops.getLines(from);
        lines.updateReachable(fromLines, from, time);

        Time nextEarliestStopTime = time;
        Optional<Pair<StopName, Time>> earliestNext = stops.earliestReachableStopAfterTime(nextEarliestStopTime);
        while(earliestNext.isPresent())
        {
            StopName nextEarliestStopName = earliestNext.get().getValue0();
            nextEarliestStopTime = earliestNext.get().getValue1();
            Vector<LineName> nextEarliestStopLines = stops.getLines(nextEarliestStopName);

            if(nextEarliestStopName.equals(to)) break;

            lines.updateReachable(nextEarliestStopLines, nextEarliestStopName, nextEarliestStopTime); // je mozne ze update reachable je zle?

            earliestNext = stops.earliestReachableStopAfterTime(nextEarliestStopTime);
        }

        StopName prev = to;
        while(!prev.equals(from))
        {
            Pair<Time,Optional<LineName>> at = stops.getReachableAt(prev);
            route.addToRoute(prev, at.getValue0(),at.getValue1());
            if(at.getValue1().isPresent()) prev = lines.updateCapacityAndGetPreviousStop(at.getValue1().get(), prev, at.getValue0());
        }
        route.addToRoute(prev, time, Optional.empty());

        lines.clean();
        return null;
    }
}
