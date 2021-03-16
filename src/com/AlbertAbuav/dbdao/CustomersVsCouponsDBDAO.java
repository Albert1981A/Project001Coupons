package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CustomersVsCouponsDAO;
import com.AlbertAbuav.utils.Colors;
import com.AlbertAbuav.utils.DBUtils;

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
    private static final String QUERY_IS_CUSTOMERS_COUPONS_EXISTS_BY_CUSTOMER_ID_AND_COUPON_ID = "SELECT * FROM `couponsystem`.`customers_vs_coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`customers_vs_coupons` WHERE (`customer_id` = ?) and (`coupon_id` = ?));";
    private static final String QUERY_IS_CUSTOMERS_COUPONS_EXISTS_BY_COUPON_ID = "SELECT * FROM `couponsystem`.`customers_vs_coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`customers_vs_coupons` WHERE (`coupon_id` = ?));";

    @Override
    public void addCustomersVsCoupons(int customerID, int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        map.put(2, couponID);
        DBUtils.runQuery(QUERY_ADD_CUSTOMERS_VS_COUPONS, map);
    }

    @Override
    public void deleteCustomersVsCoupons(int customerID, int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        map.put(2, couponID);
        DBUtils.runQuery(QUERY_DELETE_CUSTOMERS_VS_COUPONS, map);
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
            Colors.setRedPrint(e.getMessage());
        }
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
            Colors.setRedPrint(e.getMessage());
        }
        return customersVsCoupons;
    }

    @Override
    public boolean isCustomersCouponsExistsByCustomerIdAndCouponId(int customerID, int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, customerID);
        map.put(2, couponID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_CUSTOMERS_COUPONS_EXISTS_BY_CUSTOMER_ID_AND_COUPON_ID, map);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isCustomersCouponsExistsByCouponId(int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_CUSTOMERS_COUPONS_EXISTS_BY_COUPON_ID, map);
        try {
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return false;
    }
}
