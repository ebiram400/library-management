package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.dto.RequestStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Requestdto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static ir.dotin.softwaresystems.librarymanagement.repository.ConnectToDB.connectToDB;

@Slf4j
@Repository
public class Requests {

    public ArrayList<Requestdto> watchRequest() throws SQLException {
        ArrayList<Requestdto> requests = new ArrayList<>();
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            log.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlSelect = "SELECT * FROM requests WHERE status=" + RequestStatus.PENDING_APPROVAL;
        try {
            ResultSet resultSet = newStatement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                Requestdto request = new Requestdto();
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Requestdto> watchRequest(int user_id) throws SQLException {
        ArrayList<Requestdto> requests = new ArrayList<>();
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            log.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlSelect = "SELECT * FROM requests WHERE user_id="+user_id ;
        try {
            ResultSet resultSet = newStatement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                Requestdto request = new Requestdto();
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addRequest(Requestdto request) throws SQLException {
        //        connect to data base
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            log.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlInsert = "INSERT INTO requests (user_id,book_id,status) VALUES (" + request.getUser_id() + "," + request.getBook_id() + "," + request.getRequestStatus() + ")";
        try {
            newStatement.executeUpdate(sqlInsert);
        } catch (Exception e) {
            log.error("inert new book fail. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }
    }

    public void updateRequestStatus(Requestdto request) throws SQLException {
        //        connect to data base
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            log.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlUpdate="UPDATE requests SET status="+request.getRequestStatus().toString() +" WHERE user_id="+request.getUser_id()+", book_id="+request.getBook_id();
        try {
            newStatement.executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            log.error("update request status fail. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }
    }
}
