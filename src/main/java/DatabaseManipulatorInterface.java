import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.Map;
import java.util.Vector;

public interface DatabaseManipulatorInterface
{

    Vector<LineName> getLinesToStop(String stopName);

    Pair<LineName, StopName> getLine(String lineName);

    Vector<Time> getTimesToLine(String lineName);

    int getNumOfSegments(String lineName);

    Triplet<TimeDiff, Integer, StopName> getSegmentToLine(String lineName, int index);

    Map<Time, Integer> getPassengersToSegment(String lineName, int index);
}
