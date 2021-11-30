import com.j256.ormlite.stmt.query.In;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DatabaseManipulator implements DatabaseManipulatorInterface
{

    private Connection connect()  {
        // SQLite connection string

        String url = "jdbc:sqlite:TCSA.db";
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage()+" Connection was not established");
        }
        return conn;
    }

    @Override
    public Vector<LineName> getLinesToStop(String stopName) {
        Connection conn = connect();
        PreparedStatement selectLines = null;

        if (conn == null) return null;

        Vector<LineName> toReturn = null;
        try {
            selectLines = conn.prepareStatement("select * from stop where name = ?");
            selectLines.setString(1, stopName);
            ResultSet rs = selectLines.executeQuery();

            toReturn = new Vector<>();
            while(rs.next())
            {
                toReturn.add(new LineName(rs.getString("line_name")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " nothing loaded");
        } finally {
            try {
                if (selectLines != null) selectLines.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return toReturn;
    }

    @Override
    public Pair<LineName, StopName> getLine(String lineName) {
        Connection conn = connect();
        PreparedStatement selectLine = null;

        if (conn == null) return null;

        Pair<LineName, StopName> toReturn = null;
        try {
            selectLine = conn.prepareStatement("select * from line where name = ?");
            selectLine.setString(1, lineName);
            ResultSet rs = selectLine.executeQuery();

            toReturn = new Pair<>(new LineName(rs.getString("name")), new StopName(rs.getString("first_stop")));
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " nothing loaded");
        } finally {
            try {
                if (selectLine != null) selectLine.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return toReturn;
    }

    @Override
    public Vector<Time> getTimesToLine(String lineName) {
        Connection conn = connect();
        PreparedStatement selectTimes = null;

        if (conn == null) return null;

        Vector<Time> toReturn = null;
        try {
            selectTimes = conn.prepareStatement("select * from start_times where name = ?");
            selectTimes.setString(1, lineName);
            ResultSet rs = selectTimes.executeQuery();

            toReturn = new Vector<>();
            while(rs.next())
            {
                toReturn.add(new Time(rs.getInt("time")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " nothing loaded");
        } finally {
            try {
                if (selectTimes != null) selectTimes.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return toReturn;
    }

    @Override
    public int getNumOfSegments(String lineName) {
        Connection conn = connect();
        PreparedStatement selectTimes = null;

        if (conn == null) return -1;

        int toReturn = -1;
        try {
            selectTimes = conn.prepareStatement("select count(order) as num from segment where line_name = ?"); //TODO otestovať
            selectTimes.setString(1, lineName);
            ResultSet rs = selectTimes.executeQuery();

            toReturn = rs.getInt("num");
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " nothing loaded");
        } finally {
            try {
                if (selectTimes != null) selectTimes.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return toReturn;
    }

    @Override
    public Triplet<TimeDiff, Integer, StopName> getSegmentToLine(String lineName, int index) {
        Connection conn = connect();
        PreparedStatement selectLine = null;

        if (conn == null) return null;

        Triplet<TimeDiff, Integer, StopName> toReturn = null;
        try {
            selectLine = conn.prepareStatement("select time_diff, capacity, next_stop from segment where line_name = ? and order = ?");
            selectLine.setString(1, lineName);
            selectLine.setInt(2, index);
            ResultSet rs = selectLine.executeQuery();

            toReturn = new Triplet<>(new TimeDiff(rs.getInt("time_diff")), rs.getInt("capacity"), new StopName(rs.getString("next_stop")));
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " nothing loaded");
        } finally {
            try {
                if (selectLine != null) selectLine.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return toReturn;
    }

    @Override
    public Map<Time, Integer> getPassengersToSegment(String lineName, int index) {
        Connection conn = connect();
        PreparedStatement selectLine = null;

        if (conn == null) return null;

        Map<Time, Integer> toReturn;
        try {
            selectLine = conn.prepareStatement("select time, passengers_num from passengers where line_name = ? and order = ?");
            selectLine.setString(1, lineName);
            selectLine.setInt(2, index);
            ResultSet rs = selectLine.executeQuery();

            toReturn = new HashMap<>();
            while(rs.next())
            {
                toReturn.put(new Time(rs.getInt("time")), rs.getInt("passengers_num"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + " nothing loaded");
            toReturn = null;
        } finally {
            try {
                if (selectLine != null) selectLine.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return toReturn;
    }
}
