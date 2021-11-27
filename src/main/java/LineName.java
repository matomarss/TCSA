import java.util.Objects;

public class LineName
{
    private String lineName;

    public LineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineName lineName1 = (LineName) o;
        return lineName.equals(lineName1.lineName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineName);
    }

    @Override
    public String
    toString() {
        return lineName;
    }
}
