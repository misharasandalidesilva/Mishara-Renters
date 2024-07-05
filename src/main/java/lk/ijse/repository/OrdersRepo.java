package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Menu;
import lk.ijse.model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepo {
    public static boolean delete(String O_id) throws SQLException {
        String sql = "DELETE FROM orders WHERE O_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, O_id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Orders orders) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?, ?, ?, ?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, orders.getO_id());
        pstm.setObject(2, orders.getQty());
        pstm.setObject(3, orders.getStatus());
        pstm.setObject(4, orders.getC_id());
        pstm.setObject(5, orders.getE_Code());

        return pstm.executeUpdate() > 0;
    }

    public static List<Orders> getAll() throws SQLException {
        String sql = "SELECT * FROM orders";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Orders> OrderList = new ArrayList<>();
        while (resultSet.next()) {
            String O_id = resultSet.getString(1);
            String Qty = resultSet.getString(2);
            String Status = resultSet.getString(3);
            String C_id = resultSet.getString(4);
            String E_Code = resultSet.getString(5);

            Orders orders = new Orders(O_id, Qty, Status, C_id, E_Code);

            OrderList.add(orders);
        }
        return OrderList;
    }

    public static Orders searchById(String O_id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE O_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, O_id);
        ResultSet resultSet = pstm.executeQuery();

        Orders orders = null;

        if (resultSet.next()) {
            String OId = resultSet.getString(1);
            String Qty = resultSet.getString(2);
            String Status = resultSet.getString(3);
            String C_id = resultSet.getString(4);
            String E_Code = resultSet.getString(5);


            orders = new Orders(OId, Qty, Status, C_id, E_Code);
        }
        return orders;
    }

    public static boolean update(Orders orders) throws SQLException {
        String sql = "UPDATE orders SET Status = ?, Qty = ?,C_id = ?,E_Code = ? WHERE O_id = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(5, orders.getO_id());
        pstm.setObject(2, orders.getQty());
        pstm.setObject(1, orders.getStatus());
        pstm.setObject(3, orders.getC_id());
        pstm.setObject(4, orders.getE_Code());


        return pstm.executeUpdate() > 0;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT O_id FROM orders ORDER BY CAST(SUBSTRING(O_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}