
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
    private static Connection con = null;

    private Connections() throws SQLException, ClassNotFoundException{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection(
                "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_o7p0b", "a10467158");
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(con == null){
            new Connections();
        }
        return con;
    }
}
