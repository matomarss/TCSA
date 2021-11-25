import java.util.List;
import java.util.Vector;

public class ConnectionSearch {
    private Stops stops;
    private Lines lines;

    public ConnectionData search(StopName from, StopName to, Time time)
    {
        stops.setStartingStop(from, time);
        Vector<LineName> fromLines = stops.getLines(from);
        lines.updateReachable(fromLines, from, time);
        return null;
    }
}
