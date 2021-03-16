package com.AlbertAbuav.dbdao;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.dao.CustomersVsCouponsDAO;
import com.AlbertAbuav.utils.Colors;
import com.AlbertAbuav.utils.DBUtils;
import com.AlbertAbuav.utils.DateUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CouponsDBDAO implements CouponsDAO {

    private static final String QUERY_IS_COUPON_EXISTS_BY_TITLE = "SELECT * FROM `couponsystem`.`coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`coupons` WHERE (`title` = ?));";
    private static final String QUERY_IS_COUPON_EXISTS_BY_COUPON_ID_AND_COMPANY_ID = "SELECT * FROM `couponsystem`.`coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`coupons` WHERE (`id` = ?) AND (`company_id` = ?));";
    private static final String QUERY_IS_COUPON_EXISTS_BY_COUPON_ID_AND_CATEGORY = "SELECT * FROM `couponsystem`.`coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`coupons` WHERE (`id` = ?) AND (`category_id` = ?));";
    private static final String QUERY_IS_COUPON_EXISTS_BY_COUPON_ID_AND_MAX_PRICE = "SELECT * FROM `couponsystem`.`coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`coupons` WHERE (`id` = ?) AND (`price` < ?));";
    private static final String QUERY_IS_COUPONS_EXISTS_BY_COMPANY_ID = "SELECT * FROM `couponsystem`.`coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`coupons` WHERE (`company_id` = ?));";
    private static final String QUERY_IS_COUPON_EXISTS_BY_COMPANY_ID_AND_TITLE = "SELECT * FROM `couponsystem`.`coupons` WHERE EXISTS (SELECT * FROM `couponsystem`.`coupons` WHERE (`company_id` = ?) AND (`title` = ?));";
    private static final String QUERY_INSERT_COUPON = "INSERT INTO `couponsystem`.`coupons` (`company_id`, `category_id`, `title`, `description`, `start_date`, `end_date`, `amount`, `price`, `image`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String QUERY_UPDATE_COUPON = "UPDATE `couponsystem`.`coupons` SET `company_id` = ?, `category_id` = ?, `title` = ?, `description` = ?, `start_date` = ?, `end_date` = ?, `amount` = ?, `price` = ?, `image` = ? WHERE (`id` = ?);";
    private static final String QUERY_DELETE_COUPON = "DELETE FROM `couponsystem`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS = "SELECT * FROM `couponsystem`.`coupons`;";
    private static final String QUERY_GET_ALL_COUPONS_OF_A_SINGLE_COMPANY = "SELECT * FROM `couponsystem`.`coupons` WHERE (`company_id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS_OF_A_SINGLE_COMPANY_BY_CATEGORY = "SELECT * FROM `couponsystem`.`coupons` WHERE (`company_id` = ?) AND (`category_id` = ?);";
    private static final String QUERY_GET_ALL_COUPONS_UP_TO_MAX_PRICE = "SELECT * FROM `couponsystem`.`coupons` WHERE (`company_id` = ?) AND (`price` < ?);";
    private static final String QUERY_GET_SINGLE_COUPON = "SELECT * FROM `couponsystem`.`coupons` WHERE (`id` = ?);";
    private static final String QUERY_GET_SINGLE_COUPON_BY_COUPON_ID_AND_CATEGORY = "SELECT * FROM `couponsystem`.`coupons` WHERE (`id` = ?) AND (`category_id` = ?);";
    private static final String QUERY_GET_COUPON_BY_TITLE = "SELECT * FROM `couponsystem`.`coupons` WHERE (`title` = ?);";

    private CustomersVsCouponsDAO customersVsCouponsDAO = new CustomersVsCouponsDBDAO();

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
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyID());
        map.put(2, (coupon.getCategory().ordinal())+1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, DateUtils.addOneDayToUtilDate(coupon.getStartDate()));
        map.put(6, DateUtils.addOneDayToUtilDate(coupon.getEndDate()));
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        DBUtils.runQuery(QUERY_INSERT_COUPON, map);
    }

    @Override
    public void updateCoupon(Coupon coupon) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, coupon.getCompanyID());
        map.put(2, (coupon.getCategory().ordinal())+1);
        map.put(3, coupon.getTitle());
        map.put(4, coupon.getDescription());
        map.put(5, coupon.getStartDate());
        map.put(6, coupon.getEndDate());
        map.put(7, coupon.getAmount());
        map.put(8, coupon.getPrice());
        map.put(9, coupon.getImage());
        map.put(10, coupon.getId());
        DBUtils.runQuery(QUERY_UPDATE_COUPON, map);
    }

    @Override
    public void deleteCoupon(int couponID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        DBUtils.runQuery(QUERY_DELETE_COUPON, map);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_COUPONS);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int companyID = resultSet.getInt(2);
                Category category = Category.values()[(resultSet.getInt(3)) - 1];
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
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllCouponsOfSingleCompany(int companyID) {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_COUPONS_OF_A_SINGLE_COMPANY, map);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                Category category = Category.values()[(resultSet.getInt(3)) - 1];
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
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupons;
    }

    @Override
    public boolean isCouponExistsByCouponIdAndCompanyId(int couponID, int companyID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        map.put(2, companyID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COUPON_EXISTS_BY_COUPON_ID_AND_COMPANY_ID, map);
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
    public boolean isCouponsExistsByCompanyId(int companyID) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COUPONS_EXISTS_BY_COMPANY_ID, map);
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
    public boolean isCouponExistsByCompanyIdAndTitle(int companyID, String title) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        map.put(2, title);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COUPON_EXISTS_BY_COMPANY_ID_AND_TITLE, map);
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
    public boolean isCouponExistsByTitle(String title) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, title);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COUPON_EXISTS_BY_TITLE, map);
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
    public boolean isCouponExistsByCouponIdAndCategory(int couponID, Category category) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        map.put(2, category);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COUPON_EXISTS_BY_COUPON_ID_AND_CATEGORY, map);
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
    public boolean isCouponExistsByCouponIdAndMaxPrice(int couponID, double maxPrice) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        map.put(2, maxPrice);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_IS_COUPON_EXISTS_BY_COUPON_ID_AND_MAX_PRICE, map);
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
    public List<Coupon> getAllExpiredCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_COUPONS);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int companyID = resultSet.getInt(2);
                Category category = Category.values()[(resultSet.getInt(3)) - 1];
                String title = resultSet.getString(4);
                String description = resultSet.getString(5);
                Date startDate = resultSet.getDate(6);
                Date endDate = resultSet.getDate(7);
                int amount = resultSet.getInt(8);
                double price = resultSet.getDouble(9);
                String image = resultSet.getString(10);
                Coupon tmp = new Coupon(id, companyID, category, title, description, startDate, endDate, amount, price, image);
                if (tmp.getEndDate().before(DateUtils.javaDateFromLocalDate(LocalDate.now()))) {
                    coupons.add(tmp);
                }
            }
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupons;
    }

    @Override
    public Coupon getSingleCoupon(int couponID) {
        Coupon coupon = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_SINGLE_COUPON, map);
        try {
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
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupon;
    }

    @Override
    public Coupon getSingleCouponByCouponIdAndCategory(int couponID, Category category) {
        Coupon coupon = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, couponID);
        map.put(2, category);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_SINGLE_COUPON_BY_COUPON_ID_AND_CATEGORY, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            int company_id = resultSet.getInt(2);
            Category categoryB = Category.values()[(resultSet.getInt(3))-1];
            String title = resultSet.getString(4);
            String description = resultSet.getString(5);
            Date start_date = resultSet.getDate(6);
            Date end_date = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);
            coupon = new Coupon(id, company_id, categoryB, title, description, start_date, end_date, amount, price, image);
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupon;
    }

    @Override
    public Coupon getCouponByTitle(String title) {
        Coupon coupon = null;
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, title);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_COUPON_BY_TITLE, map);
        try {
            resultSet.next();
            int id = resultSet.getInt(1);
            int company_id = resultSet.getInt(2);
            Category category = Category.values()[(resultSet.getInt(3))-1];
            String description = resultSet.getString(5);
            Date start_date = resultSet.getDate(6);
            Date end_date = resultSet.getDate(7);
            int amount = resultSet.getInt(8);
            double price = resultSet.getDouble(9);
            String image = resultSet.getString(10);
            coupon = new Coupon(id, company_id, category, title, description, start_date, end_date, amount, price, image);
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupon;
    }

    @Override
    public List<Coupon> getAllCompanyCouponsOfSpecificCategory(int companyID, Category category) {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        map.put(2, category);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_COUPONS_OF_A_SINGLE_COMPANY_BY_CATEGORY, map);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
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
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupons;
    }

    @Override
    public List<Coupon> getAllCompanyCouponsUpToMaxPrice(int companyID, double maxPrice) {
        List<Coupon> coupons = new ArrayList<>();
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, companyID);
        map.put(2, maxPrice);
        ResultSet resultSet = DBUtils.runQueryWithResultSet(QUERY_GET_ALL_COUPONS_UP_TO_MAX_PRICE, map);
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
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
        } catch (SQLException e) {
            Colors.setRedPrint(e.getMessage());
        }
        return coupons;
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

    @Override
    public boolean isCustomersCouponsExistsByCustomerIdAndCouponId(int customerID, int couponID) {
        return customersVsCouponsDAO.isCustomersCouponsExistsByCustomerIdAndCouponId(customerID, couponID);
    }

    @Override
    public boolean isCustomersCouponsExistsByCouponId(int couponID) {
        return customersVsCouponsDAO.isCustomersCouponsExistsByCouponId(couponID);
    }
}
