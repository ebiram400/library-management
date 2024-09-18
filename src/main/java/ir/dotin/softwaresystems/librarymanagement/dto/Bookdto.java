package ir.dotin.softwaresystems.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Bookdto {
    private int book_id;
    private String book_name;
    private String author;
    private String publisher;
    private BookStatus status = BookStatus.BOOKABLE;
    private String theLastRecipient;
    private LocalDateTime lastDateOfReservation;

    public Bookdto() {
    }

    public Bookdto(String book_name, String author, String publisher) {
        this.book_name = book_name;
        this.author = author;
        this.publisher = publisher;
    }

    public Bookdto(int book_id,BookStatus status) {
        this.book_id = book_id;
        this.status = status;
    }
}
