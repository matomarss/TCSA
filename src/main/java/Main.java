import org.javatuples.Triplet;

import java.util.Optional;

public class Main
{

    public static void main(String[] args) {

        Tcsa tcsa = new TcsaInMemory();

        tcsa.findAndReserve(new StopName("B12"), new StopName("C12"), new Time(1));

        tcsa = new TcsaInDatabase();

        tcsa.findAndReserve(new StopName("B12"), new StopName("C12"), new Time(1));
    }
}
