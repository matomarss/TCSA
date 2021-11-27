public interface LinesFactoryInterface
{
    LineInterface getLineByName(LineName lineName);

    void createSegment(LineName lineName, int index);

    void clearBuffer();
}
