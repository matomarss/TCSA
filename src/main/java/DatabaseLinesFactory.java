import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.*;

public class DatabaseLinesFactory implements LinesFactoryInterface{

    private StopsInterface stops;
    private DatabaseManipulatorInterface db;
    private Map<LineName, List<LineSegmentInterface>> lineSegments;

    public DatabaseLinesFactory(StopsInterface stops, DatabaseManipulatorInterface database)
    {
        this.stops = stops;
        lineSegments = new HashMap<>();
        db = database;
    }

    @Override
    public LineInterface getLineByName(LineName lineName) {
        Pair<LineName, StopName> lineData = db.getLine(lineName.getLineName());
        Vector<Time> startingTimes = db.getTimesToLine(lineName.getLineName());

        List<LineSegmentInterface> lineSegments = new ArrayList<>();
        List<LineSegmentInterface> lineSegments2 = new ArrayList<>();
        int numOfSegments = db.getNumOfSegments(lineName.getLineName());
        for (int i = 0; i < numOfSegments ;i++)
        {
            lineSegments.add(new ProxyLineSegment(this, lineName, i));
            lineSegments2.add(null);
        }

        this.lineSegments.put(lineName, lineSegments2);
        return new Line(lineData.getValue0(), lineData.getValue1(), lineSegments, startingTimes);
    }

    @Override
    public LineSegmentInterface createSegment(LineName lineName, int index)
    {
        Triplet<TimeDiff,Integer,StopName> segmentData = db.getSegmentToLine(lineName.getLineName(), index);

        Map<Time, Integer> numOfPassengers = db.getPassengersToSegment(lineName.getLineName(), index);

        LineSegment toReturn = new LineSegment(segmentData.getValue0(), numOfPassengers,segmentData.getValue1(), lineName,
                new ProxyStop(stops, segmentData.getValue2()));

        this.lineSegments.get(lineName).set(index, toReturn);
        return toReturn;
    }

    @Override
    public void clean() {

        Vector<Triplet<LineName, Integer, Map<Time, Integer>>> segments = new Vector<>();
        for (LineName ln:lineSegments.keySet())
        {
            List<LineSegmentInterface> segmentsInLine = lineSegments.get(ln);
            for(int i = 0; i< segmentsInLine.size(); i++)
            {
                if(segmentsInLine.get(i) == null) continue;
                segments.add(new Triplet<>(ln,i,segmentsInLine.get(i).getPassengers()));
            }
        }

        db.updateSegments(segments);
        lineSegments = new HashMap<>();
    }
}
