package com.eriksson.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/UppgiftDatabasTestVivianne?serverTimezone=UTC",
                "root", "Honung2021!"
        );
    }
}
