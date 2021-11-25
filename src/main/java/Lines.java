import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Lines {
    private Map<LineName,Line> lines; // pridat lazy loading

    public void updateReachable(Vector<LineName> onLines, StopName stopName, Time time)
    {
        for (LineName lineName: onLines)
        {
            Line line = null;
            if(lines.containsKey(lineName))
            {
                line = lines.get(lineName);
            }

            // lazy loading

            line.updateReachable(time,stopName);
        }
    }
    public StopName updateCapacityAndGetPreviousStop(LineName line, StopName stop, Time time)
    {
        return null;
    }

    public void clean()
    {

    }
}
