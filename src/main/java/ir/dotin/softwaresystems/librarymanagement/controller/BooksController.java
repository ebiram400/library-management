package ir.dotin.softwaresystems.librarymanagement.controller;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;


@RestController
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<ArrayList<Bookdto>> watchBooks() {
        try{
            return ResponseEntity.ok(bookService.watchBooks());
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,(String) e.getMessage());
        }
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<Bookdto> addBooks(@RequestBody Bookdto book) {
        try {
            return ResponseEntity.ok(bookService.addBooks(book));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,(String) e.getMessage());
        }

    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping
    public ResponseEntity<Bookdto> deleteBooks(@RequestBody Bookdto book) {
        try {
            return ResponseEntity.ok(bookService.deleteBooks(book));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,(String) e.getMessage());
        }
    }
}
