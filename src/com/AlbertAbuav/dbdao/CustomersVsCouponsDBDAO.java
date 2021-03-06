package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CustomersVsCouponsDAO;
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

public class CustomersVsCouponsDBDAO implements CustomersVsCouponsDAO {

    private static final String QUERY_ADD_CUSTOMERS_VS_COUPONS = "INSERT INTO `couponsystem`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
    private static final String QUERY_DELETE_CUSTOMERS_VS_COUPONS = "DELETE FROM `couponsystem`.`customers_vs_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?);";
    private static final String QUERY_GET_ALL_CUSTOMERS_COUPONS = "SELECT * FROM `couponsystem`.`customers_vs_coupons` WHERE (`customer_id` = ?);";
    private static final String QUERY_GET_ALL_CUSTOMERS_COUPONS_BY_COUPON_ID = "SELECT * FROM `couponsystem`.`customers_vs_coupons` WHERE (`coupon_id` = ?);";

    @Override
    public void addCustomersVsCoupons(int customerID, int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        map.put(2, couponID);
        DBUtils.runQuery(QUERY_ADD_CUSTOMERS_VS_COUPONS, map);

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_ADD_CUSTOMERS_VS_COUPONS);
//            statement.setInt(1, customerID);
//            statement.setInt(2, couponID);
//            statement.execute();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
    }

    @Override
    public void deleteCustomersVsCoupons(int customerID, int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        map.put(2, couponID);
        DBUtils.runQuery(QUERY_DELETE_CUSTOMERS_VS_COUPONS, map);

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_CUSTOMERS_VS_COUPONS);
//            statement.setInt(1, customerID);
//            statement.setInt(2, couponID);
//            statement.execute();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
    }

    @Override
    public List<CustomersVsCoupons> getAllCustomersCoupons(int customerID) {
        List<CustomersVsCoupons> customersVsCoupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_CUSTOMERS_COUPONS, map);
        try {
            while (resultSet.next()) {
                int couponID = resultSet.getInt(2);
                CustomersVsCoupons temp = new CustomersVsCoupons(customerID, couponID);
                customersVsCoupons.add(temp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_CUSTOMERS_COUPONS);
//            statement.setInt(1, customerID);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                int couponID = resultSet.getInt(2);
//                CustomersVsCoupons temp = new CustomersVsCoupons(customerID, couponID);
//                customersVsCoupons.add(temp);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
        return customersVsCoupons;
    }

    @Override
    public List<CustomersVsCoupons> getAllCustomersCouponsByCouponId(int couponID) {
        List<CustomersVsCoupons> customersVsCoupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_CUSTOMERS_COUPONS_BY_COUPON_ID, map);
        try {
            while (resultSet.next()) {
                int customerID = resultSet.getInt(1);
                CustomersVsCoupons temp = new CustomersVsCoupons(customerID, couponID);
                customersVsCoupons.add(temp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

//        Connection connection = null;
//        try {
//            // Step 2
//            connection = ConnectionPool.getInstance().getConnection();
//            // Step 3
//            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_CUSTOMERS_COUPONS_BY_COUPON_ID);
//            statement.setInt(1, couponID);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                int customerID = resultSet.getInt(1);
//                CustomersVsCoupons temp = new CustomersVsCoupons(customerID, couponID);
//                customersVsCoupons.add(temp);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            // Step 5
//            ConnectionPool.getInstance().returnConnection(connection);
//        }
        return customersVsCoupons;
    }
}
