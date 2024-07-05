package lk.ijse.repository;


import lk.ijse.db.DbConnection;
import lk.ijse.model.Customer;
import lk.ijse.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String E_id = resultSet.getString(1);
            String E_name = resultSet.getString(2);
            String E_contact = resultSet.getString(3);
            String E_time = resultSet.getString(4);

            Employee employee = new Employee(E_id, E_name, E_contact, E_time);
            employeeList.add(employee);
        }
        return employeeList;
    }


    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET E_name = ?, E_contact = ?, E_time = ? WHERE E_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(4, employee.getE_id());
        pstm.setObject(1, employee.getE_name());
        pstm.setObject(2, employee.getE_Contact());
        pstm.setObject(3, employee.getE_time());

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, employee.getE_id());
        pstm.setObject(2, employee.getE_name());
        pstm.setObject(3, employee.getE_Contact());
        pstm.setObject(4, employee.getE_time());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static Employee searchById(String E_id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE E_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, E_id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String E_name = resultSet.getString(2);
            String E_contact = resultSet.getString(3);
            String E_time = resultSet.getString(4);

            employee = new Employee(id, E_name, E_contact, E_time);
        }
        return employee;
    }

    public static boolean delete(String E_id) throws SQLException {
        String sql = "DELETE FROM employee WHERE E_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, E_id);

        return pstm.executeUpdate() > 0;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT E_id FROM employee ORDER BY CAST(SUBSTRING(E_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}