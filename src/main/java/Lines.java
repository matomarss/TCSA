import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Lines {
    private Map<LineName,Line> lines; // pridat lazy loading

    public void updateReachable(Vector<LineName> lines, StopName stopName, Time time)
    {
        for (LineName lineName: lines)
        {
            Line line = null;
            if(this.lines.containsKey(lineName))
            {
                line = this.lines.get(lineName);
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
