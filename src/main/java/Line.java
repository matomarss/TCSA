import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Vector;

public class Line implements LineInterface
{
    private LineName name;
    private Vector<Time> startingTimes;
    private StopName firstStop;
    private List<LineSegmentInterface> lineSegments;

    public Line(LineName name, StopName firstStop, List<LineSegmentInterface> lineSegments, Vector<Time> startingTimes)
    {
        this.name = name;
        this.firstStop = firstStop;
        this.lineSegments = lineSegments;
        this.startingTimes = startingTimes;
    }
    public void updateReachable(Time time, StopName stopName)
    {

        for (Time startingTime : startingTimes)
        {
            if(stopName.equals(firstStop))
            {
                if(startingTime.getTime() >= time.getTime())
                {
                    updateAfterFrom(startingTime, 0);
                }
                continue;
            }

            Time nextTime = startingTime;
            for (int i=0; i < lineSegments.size(); i++)
            {
                Pair<Time, StopName> nextStop = lineSegments.get(i).nextStop(nextTime);

                if(nextStop.getValue1().equals(stopName))
                {
                    if(nextStop.getValue0().getTime() >= time.getTime())
                    {
                        updateAfterFrom(nextStop.getValue0(), i+1);
                    }
                    continue;
                }
                nextTime = nextStop.getValue0();
            }
        }
    }

    private void updateAfterFrom(Time timeAtFrom, int segmentAfterFrom) {
        Time nextTime = timeAtFrom;
        boolean canTravelThroughSegment;
        for (int i = segmentAfterFrom; i < lineSegments.size(); i++) {
            Triplet<Time, StopName, Boolean> nextStop = lineSegments.get(i).nextStopAndUpdateReachable(nextTime);
            nextTime = nextStop.getValue0();

            canTravelThroughSegment = nextStop.getValue2();

            if (!canTravelThroughSegment) break;
        }
    }

    public StopName updateCapacityAndGetPreviousStop(StopName stop, Time time)
    {
        if(firstStop.equals(stop)) return null;

        for (Time startTime: startingTimes)
        {
            StopName previousStop = firstStop;
            Time previousTime = startTime;
            for (int i=0; i < lineSegments.size(); i++)
            {
                Pair<Time, StopName> nextStop = lineSegments.get(i).nextStop(previousTime);
                if(nextStop.getValue0().equals(time) && nextStop.getValue1().equals(stop))
                {
                    lineSegments.get(i).incrementNumberOfPassengers(time);
                    return previousStop;
                }
                previousStop = nextStop.getValue1();
                previousTime = nextStop.getValue0();
            }
        }

        return null;
    }
    @Override
    public LineName getName()
    {
        return name;
    }
}
