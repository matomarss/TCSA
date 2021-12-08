public interface LinesFactoryInterface
{
    LineInterface getLineByName(LineName lineName);

    LineSegmentInterface createSegment(LineName lineName, int index);

    void clean();


}
