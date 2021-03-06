package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.dao.CategoriesDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesDBDAO implements CategoriesDAO {

    private static final String QUERY_INSERT_CATEGORIES = "INSERT INTO `couponsystem`.`categories` (`name`) VALUES (?);";
    private static final String QUERY_GET_ALL_CATEGORIES = "SELECT * FROM `couponsystem`.`categories`;";

    @Override
    public void addCategories() {
        Map<Integer, Object> map = new HashMap<>();
        for (int i = 0; i < Category.values().length; i++) {
            map.put(1, Category.values()[i].name());
            DBUtils.runQuery(QUERY_INSERT_CATEGORIES, map);
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_CATEGORIES);
//            for (int i = 0; i < Category.values().length; i++) {
//                statement.setString(1, Category.values()[i].name());
//                statement.execute();
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_CATEGORIES);
        try {
            while (resultSet.next()) {
                Category category = Category.valueOf(resultSet.getString(2));
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_CATEGORIES);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Category category = Category.valueOf(resultSet.getString(2));
//                categories.add(category);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
        return categories;
    }


}
