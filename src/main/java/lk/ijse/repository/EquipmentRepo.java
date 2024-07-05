package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Employee;
import lk.ijse.model.Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentRepo {
    public static boolean delete(String Eq_id) throws SQLException {
        String sql = "DELETE FROM equipment WHERE Eq_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, Eq_id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Equipment equipment) throws SQLException {
        String sql = "INSERT INTO equipment VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, equipment.getEq_id());
        pstm.setObject(2, equipment.getEq_name());
        pstm.setObject(3, equipment.getEq_qty());

        return pstm.executeUpdate() > 0;

    }

    public static Equipment searchById(String Eq_id) throws SQLException {
        String sql = "SELECT * FROM equipment WHERE Eq_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, Eq_id);
        ResultSet resultSet = pstm.executeQuery();

        Equipment equipment = null;

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String Eq_name = resultSet.getString(2);
            String Eq_qty = resultSet.getString(3);

            equipment = new Equipment(id, Eq_name, Eq_qty);
        }
        return equipment;
        
    }

    public static List<Equipment> getAll() throws SQLException {
        String sql = "SELECT * FROM equipment";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Equipment> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String Eq_id = resultSet.getString(1);
            String Eq_name = resultSet.getString(2);
            String Eq_qty = resultSet.getString(3);

            Equipment equipment = new Equipment(Eq_id, Eq_name, Eq_qty);
            employeeList.add(equipment);
        }
        return employeeList;
    }

    public static boolean update(Equipment equipment) throws SQLException {
        String sql = "UPDATE Equipment SET Eq_name = ?, Eq_qty = ? WHERE Eq_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(3, equipment.getEq_id());
        pstm.setObject(1, equipment.getEq_name());
        pstm.setObject(2, equipment.getEq_qty());

        return pstm.executeUpdate() > 0;

    }
    public static String currentId() throws SQLException {
        String sql = "SELECT Eq_id FROM equipment ORDER BY CAST(SUBSTRING(Eq_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
