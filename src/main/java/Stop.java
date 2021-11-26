import org.javatuples.Pair;

import java.util.Optional;
import java.util.Vector;

public class Stop
{
    private StopName name;
    private Time reachableAt = null;
    private Optional<LineName> reachableVia = Optional.empty();
    private Vector<LineName> lines; // ako sa tu dostanú lines?

    public Stop(StopName name) {
        this.name = name;
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
