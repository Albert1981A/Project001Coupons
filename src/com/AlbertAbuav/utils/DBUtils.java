package com.AlbertAbuav.utils;

import com.AlbertAbuav.db.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DBUtils {
    /**
     * To perform an operation from the code to the database there are five steps.
     * This method perform 3 steps.
     * Step 2 - Taking a connection from the ConnectionPool class.
     * Step 3 - Preparing the instruction for the SQL end execute it.
     * Step 5 - Returning the connection to the ConnectionPool.
     * @param sql
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
}
