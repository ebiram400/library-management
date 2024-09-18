package ir.dotin.softwaresystems.librarymanagement.exceptions;

public class DBConnectionException extends RuntimeException {
    public DBConnectionException(String message) {
        super(message);
    }
}
