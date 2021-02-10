package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CustomersVsCouponsDAO;
import com.AlbertAbuav.db.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomersVsCouponsDBDAO implements CustomersVsCouponsDAO {

    private static final String QUERY_ADD_CUSTOMERS_VS_COUPONS = "INSERT INTO `couponsystem`.`customers_vs_coupons` (`customer_id`, `coupon_id`) VALUES (?, ?);";
    private static final String QUERY_DELETE_CUSTOMERS_VS_COUPONS = "DELETE FROM `couponsystem`.`customers_vs_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?);";
    private static final String QUERY_GET_ALL_CUSTOMERS_COUPONS = "SELECT * FROM `couponsystem`.`customers_vs_coupons` WHERE (`customer_id` = ?);";

    @Override
    public void addCustomersVsCoupons(int customerID, int couponID) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_ADD_CUSTOMERS_VS_COUPONS);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCustomersVsCoupons(int customerID, int couponID) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_CUSTOMERS_VS_COUPONS);
            statement.setInt(1, customerID);
            statement.setInt(2, couponID);
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public List<CustomersVsCoupons> getAllCustomersCoupons(int customerID) {
        List<CustomersVsCoupons> customersVsCoupons = new ArrayList<>();
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_CUSTOMERS_COUPONS);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int couponID = resultSet.getInt(2);
                CustomersVsCoupons temp = new CustomersVsCoupons(customerID, couponID);
                customersVsCoupons.add(temp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return customersVsCoupons;
    }
}
