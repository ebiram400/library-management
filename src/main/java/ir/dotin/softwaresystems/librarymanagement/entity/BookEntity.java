package ir.dotin.softwaresystems.librarymanagement.entity;

import ir.dotin.softwaresystems.librarymanagement.dto.BookStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter @Setter
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name", nullable = false)
    private String bookName;

    @Column(name = "Author",nullable = false)
    private String author;

    @Column(name = "publisher",nullable = false)
    private String publisher;

    @Column(name = "category",nullable = false)
    private String category;

    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @Column(name = "theLastRecipient")
    private String theLastRecipient;

    @Column(name = "lastDateOfReservation")
    private LocalDateTime lastDateOfReservation;

    public BookEntity(Long id, String bookName, String author, String publisher, BookStatus status) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
    }

    public BookEntity(String bookName, String author, String publisher) {
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
    }

    public BookEntity() {
    }

    public BookEntity(Long id) {
        this.id = id;
    }
}
