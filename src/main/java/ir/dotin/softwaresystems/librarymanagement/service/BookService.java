package ir.dotin.softwaresystems.librarymanagement.service;

import com.google.gson.Gson;
import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.repository.Books;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BookService {
    private final Books books;

    @Autowired
    public BookService(Books books) {
        this.books = books;
    }

    public String watchBooks(){
        try{
            return new Gson().toJson(books.getBooks());
        }catch (Exception e){
            log.error(e.getMessage());
            return "watch books failed";
        }

    }

    public String addBooks(Bookdto book){
        try{
            books.addBook(book);
            return "book added";
        }catch(Exception e){
            log.error(e.getMessage());
            return "delete book failed";
        }
    }

    public String deleteBooks(Bookdto book){
        try{
            books.DeleteBook(book);
            return "Book deleted";
        }catch (Exception e){
            log.error(e.getMessage());
            return "delete book failed";
        }
    }
}
