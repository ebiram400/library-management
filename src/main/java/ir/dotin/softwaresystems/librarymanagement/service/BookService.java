package ir.dotin.softwaresystems.librarymanagement.service;

import ir.dotin.softwaresystems.librarymanagement.dto.BookStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.entity.BookEntity;
import ir.dotin.softwaresystems.librarymanagement.mapper.BookMapper;
import ir.dotin.softwaresystems.librarymanagement.repository.Books;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Slf4j
@Service
public class BookService {
    private final Books books;

    @Autowired
    public BookService(Books books) {
        this.books = books;
    }

    public ArrayList<Bookdto> watchBooks() throws Exception {
        try{
            return books.findAll().parallelStream().map(BookMapper.INSTANCE::toDto).collect(Collectors.toCollection(ArrayList::new));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("watch books failed");
        }
    }

    public Bookdto addBooks(Bookdto book) throws Exception {
        try{
            books.save(BookMapper.INSTANCE.toEntity(book));
            return book;
        }catch(Exception e){
            log.error(e.getMessage());
            throw new Exception("add book fail!");
        }
    }

    public Bookdto deleteBooks(Bookdto book) throws Exception {
        try{
            books.delete(BookMapper.INSTANCE.toEntity(book));
            return book;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("delete book failed");
        }
    }

    public BookEntity findBook(Bookdto book) throws Exception {
            return books.searchAllByBookNameAndAuthorAndPublisher(book.getBookName(), book.getAuthor(), book.getPublisher())
                    .parallelStream().filter(firstBook->firstBook.getStatus().equals(BookStatus.BOOKABLE)).findFirst().orElseThrow(Exception::new);
    }

    public Bookdto findBookById(BookEntity book) throws Exception {
        try{
            return BookMapper.INSTANCE.toDto(books.findById(book.getId()).orElseThrow(Exception::new));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BookEntity findBookById(Bookdto book) throws Exception {
        try {
            return books.findById(book.getId()).orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatusBook(Bookdto book) throws Exception {
        books.save(BookMapper.INSTANCE.toEntity(book));
    }
}
