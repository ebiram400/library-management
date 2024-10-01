package ir.dotin.softwaresystems.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Bookdto {
    private Long id;
    private String bookName;
    private String author;
    private String publisher;
    private BookStatus status = BookStatus.BOOKABLE;
    private String theLastRecipient;
    private LocalDateTime lastDateOfReservation;

    public Bookdto() {
    }

    public Bookdto(String bookName, String author, String publisher) {
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
    }
}
