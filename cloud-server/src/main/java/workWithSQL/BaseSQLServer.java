package workWithSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseSQLServer implements SQLServer {
    public static Connection connection;
    private static final String URL_SQL = "jdbc:sqlite:bd_user.db";


    @Override
    public void start() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(URL_SQL);
    }

    @Override
    public void stop() throws SQLException {
        connection.close();
    }
}
