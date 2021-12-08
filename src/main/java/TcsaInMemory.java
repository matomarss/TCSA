import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;

import java.util.*;

public class TcsaInMemory extends  Tcsa
{
    public TcsaInMemory() {
        List<Pair<StopName, Vector<LineName>>> stopsList = new ArrayList<>();
        // *************STOPS**************
        stopsList.add(new Pair<>(new StopName("A1"), new Vector<>(Arrays.asList(new LineName("1")))));
        stopsList.add(new Pair<>(new StopName("B12"), new Vector<>(Arrays.asList(new LineName("1"),new LineName("2")))));
        stopsList.add(new Pair<>(new StopName("C12"), new Vector<>(Arrays.asList(new LineName("1"),new LineName("2")))));
        stopsList.add(new Pair<>(new StopName("Q2"), new Vector<>(Arrays.asList(new LineName("2")))));
        stopsList.add(new Pair<>(new StopName("A2"), new Vector<>(Arrays.asList(new LineName("2")))));
        Stops stops = new Stops(new InMemoryStopsFactory(stopsList));
        // **************STOPS-end*************



        // *****************************************LINES****************************************************
        List<Quartet<LineName, StopName, Vector<Time>, List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>>>> linesList = new ArrayList<>();

        // **************************LINE1*****************************
        List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>> lineSegments1 = new ArrayList<>();

        Map<Time, Integer> passengers11 = new HashMap<>();
        passengers11.put(new Time(6), 5);
        passengers11.put(new Time(7), 6);
        lineSegments1.add(new Quintet<>(new TimeDiff(5), passengers11, 7, new LineName("1"), new StopName("B12")));

        Map<Time, Integer> passengers12 = new HashMap<>();
        passengers12.put(new Time(11), 7);
        passengers12.put(new Time(12), 6);
        lineSegments1.add(new Quintet<>(new TimeDiff(5), passengers12, 8, new LineName("1"), new StopName("C12")));

        linesList.add(new Quartet<>(new LineName("1"), new StopName("A1"), new Vector<>(Arrays.asList(new Time(1), new Time(2))), lineSegments1));
        // **************************LINE1-end*****************************


        // **************************LINE2*****************************
        List<Quintet<TimeDiff, Map<Time, Integer>, Integer, LineName, StopName>> lineSegments2 = new ArrayList<>();

        Map<Time, Integer> passengers21 = new HashMap<>();
        passengers21.put(new Time(4), 5);
        passengers21.put(new Time(6), 6);
        lineSegments2.add(new Quintet<>(new TimeDiff(1), passengers21, 10, new LineName("2"), new StopName("B12")));

        Map<Time, Integer> passengers22 = new HashMap<>();
        passengers22.put(new Time(5), 8);
        passengers22.put(new Time(7), 6);
        lineSegments2.add(new Quintet<>(new TimeDiff(1), passengers22, 10, new LineName("2"), new StopName("Q2")));

        Map<Time, Integer> passengers23 = new HashMap<>();
        passengers23.put(new Time(7), 8);
        passengers23.put(new Time(9), 6);
        lineSegments2.add(new Quintet<>(new TimeDiff(2), passengers23, 10, new LineName("2"), new StopName("C12")));
        linesList.add(new Quartet<>(new LineName("2"), new StopName("A2"), new Vector<>(Arrays.asList(new Time(3), new Time(5))), lineSegments2));

        Lines lines = new Lines(new InMemoryLinesFactory(stops, linesList));
        // **************************LINE2-end*****************************
        // *****************************************LINES-end****************************************************

        connectionSearch = new ConnectionSearch(stops,lines);
    }
}
