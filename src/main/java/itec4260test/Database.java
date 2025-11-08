package itec4260test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Database {

    private static final Logger logger = LogManager.getLogger(Database.class);
    private static final String DB_URL = "jdbc:sqlite:hotel_prices.db";

    /** Connect to SQLite database with auto-commit enabled */
    public static Connection connect() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true); // ensures inserts/updates are saved immediately
            logger.info("Connected to database: " + DB_URL);
            return conn;
        } catch (SQLException e) {
            logger.error("Connection failed: " + e.getMessage());
            return null;
        }
    }

    /** Create table if it does not exist */
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS hotel_prices ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "hotel_name TEXT NOT NULL, "
                + "city TEXT NOT NULL, "
                + "date TEXT NOT NULL, "
                + "price REAL NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            if (conn != null) {
                stmt.execute(sql);
                logger.info("Table hotel_prices created or already exists.");
            } else {
                logger.error("Cannot create table: no database connection.");
            }

        } catch (SQLException e) {
            logger.error("Table creation failed: " + e.getMessage());
        }
    }

    /** Clear all data (for testing) */
    public static void clearTable() {
        String sql = "DELETE FROM hotel_prices";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.executeUpdate(sql);
                logger.info("Table hotel_prices cleared.");
            }
        } catch (SQLException e) {
            logger.error("Failed to clear table: " + e.getMessage());
        }
    }
}
