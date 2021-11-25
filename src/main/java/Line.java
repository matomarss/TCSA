import com.j256.ormlite.table.DatabaseTable;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.List;
import java.util.Vector;

@DatabaseTable(tableName = "lines")
public class Line
{
    private LineName name;
    private Vector<Time> startingTimes;
    private StopName firstStop;
    private List<LineSegment> lineSegments;

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
            segmentAfterFrom = 1;
        }
        else
        {
            for (Time startingTime : startingTimes)
            {
                Time nextTime = startingTime;
                for (int i=0; i < lineSegments.size(); i++)
                {
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

            for (int i = segmentAfterFrom; i < lineSegments.size(); i++)
            {
                Triplet<Time, StopName, Boolean> nextStop = lineSegments.get(i).nextStopAndUpdateReachable(nextTime); // TODO: co s boolom?
                nextTime = nextStop.getValue0();
            }
        }
    }
    public void updateCapacityAndGetPreviousStop(StopName stop, Time time)
    {

    }
}
