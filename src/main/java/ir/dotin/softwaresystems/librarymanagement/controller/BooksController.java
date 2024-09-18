package ir.dotin.softwaresystems.librarymanagement.controller;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
public class BooksController {
    private BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String viewBooks() {
        return bookService.watchBooks();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public String addBooks(@RequestBody Bookdto book) {
        return bookService.addBooks(book);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping
    public String deleteBooks(@RequestBody Bookdto book) {
        return bookService.deleteBooks(book);
    }
}
