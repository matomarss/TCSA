import java.util.Vector;

public interface LinesInterface
{
    void updateReachable(Vector<LineName> onLines, StopName stopName, Time time);

    StopName updateCapacityAndGetPreviousStop(LineName lineName, StopName stopName, Time time);

    void clean();
}
