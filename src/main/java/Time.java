import java.util.Objects;

public class Time implements Comparable<Time>
{
    private int time;

    public Time(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time1 = (Time) o;
        return time == time1.time;
    }

    @Override
    public int hashCode() {
        return Objects.hash(time);
    }

    @Override
    public String toString() {
        return Integer.toString(time);
    }


    @Override
    public int compareTo(Time o) {
        return time-o.getTime();
    }
}
