public abstract class Tcsa
{
    protected ConnectionSearch connectionSearch;

    public void findAndReserve(StopName from, StopName to, Time time)
    {
        ConnectionData cd = connectionSearch.search(from, to, time);

        System.out.println(cd);
    }
}
