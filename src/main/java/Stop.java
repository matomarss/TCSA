import org.javatuples.Pair;

import java.util.Optional;
import java.util.Vector;

public class Stop implements StopInterface
{
    private StopName name;
    private Time reachableAt = null;
    private Optional<LineName> reachableVia = Optional.empty();
    private Vector<LineName> lines;

    public Stop(StopName name, Vector<LineName> lines) {
        this.name = name;
        this.lines = new Vector<>(lines);
    }

    public void updateReachableAt(Time time, Optional<LineName> line)
    {
        if(reachableAt != null && reachableAt.getTime() <= time.getTime()) return;


        reachableAt = time;
        reachableVia = line;
    }

    public Pair<Time, Optional<LineName>> getReachableAt()
    {
        return new Pair<>(reachableAt, reachableVia);
    }

    public Vector<LineName> getLines()
    {
        return lines;
    }

    public StopName getName() {
        return name;
    }
}
