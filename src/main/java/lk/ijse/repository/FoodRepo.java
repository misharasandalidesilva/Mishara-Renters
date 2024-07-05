package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Equipment;
import lk.ijse.model.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodRepo {
    public static boolean delete(String F_id) throws SQLException {
        String sql = "DELETE FROM food WHERE F_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, F_id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Food food) throws SQLException {
        String sql = "INSERT INTO food VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, food.getF_id());
        pstm.setObject(2, food.getF_qty());
        pstm.setObject(3, food.getDescription());

        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Food food) throws SQLException {
        String sql = "UPDATE food SET Description = ?, F_qty = ? WHERE F_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, food.getDescription());
        pstm.setObject(2, food.getF_qty());
        pstm.setObject(3, food.getF_id());
        System.out.println("food");

        return pstm.executeUpdate() > 0;

    }

    public static Food searchById(String F_id) throws SQLException {
        String sql = "SELECT * FROM food WHERE F_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, F_id);
        ResultSet resultSet = pstm.executeQuery();

        Food food = null;

        if (resultSet.next()) {
            String id = resultSet.getString(1);
            String F_qty = resultSet.getString(2);
            String Description = resultSet.getString(3);

            food = new Food(id, F_qty, Description);
        }
        return food;
    }

    public static List<Food> getAll() throws SQLException {
        String sql = "SELECT * FROM food";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Food> foodList = new ArrayList<>();
        while (resultSet.next()) {
            String F_id = resultSet.getString(1);
            String F_qty = resultSet.getString(2);
            String Description = resultSet.getString(3);

            Food food = new Food(F_id, F_qty, Description);
            foodList.add(food);
        }
        return foodList;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT F_id FROM food ORDER BY CAST(SUBSTRING(F_id, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
    }

