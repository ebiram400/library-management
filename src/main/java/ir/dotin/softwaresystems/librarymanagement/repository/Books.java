package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface Books extends JpaRepository<BookEntity, Long> {
    ArrayList<BookEntity> searchAllByBookNameAndAuthorAndPublisher( String bookName, String author, String publisher);


    BookEntity findById(BookEntity book);


    void updateStatusBook(BookEntity book);
}
