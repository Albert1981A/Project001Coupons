package com.AlbertAbuav.utils;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.beans.Coupon;
import com.AlbertAbuav.beans.CustomersVsCoupons;
import com.AlbertAbuav.dao.CouponsDAO;
import com.AlbertAbuav.db.ConnectionPool;
import com.AlbertAbuav.dbdao.CouponsDBDAO;
import com.AlbertAbuav.exceptions.invalidCompanyException;
import com.AlbertAbuav.exceptions.invalidCustomerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class DBUtils {
    /**
     * The DBUtils job is to recycle code to optimize the writing.
     * To perform an operation from the code to the database there are five steps.
     * This method perform 3 steps.
     * Step 2 - Taking a connection from the ConnectionPool class.
     * Step 3 - Preparing the instruction for the SQL end execute it.
     * Step 5 - Returning the connection to the ConnectionPool.
     * @param sql String
     */
    private static CouponsDAO couponsDAO = new CouponsDBDAO();

    public static void runQuery(String sql) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (Exception e) {
            Colors.setRedPrint(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public static void runQuery(String sql, Map<Integer, Object> map) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(sql);
            int key = 0;
            Object value = null;
            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value instanceof Integer) {
                    statement.setInt(key, (int) value);
                } else if (value instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (value instanceof Date) {
                    statement.setDate(key, DateUtils.convertJavaDateToSqlDate((Date) value));
                } else if (value instanceof Float) {
                    statement.setFloat(key, (Float) value);
                } else if (value instanceof Double) {
                    statement.setDouble(key, (Double) value);
                } else if (value instanceof Category) {
                    statement.setInt(key,((Category) value).ordinal()+1);
                }
            }
            statement.execute();

        } catch (Exception e) {
            Colors.setRedPrint(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
    }

    public static ResultSet runQueryWithResultSet(String sql, Map<Integer, Object> map) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(sql);
            int key = 0;
            Object value = null;
            for (Map.Entry<Integer, Object> entry : map.entrySet()) {
                key = entry.getKey();
                value = entry.getValue();
                if (value instanceof Integer) {
                    statement.setInt(key, (int) value);
                } else if (value instanceof String) {
                    statement.setString(key, String.valueOf(value));
                } else if (value instanceof Date) {
                    statement.setDate(key, DateUtils.convertJavaDateToSqlDate((Date) value));
                } else if (value instanceof Float) {
                    statement.setFloat(key, (Float) value);
                } else if (value instanceof Double) {
                    statement.setDouble(key, (Double) value);
                } else if (value instanceof Category) {
                    statement.setInt(key, ((Category) value).ordinal()+1);
                }
            }
            // Step 4
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            Colors.setRedPrint(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }

    public static ResultSet runQueryWithResultSet(String sql) {
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(sql);
            // Step 4
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            Colors.setRedPrint(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }

    public static List<Coupon> getAllCustomerCouponsGeneric(int customerID, Object object) throws invalidCustomerException {
        List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCoupons(customerID);
        if (purchases.size() == 0) {
            throw new invalidCustomerException("There are no coupons purchased by the Logged customer!");
        }
        List<Coupon> couponList = new ArrayList<>();
        if (object instanceof Double) {
            for (CustomersVsCoupons purchase : purchases) {
                if (couponsDAO.isCouponExistsByCouponIdAndMaxPrice(purchase.getCouponID(), (double)object)) {
                    couponList.add(couponsDAO.getSingleCoupon(purchase.getCouponID()));
                }
            }
        } else if (object instanceof Category) {
            for (CustomersVsCoupons purchase : purchases) {
                if (couponsDAO.isCouponExistsByCouponIdAndCategory(purchase.getCouponID(), (Category)object)) {
                    couponList.add(couponsDAO.getSingleCoupon(purchase.getCouponID()));
                }
            }
        }
        if (couponList.size() == 0) {
            throw new invalidCustomerException("No coupons were found!");
        }
        return couponList;
    }

    public static List<Coupon> getAllCustomerCouponsGeneric(int customerID) throws invalidCustomerException {
        List<CustomersVsCoupons> purchases = couponsDAO.getAllCustomersCoupons(customerID);
        if (purchases.size() == 0) {
            throw new invalidCustomerException("There are no coupons purchased by the Logged customer!");
        }
        List<Coupon> couponList = new ArrayList<>();
        for (CustomersVsCoupons purchase : purchases) {
            couponList.add(couponsDAO.getSingleCoupon(purchase.getCouponID()));
        }
        if (couponList.size() == 0) {
            throw new invalidCustomerException("No coupons were found!");
        }
        return couponList;
    }

    public static List<Coupon> getAllCompanyCouponsGeneric(int companyID, Object object) throws invalidCompanyException {
        List<Coupon> companyCoupons = new ArrayList<>();
        if (object instanceof Category) {
            companyCoupons = couponsDAO.getAllCompanyCouponsOfSpecificCategory(companyID, (Category) object);
        } else if (object instanceof Double) {
            companyCoupons = couponsDAO.getAllCompanyCouponsUpToMaxPrice(companyID, (double) object);
        }
        if (companyCoupons.size() == 0) {
            throw new invalidCompanyException("No coupons found in system!");
        }
        return companyCoupons;
    }

    public static List<Coupon> getAllCompanyCouponsGeneric(int companyID) throws invalidCompanyException {
        List<Coupon> companyCoupons = couponsDAO.getAllCouponsOfSingleCompany(companyID);
        if (companyCoupons.size() == 0) {
            throw new invalidCompanyException("No coupons found in system!");
        }
        return companyCoupons;
    }
}
