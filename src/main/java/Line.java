import com.j256.ormlite.table.DatabaseTable;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Vector;

public class Line implements LineInterface
{
    private LineName name;
    private Vector<Time> startingTimes;
    private StopName firstStop;
    private List<LineSegment> lineSegments;
    private LinesInterface lines;

    public Line(LineName name, StopName firstStop, List<LineSegment> lineSegments, Vector<Time> startingTimes)
    {
        this.name = name;
        this.firstStop = firstStop;
        this.lineSegments = lineSegments;
        this.startingTimes = startingTimes;
    }
    public void updateReachable(Time time, StopName stopName)
    {
        Time timeAtFrom = null;
        int segmentAfterFrom = 0;

        if(firstStop.equals(stopName))
        {
            for (Time startingTime:startingTimes) {
                if(startingTime.getTime() >= time.getTime())
                {
                    timeAtFrom = startingTime;
                    break;
                }
            }

            //if(timeAtFrom != null) lineSegments.get(0).nextStopAndUpdateReachable(timeAtFrom);
            segmentAfterFrom = 0;
        }
        else
        {
            for (Time startingTime : startingTimes)
            {
                Time nextTime = startingTime;
                for (int i=0; i < lineSegments.size(); i++)
                {
                    notify(i);
                    Pair<Time, StopName> nextStop = lineSegments.get(i).nextStop(nextTime);

                    if(nextStop.getValue1().equals(stopName))
                    {
                        if(nextStop.getValue0().getTime() >= time.getTime())
                        {
                            timeAtFrom = nextStop.getValue0();
                            segmentAfterFrom = i+1;
                        }
                        break; // predpokladame, že kazda line obsahuje kazdu zastavku len raz a teda sme na nej nenasli/nasli autobus, ktory ide cez hladanu stanicu v case, ktory sa da stihnut
                    }
                    nextTime = nextStop.getValue0();
                }

                if(timeAtFrom != null) break; //uz mame spravny autobus a najblizsi cas na zastavku
            }
        }

        if(timeAtFrom != null)
        {
            Time nextTime = timeAtFrom;
            boolean canTravelThroughSegment = true;
            for (int i = segmentAfterFrom; i < lineSegments.size(); i++)
            {
                notify(i);
                Triplet<Time, StopName, Boolean> nextStop = lineSegments.get(i).nextStopAndUpdateReachable(nextTime);
                nextTime = nextStop.getValue0();

                canTravelThroughSegment = nextStop.getValue2();

                if(!canTravelThroughSegment) break;
            }
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
                notify(i);
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

    public LineName getName()
    {
        return name;
    }
    @Override
    public void register(LinesInterface lines) {
        this.lines = lines;
    }

    public void notify(int i)
    {
        if(lines != null) lines.updateSegments(name, i);
    }
}
