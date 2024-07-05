package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventRepo {

    public static boolean save(Event event) throws SQLException {
        String sql = "INSERT INTO event VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, event.getE_Code());
        pstm.setObject(2, event.getDescription());
        pstm.setObject(3, event.getType());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String E_Code) throws SQLException {
        String sql = "DELETE FROM event WHERE E_Code = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, E_Code);

        return pstm.executeUpdate() > 0;

    }


    public static boolean update(Event event) throws SQLException {
        String sql = "UPDATE event SET Type = ?, Description = ? WHERE E_code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(3, event.getE_Code());
        pstm.setObject(2, event.getDescription());
        pstm.setObject(1, event.getType());

        return pstm.executeUpdate() > 0;

    }

    public static Event searchByCode(String E_Code) throws SQLException {
        String sql = "SELECT * FROM event WHERE E_Code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, E_Code);
        ResultSet resultSet = pstm.executeQuery();

        Event event = null;

        if (resultSet.next()) {
            String e_Code = resultSet.getString(1);
            String  Description= resultSet.getString(2);
            String Type = resultSet.getString(3);


            event = new Event(e_Code, Description, Type);
        }
        return event;
    }

    public static List<Event> getAll() throws SQLException {
        String sql = "SELECT * FROM event";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Event> eventList = new ArrayList<>();
        while (resultSet.next()) {
            String E_Code = resultSet.getString(1);
            String Description = resultSet.getString(2);
            String Type = resultSet.getString(3);

            Event event = new Event(E_Code, Description, Type);
            eventList.add(event);
        }
        return eventList;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT E_code FROM event ORDER BY CAST(SUBSTRING(E_code, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
    }

