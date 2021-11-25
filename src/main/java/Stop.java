import org.javatuples.Pair;

import java.util.Optional;
import java.util.Vector;

public class Stop
{
    private StopName name;
    private Optional<Time> reachableAt;
    private Optional<LineName> reachableVia;
    private Vector<LineName> lines; // ako sa tu dostanú lines?

    public Stop(StopName name) {
        this.name = name;
    }

    public void updateReachableAt(Time time, Optional<LineName> line)
    {
        reachableAt = Optional.of(time);
        reachableVia = line;
    }

    public Pair<Time, LineName> getReachableAt()
    {
        return null;
    }

    public Vector<LineName> getLines()
    {
        return lines;
    }

    public StopName getName() {
        return name;
    }
}
