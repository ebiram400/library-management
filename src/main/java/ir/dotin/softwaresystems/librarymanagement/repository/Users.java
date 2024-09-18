package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.dto.Role;
import ir.dotin.softwaresystems.librarymanagement.dto.Userdto;
import ir.dotin.softwaresystems.librarymanagement.exceptions.UserNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ir.dotin.softwaresystems.librarymanagement.repository.ConnectToDB.connectToDB;

@Repository
@Getter
@Setter
public class Users {

    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    public Users() {
    }

    public Userdto findUserByUsername(Userdto Unauthenticateduser) throws SQLException {
        Statement newStatement=null;
        try{
           newStatement = connectToDB();
        }catch (SQLException e){
            logger.error("database connection failed. Error: {}",e.getMessage(),e);
        }
        String sqlSelect = "SELECT * FROM users WHERE username = " + Unauthenticateduser.getUsername();
        try {
            ResultSet resultSet = newStatement.executeQuery(sqlSelect);
            Userdto userOnDB = new Userdto(Unauthenticateduser.getUsername(), resultSet.getString("password"));
            userOnDB.setId(resultSet.getInt("id"));
            userOnDB.setRole(Role.valueOf(resultSet.getString("role")));
            return userOnDB;
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("username not found");
        }
    }

    public void findUserByUsername(String username) throws SQLException {
        Statement newStatement=null;
        try{
            newStatement = connectToDB();
        }catch (SQLException e){
            logger.error("database connection failed. Error: {}",e.getMessage(),e);
            throw new SQLException(e);
        }
        String sqlSelect = "SELECT * FROM users WHERE username = " + username;
        try {
            newStatement.executeQuery(sqlSelect);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("username not found");
        }
    }

    public void saveUser(Userdto user) throws SQLException {
        Statement newStatement2=null;
        try{
            newStatement2 = connectToDB();
        }catch (SQLException e){
            logger.error("database connection failed. Error: {}",e.getMessage(),e);
            throw new SQLException();
        }
        String sqlSave="INSERT INTO users (username, password,role) VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "', '" + user.getRole().name() + "')";
        newStatement2.executeUpdate(sqlSave);
    }
}
