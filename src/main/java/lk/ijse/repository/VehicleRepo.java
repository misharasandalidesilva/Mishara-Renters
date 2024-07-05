package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepo {
    public static boolean save(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicle VALUES(?, ?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, vehicle.getV_id());
        pstm.setObject(2, vehicle.getV_name());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE vehicle SET V_name = ? WHERE V_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(2, vehicle.getV_id());
        pstm.setObject(1, vehicle.getV_name());

        return pstm.executeUpdate() > 0;
    }

    public static Vehicle searchById(String vId) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE V_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, vId);
        ResultSet resultSet = pstm.executeQuery();

        Vehicle vehicle = null;

        if (resultSet.next()) {
            String V_id = resultSet.getString(1);
            String V_name = resultSet.getString(2);


           vehicle = new Vehicle(V_id, V_name);
        }
        return vehicle;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM vehicle WHERE V_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Vehicle> getAll() throws SQLException {
        String sql = "SELECT * FROM vehicle";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Vehicle> VehicleList = new ArrayList<>();
        while (resultSet.next()) {
            String V_id = resultSet.getString(1);
            String V_name = resultSet.getString(2);

            Vehicle vehicle = new Vehicle(V_id,V_name);
            VehicleList.add(vehicle);
        }
        return VehicleList;

    }

    public static String currentId() throws SQLException {
        String sql = "SELECT V_id FROM vehicle ORDER BY CAST(SUBSTRING(V_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
