import org.javatuples.Quartet;
import org.javatuples.Quintet;

import java.util.*;

public class InMemoryLinesFactory implements LinesFactoryInterface{

    private List<Quartet<LineName, StopName, Vector<Time>, List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>>>> lines;
    private StopsInterface stops;

    public InMemoryLinesFactory(StopsInterface stops, List<Quartet<LineName, StopName, Vector<Time>, List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>>>> lines)
    {
        this.stops = stops;
        this.lines = lines;
    }

    @Override
    public LineInterface getLineByName(LineName lineName) {
        for (Quartet<LineName, StopName, Vector<Time>, List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>>> line : lines) {
            if(line.getValue0().equals(lineName))
            {
                List<LineSegmentInterface> lineSegments = new ArrayList<>();
                int numOfSegments = line.getValue3().size();
                for (int i = 0; i < numOfSegments ;i++)
                {
                    lineSegments.add(new ProxyLineSegment(this, lineName, i));
                }
                return new Line(line.getValue0(), line.getValue1(), lineSegments, line.getValue2());
            }
        }
        return null;
    }

    @Override
    public LineSegmentInterface createSegment(LineName lineName, int index)
    {
        for (Quartet<LineName, StopName, Vector<Time>, List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>>> line : lines) {
            if(line.getValue0().equals(lineName))
            {
                Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName> lineSegment = line.getValue3().get(index);
                return new LineSegment(lineSegment.getValue0(), lineSegment.getValue1(), lineSegment.getValue2(),lineSegment.getValue3(),new ProxyStop(stops, lineSegment.getValue4()));
            }
        }
        return null;
    }

    @Override
    public void clean()
    {

    }
}
