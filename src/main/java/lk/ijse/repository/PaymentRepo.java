package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Orders;
import lk.ijse.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET Description = ?, Date = ?, O_id = ? WHERE P_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(4, payment.getP_id());
        pstm.setObject(2, payment.getDate());
        pstm.setObject(1, payment.getDescription());
        pstm.setObject(3, payment.getO_id());


        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String P_id) throws SQLException {
        String sql = "DELETE FROM payment WHERE P_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, P_id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES(?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, payment.getP_id());
        pstm.setObject(2, payment.getDate());
        pstm.setObject(3, payment.getDescription());
        pstm.setObject(4, payment.getO_id());


        return pstm.executeUpdate() > 0;
    }

    public static Payment searchById(String P_id) throws SQLException {
        String sql = "SELECT * FROM payment WHERE P_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, P_id);
        ResultSet resultSet = pstm.executeQuery();

        Payment payment = null;

        if (resultSet.next()) {
            String PId = resultSet.getString(1);
            String Date = resultSet.getString(2);
            String Description = resultSet.getString(3);
            String O_id = resultSet.getString(4);


            payment = new Payment(PId, Date, Description, O_id);
        }
        return payment;
    }

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payment";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> PaymentList = new ArrayList<>();
        while (resultSet.next()) {
            String P_id = resultSet.getString(1);
            String Date = resultSet.getString(2);
            String Description = resultSet.getString(3);
            String O_id = resultSet.getString(4);


            Payment payment = new Payment(P_id, Date, Description, O_id);
            PaymentList.add(payment);
        }
        return PaymentList;


    }

    public static String currentId() throws SQLException {
        String sql = "SELECT P_id FROM payment ORDER BY CAST(SUBSTRING(P_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
