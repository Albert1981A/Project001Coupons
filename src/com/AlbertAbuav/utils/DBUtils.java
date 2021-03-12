package com.AlbertAbuav.utils;

import com.AlbertAbuav.beans.Category;
import com.AlbertAbuav.db.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;

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
    public static void runQuery(String sql) {
        Connection connection = null;
        try {
            // Step 2
            connection = ConnectionPool.getInstance().getConnection();
            // Step 3
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                    statement.setInt(key,(int) value);
                }
            }
            statement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                    statement.setInt(key,(int) value);
                }
            }
            // Step 4
            resultSet = statement.executeQuery();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        } finally {
            // Step 5
            ConnectionPool.getInstance().returnConnection(connection);
        }
        return resultSet;
    }
}
