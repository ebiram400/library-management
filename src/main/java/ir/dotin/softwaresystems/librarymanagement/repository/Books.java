package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.dto.BookStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.Requestdto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static ir.dotin.softwaresystems.librarymanagement.repository.ConnectToDB.connectToDB;

@Repository
public class Books {
    private static final Logger logger = LoggerFactory.getLogger(Books.class);

    public void DeleteBook(Bookdto book) throws SQLException {
//        connect to data base
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            logger.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlDelete = "Delete from books where book_name=" + book.getBook_name();
        try {
            newStatement.executeUpdate(sqlDelete);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public void addBook(Bookdto book) throws SQLException {
//        connect to data base
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            logger.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlInsert = "INSERT INTO books (book_name,author,publisher) VALUES (" + book.getBook_name() + "," + book.getAuthor() + "," + book.getPublisher() + ")";
        try {
            newStatement.executeUpdate(sqlInsert);
        } catch (Exception e) {
            logger.error("inert new book fail. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

    }

    public ArrayList<Bookdto> getBooks() throws SQLException {
        ArrayList<Bookdto> books = new ArrayList<>();
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            logger.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlSelect = "SELECT * FROM books";
        try {
            ResultSet resultSet = newStatement.executeQuery(sqlSelect);
            while (resultSet.next()) {
                Bookdto book = new Bookdto(resultSet.getString("book_name"), resultSet.getString("author"), resultSet.getString("publisher"));
                book.setBook_id(resultSet.getInt("book_id"));
                book.setStatus(BookStatus.valueOf(resultSet.getString("status")));
                book.setTheLastRecipient(resultSet.getString("the_last_recipient"));
                book.setLastDateOfReservation(resultSet.getTimestamp("last_date_of_reservation").toLocalDateTime());

                books.add(book);
            }
            return books;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SQLException(e);
        }
    }

    public ArrayList<Bookdto> findBook(Bookdto book) throws SQLException {
        //        connect to data base
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            logger.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlSelect = "SELECT * FROM books WHERE book_name=" + book.getBook_name() + "author=" + book.getAuthor() + "publisher=" + book.getPublisher();
        try {
            ResultSet resultSet = newStatement.executeQuery(sqlSelect);
            ArrayList<Bookdto> booksTarget = new ArrayList<>();
            while (resultSet.next()) {
                booksTarget.add(new Bookdto(resultSet.getInt("book_id"), BookStatus.valueOf(resultSet.getString("status"))));
            }
            return booksTarget;
        } catch (Exception e) {
            logger.error("select book by Id fail. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }
    }

    public void updateStatusBook(Requestdto requestdto) throws SQLException {
        //        connect to data base
        Statement newStatement = null;
        try {
            newStatement = connectToDB();
        } catch (SQLException e) {
            logger.error("database connection failed. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }

        String sqlUpdate = "UPDATE books SET status=" + BookStatus.NOT_BOOKABLE.name() + ",theLastRecipient=" + requestdto.getUser_id() + ",lastDateOfReservation=" + LocalDateTime.now()+" WHERE id="+requestdto.getBook_id();
        try {
            newStatement.executeUpdate(sqlUpdate);
        } catch (SQLException e) {
            logger.error("update book status fail. Error: {}", e.getMessage(), e);
            throw new SQLException(e);
        }
    }
}
