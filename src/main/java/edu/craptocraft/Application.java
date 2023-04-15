package edu.craptocraft;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class Application
{
    private static HikariDataSource dataSource;
    public static void main(String[] args) throws SQLException
    {
        try{
            initDatabaseConnectionPool();
            deleteData("%");
            readData();
            createData("Java", 10);
            createData("JavaScript", 8);
            createData("C++", 6);
            readData();
            updateData("C++", 4);
            readData();
            deleteData("C++");
            readData();
        }finally {
            closeDatabaseConnectionPool();
        }
    }

    private static void createData(String name, int rating) throws SQLException
    {
        System.out.println("Creating data...");
        int rowInserted;
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement statment = connection.prepareStatement("""
                        INSERT INTO programming_language(name, rating)
                        VALUES (?, ?)
                    """)) {
                statment.setString(1, name);
                statment.setInt(2, rating);
                rowInserted = statment.executeUpdate();
            }
        }
        System.out.println("Rows inserted..." + rowInserted);
    }

    private static void readData() throws SQLException
    {
        System.out.println("Reading data...");
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT name, rating
                    FROM programming_language
                    ORDER BY rating DESC
                    """)) {
                ResultSet resultSet = statement.executeQuery();
                boolean empty = true;
                while (resultSet.next()){
                    String name = resultSet.getString(1);
                    int rating = resultSet.getInt(2);
                    System.out.println("<\t> " + name + ": " + rating);
                    empty = false;
                }
                if (empty){
                    System.out.println("<\t> (no data)" );
                }
            }
        }
    }

    private static void updateData(String name, int newRating) throws SQLException
    {
        System.out.println("Updating data...");
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("""
                        UPDATE programming_language
                        SET rating = ?
                        WHERE name = ?
                    """)) {
                statement.setInt(1, newRating);
                statement.setString(2, name);
                int rowsUpdated = statement.executeUpdate();
                System.out.println("Rows updated " + rowsUpdated);
            }
        }
    }

    private static void deleteData(String nameExpression) throws SQLException
    {
        System.out.println("Deleting data...");
        try (Connection connection = dataSource.getConnection())
        {
            try (PreparedStatement statement = connection.prepareStatement("""
                        DELETE FROM programming_language
                        WHERE name LIKE ?
                    """)) {
                statement.setString(1, nameExpression);
                int rowsDeleted = statement.executeUpdate();
                System.out.println("Rows deleted: " + rowsDeleted);
            }
        }
    }

    private static void initDatabaseConnectionPool()
    {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3307/jdbc_demo");
        dataSource.setUsername("root");
        dataSource.setPassword("Darma17");
        //if (true) throw  new RuntimeException("Simulated error");
    }

    private static void closeDatabaseConnectionPool()
    {
        dataSource.close();
    }


}
