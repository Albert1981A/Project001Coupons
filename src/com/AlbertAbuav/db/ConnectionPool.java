package com.AlbertAbuav.db;

import com.AlbertAbuav.utils.Colors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    /**
     * This is a singleton class and this is the one and only instance of the class
     * It create a Stack of connections to the data bass ready for all actions to the database
     */
    private static ConnectionPool instance = null;
    private static final int NUM_OF_CONS = 10;
    private final Stack<Connection> connections = new Stack<>();

    /**
     * Make the constructor private
     * This is to invoke the class by the getter
     */
    private ConnectionPool() {
        // System.out.println("\"CTOR\" IN ACTION !!!");
        openAllConnections();
    }

    /**
     * The class is lazily loaded (it is initialized only after it is called by the getter)
     * The getter method have a doable lock synchronized block
     * This is to make the method efficient and Thread safe
     * @return
     */
    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    /**
     * Get a connection from the Stack.
     * If the stack is empty the stack will "wait".
     * only when a connection retrieve the Stack
     * Will be "notify" and the "pop" will be executed.
     * @return connection
     */
    public Connection getConnection() {
        synchronized (connections) {
            if (connections.empty()) {
                try {
                    connections.wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return connections.pop();
    }

    /**
     * Return a connection to the Stack.
     * After the insert of the connection to the Stack
     * It will notify that a connection retrieved.
     * @param connection
     */
    public void returnConnection(Connection connection) {
        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }

    /**
     * Create 10 connections ready to use in a Stack Collection
     */
    public void openAllConnections() {
        for (int i = 0; i < NUM_OF_CONS; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(DatabaseManager.url, DatabaseManager.username, DatabaseManager.password);
            } catch (SQLException throwables) {
                // throwables.printStackTrace();
                // System.out.println(throwables.getMessage());
                Colors.setRedPrint(throwables.getMessage());
            }
            connections.push(connection);
        }
    }

    /**
     * Close all the connections but only if the stack is full.
     * If not, and their is a connection in use, the Stack will "wait".
     */
    public void closeAllConnections() {
        synchronized (connections) {
            while (connections.size() < NUM_OF_CONS) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        connections.removeAllElements();
    }
}
