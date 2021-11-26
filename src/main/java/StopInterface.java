import org.javatuples.Pair;

import java.util.Optional;
import java.util.Vector;

public interface StopInterface
{
    void updateReachableAt(Time time, Optional<LineName> line);

    Pair<Time, Optional<LineName>> getReachableAt();

    Vector<LineName> getLines();

    StopName getName();
}
