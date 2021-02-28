package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dao.CustomersVsCouponsDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.utils.DateUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CouponsDBDAO implements CouponsDAO {

    private static final String QUERY_INSERT_COUPON = "INSERT INTO `couponsystem`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String QUERY_UPDATE_COUPON = "UPDATE `couponsystem`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE_COUPON = "DELETE FROM `couponsystem`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS = "SELECT * FROM `couponsystem`.`coupons`;";
    private static final String QUERY_GET_SINGLE_COUPON = "SELECT * FROM `couponsystem`.`coupons` WHERE (`id` = ?);";

    CustomersVsCouponsDAO customersVsCouponsDAO = new CustomersVsCouponsDBDAO();

    /**
     * To perform an operation from the code to the database there are five steps.
     * This methods use the steps below.
     * Step 2 - Taking a connection from the ConnectionPool class.
     * Step 3 - Preparing the instruction for the SQL end execute it.
     * Step 4 - ResultSet (Optional) - Get from the database results into "ResultSet".
     * Step 5 - Returning the connection to the ConnectionPool.
     */

    @Override
    public void addCoupon(Coupon coupon) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_COUPON);
            statement.setInt(1, coupon.getCompanyID());
            statement.setInt(2, (coupon.getCategory().ordinal())+1);
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, DateUtils.convertJavaDateToSqlDate(DateUtils.addOneDayToUtilDate(coupon.getStartDate())));
            statement.setDate(6, DateUtils.convertJavaDateToSqlDate(DateUtils.addOneDayToUtilDate(coupon.getEndDate())));
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void updateCoupon(Coupon coupon) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_UPDATE_COUPON);
            statement.setInt(1, coupon.getCompanyID());
            statement.setInt(2, (coupon.getCategory().ordinal())+1);
            statement.setString(3, coupon.getTitle());
            statement.setString(4, coupon.getDescription());
            statement.setDate(5, DateUtils.convertJavaDateToSqlDate(coupon.getStartDate()));
            statement.setDate(6, DateUtils.convertJavaDateToSqlDate(coupon.getEndDate()));
            statement.setInt(7, coupon.getAmount());
            statement.setDouble(8, coupon.getPrice());
            statement.setString(9, coupon.getImage());
            statement.setInt(10, coupon.getId());
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public void deleteCoupon(int couponID) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_COUPON);
            statement.setInt(1, couponID);
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    @Override
    public List<Coupon> getAllCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_ALL_COUPONS);
            // Step 4
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int id = resultSet.getInt(1);
                int companyID = resultSet.getInt(2);
                Category category = Category.values()[(resultSet.getInt(3))-1];
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon tmp = new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price, image);
                coupons.add(tmp);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupons;
    }

    @Override
    public Coupon getSingleCoupon(int couponID) {
        Coupon coupon = null;
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_SINGLE_COUPON);
            statement.setInt(1, couponID);
            // Step 4
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            int company_id = resultSet.getInt(2);
            Category category = Category.values()[(resultSet.getInt(3))-1];
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date start_date = resultSet.getDate(6);
            Date end_date = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);
            coupon = new Coupon(id, company_id, category, title, description, start_date, end_date, amount, price, image);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return coupon;
    }

    @Override
    public void addCouponPurchase(int customerID, int couponID) {
        customersVsCouponsDAO.addCustomersVsCoupons(customerID, couponID);
    }

    @Override
    public void deleteCouponPurchase(int customerID, int couponID) {
        customersVsCouponsDAO.deleteCustomersVsCoupons(customerID, couponID);
    }

    @Override
    public List<CustomersVsCoupons> getAllCustomersCoupons(int customerID) {
        return customersVsCouponsDAO.getAllCustomersCoupons(customerID);
    }

    @Override
    public List<CustomersVsCoupons> getAllCustomersCouponsByCouponId(int couponID) {
        return customersVsCouponsDAO.getAllCustomersCouponsByCouponId(couponID);
    }
}
