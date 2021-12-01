import org.javatuples.Pair;

import java.util.Optional;
import java.util.Vector;

public class ConnectionSearch {
    private final StopsInterface stops;
    private final LinesInterface lines;

    public ConnectionSearch(StopsInterface stops, LinesInterface lines) {
        this.stops = stops;
        this.lines = lines;
    }

    public ConnectionData search(StopName from, StopName to, Time time)
    {
        ConnectionData connectionData = new ConnectionData();
        stops.setStartingStop(from, time);


        Vector<LineName> fromLines = stops.getLines(from);
        lines.updateReachable(fromLines, from, time);

        Time nextEarliestStopTime = time;
        Optional<Pair<Vector<StopName>, Time>> earliestNext = stops.earliestReachableStopAfterTime(nextEarliestStopTime);
        while(earliestNext.isPresent() && !connectionData.foundRoute())
        {
            Vector<StopName> nextEarliestStopNames = earliestNext.get().getValue0();
            nextEarliestStopTime = earliestNext.get().getValue1();

            for (StopName nextEarliestStopName :nextEarliestStopNames)
            {
                if(nextEarliestStopName.equals(to))
                {
                    connectionData.setFoundRoute(true);
                    break;
                }

                Vector<LineName> nextEarliestStopLines = stops.getLines(nextEarliestStopName);

                lines.updateReachable(nextEarliestStopLines, nextEarliestStopName, nextEarliestStopTime);
            }

            earliestNext = stops.earliestReachableStopAfterTime(nextEarliestStopTime);
        }

        if(connectionData.foundRoute())
        {
            StopName prev = to;
            while(!prev.equals(from))
            {
                Pair<Time,Optional<LineName>> at = stops.getReachableAt(prev);
                connectionData.addToRoute(prev, at.getValue0(),at.getValue1());
                if(!prev.equals(from)) prev = lines.updateCapacityAndGetPreviousStop(at.getValue1().get(), prev, at.getValue0());
            }
            connectionData.addToRoute(prev, time, Optional.empty());
        }

        lines.clean();
        stops.clean();

        return connectionData;
    }
}
