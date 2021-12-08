public interface LineInterface
{
    void updateReachable(Time time, StopName stopName);

    StopName updateCapacityAndGetPreviousStop(StopName stop, Time time);

    LineName getName();
}
