package ir.dotin.softwaresystems.librarymanagement.repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class ConnectToDB {
    private static Statement statement;

    public static Statement connectToDB() throws SQLException {
        if(statement==null){
            try{
                Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:8081/librarydb", "root", "");
                statement = connection.createStatement();
                return(statement);
            }catch (SQLException e){
                throw new SQLException();
            }
        }
        return statement;
    }
}
