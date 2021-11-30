import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import java.util.*;
import java.sql.*;

public class DatabaseLinesFactory implements LinesFactoryInterface{

    private Map<LineName, List<LineSegment>> lineSegments;
    private StopsInterface stops;
    private DatabaseManipulatorInterface db;

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

        List<LineSegment> lineSegments = new ArrayList<>();
        int numOfSegments = db.getNumOfSegments(lineName.getLineName());
        for (int i = 0; i < numOfSegments ;i++)
        {
            lineSegments.add(null);
        }

        this.lineSegments.put(lineName, lineSegments);
        return new Line(lineData.getValue0(), lineData.getValue1(), lineSegments, startingTimes);
    }

    @Override
    public void createSegment(LineName lineName, int index)
    {
        if(lineSegments.get(lineName).get(index) != null) return;

        Triplet<TimeDiff,Integer,StopName> segmentData = db.getSegmentToLine(lineName.getLineName(), index);

        Map<Time, Integer> numOfPassengers = db.getPassengersToSegment(lineName.getLineName(), index);

        this.lineSegments.get(lineName).set(index, new LineSegment(segmentData.getValue0(), numOfPassengers,segmentData.getValue1(), lineName,
                        new ProxyStop(stops, segmentData.getValue2())));
    }

    @Override
    public void clearBuffer() {
        lineSegments = new HashMap<>();
        // TODO: zistit ci aj stops
    }
}
