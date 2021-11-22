import org.javatuples.Pair;

import java.util.Optional;
import java.util.Vector;

public class Stop
{
    private StopName name;
    private Optional<Time> reachableAt;
    private Optional<LineName> reachableVia;
    private Vector<LineName> lines;

    public void updateReachableAt(Time time, Optional<LineName> line)
    {

    }

    public Pair<Time, LineName> getReachableAt()
    {
        return null;
    }

    public Vector<LineName> getLines()
    {
        return null;
    }
}
