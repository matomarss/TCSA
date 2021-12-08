import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Lines implements LinesInterface {
    private Map<LineName,LineInterface> lines;
    private final LinesFactoryInterface linesFactory;

    public Lines(LinesFactoryInterface linesFactory) {
        lines = new HashMap<>();
        this.linesFactory = linesFactory;
    }

    @Override
    public void updateReachable(Vector<LineName> onLines, StopName stopName, Time time)
    {
        for (LineName lineName: onLines)
        {
            getLineByName(lineName).updateReachable(time, stopName);
        }
    }

    @Override
    public StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stopName, Time time)
    {
        return getLineByName(lineName).updateCapacityAndGetPreviousStop(stopName,time);
    }

    @Override
    public void clean()
    {
        lines = new HashMap<>();
        linesFactory.clean();
    }

    private LineInterface getLineByName(LineName lineName)
    {
        LineInterface line;

        if(lines.containsKey(lineName))
        {
            line = lines.get(lineName);
        }
        else {
            line = Load(lineName);
        }
        return line;
    }
    private LineInterface Load(LineName lineName) {
        LineInterface line = linesFactory.getLineByName(lineName);
        lines.put(lineName, line);

        return line;
    }
}
