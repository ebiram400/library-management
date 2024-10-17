package ir.dotin.softwaresystems.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
public class Bookdto {
    private Long id;
    @NotBlank(message = "Book name must not be blank")
    private String bookName;
    @Pattern(regexp = "^[A-Za-z]+$", message = "Author name must contain only letters")
    private String author;
    @Pattern(regexp = "^[0-9]{4}$",message = "year of publisher must be 4 digits")
    private String publisher;
    private String category;
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
