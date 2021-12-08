import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;

public class ProxyLineSegment implements LineSegmentInterface{

    private LinesFactoryInterface linesFactory;
    private LineSegmentInterface lineSegment;

    private LineName name;
    private int index;

    public ProxyLineSegment(LinesFactoryInterface linesFactory, LineName name, int index) {
        this.linesFactory = linesFactory;
        this.name = name;
        this.index = index;
        lineSegment = null;
    }

    @Override
    public Pair<Time, StopName> nextStop(Time startTime) {
        if(lineSegment == null) lineSegment = linesFactory.createSegment(name, index);
        return lineSegment.nextStop(startTime);
    }

    @Override
    public Triplet<Time, StopName, Boolean> nextStopAndUpdateReachable(Time startTime) {
        if(lineSegment == null) lineSegment = linesFactory.createSegment(name, index);
        return lineSegment.nextStopAndUpdateReachable(startTime);
    }

    @Override
    public void incrementNumberOfPassengers(Time time) {
        if(lineSegment == null) lineSegment = linesFactory.createSegment(name, index);
        lineSegment.incrementNumberOfPassengers(time);
    }

    @Override
    public Integer getPassengers(Time time) {
        if(lineSegment == null) lineSegment = linesFactory.createSegment(name, index);
        return lineSegment.getPassengers(time);
    }

    @Override
    public Map<Time, Integer> getPassengers() {
        if(lineSegment == null) lineSegment = linesFactory.createSegment(name, index);
        return lineSegment.getPassengers();
    }
}
