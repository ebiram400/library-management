package ir.dotin.softwaresystems.librarymanagement.controller;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.Requestdto;
import ir.dotin.softwaresystems.librarymanagement.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
@RequestMapping("/requests")
public class RequestController {
    private final RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public ResponseEntity<ArrayList<Requestdto>> getRequests() {
        try {
            return ResponseEntity.ok(requestService.watchRequests());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping
    public ResponseEntity<Requestdto> postRequests(@RequestBody Bookdto book) {
        try {
            return ResponseEntity.ok(requestService.addRequest(book));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping
    public ResponseEntity<Requestdto> patchRequests(@RequestBody Requestdto requestdto) {
        try {
            return ResponseEntity.ok(requestService.responseRequests(requestdto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/user")
    public ResponseEntity<ArrayList<Requestdto>> getRequestsUser() {
        try {
            return ResponseEntity.ok(requestService.userRequests());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
