package lk.ijse.repository;

import lk.ijse.db.DbConnection;
import lk.ijse.model.Food;
import lk.ijse.model.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuRepo {
    public static List<Menu> getAll() throws SQLException {
        String sql = "SELECT * FROM menu";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Menu> MenuList = new ArrayList<>();
        while (resultSet.next()) {
            String M_Code = resultSet.getString(1);
            String M_name = resultSet.getString(2);
            String Description = resultSet.getString(3);
            String F_id = resultSet.getString(4);
            String O_id = resultSet.getString(5);

            Menu menu = new Menu(M_Code, M_name, Description,F_id,O_id);
            MenuList.add(menu);
        }
        return MenuList;

    }

    public static boolean delete(String M_Code) throws SQLException {
        String sql = "DELETE FROM menu WHERE M_Code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, M_Code);

        return pstm.executeUpdate() > 0;
    }

    public static Menu searchByCode(String M_Code) throws SQLException {
        String sql = "SELECT * FROM menu WHERE M_Code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, M_Code);
        ResultSet resultSet = pstm.executeQuery();

        Menu menu = null;

        if (resultSet.next()) {
            String M_Code1 = resultSet.getString(1);
            String M_name = resultSet.getString(2);
            String Description = resultSet.getString(3);
            String F_id = resultSet.getString(4);
            String O_id = resultSet.getString(5);


            menu = new Menu(M_Code1, M_name, Description,F_id,O_id);
        }
        return menu;
    }

    public static boolean update(Menu menu) throws SQLException {
        String sql = "UPDATE menu SET Description = ?, M_name = ?, F_id = ?, O_id = ? WHERE M_Code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setObject(5, menu.getM_Code());
        pstm.setObject(2, menu.getM_name());
        pstm.setObject(1, menu.getDescription());
        pstm.setObject(3, menu.getF_id());
        pstm.setObject(4, menu.getO_id());


        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Menu menu) throws SQLException {
        String sql = "INSERT INTO menu VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, menu.getM_Code());
        pstm.setObject(2, menu.getM_name());
        pstm.setObject(3, menu.getDescription());
        pstm.setObject(4, menu.getF_id());
        pstm.setObject(5, menu.getO_id());

        return pstm.executeUpdate() > 0;
    }

    public static String currentId() throws SQLException {
        String sql = "SELECT M_code FROM menu ORDER BY CAST(SUBSTRING(M_code, 2) AS UNSIGNED) DESC LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
    }

