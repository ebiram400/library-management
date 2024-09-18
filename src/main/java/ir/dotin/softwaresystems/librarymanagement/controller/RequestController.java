package ir.dotin.softwaresystems.librarymanagement.controller;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.Requestdto;
import ir.dotin.softwaresystems.librarymanagement.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
public class RequestController {
    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping
    public String getRequests() {
        return requestService.watchRequests();
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping
    public String postRequests(@RequestBody Bookdto book) {
        return requestService.addRequest(book);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PatchMapping
    public String patchRequests(@RequestBody Requestdto requestdto) {
        return requestService.responseRequests(requestdto);
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/user")
    public String getRequestsUser() {
        return requestService.userRequests();
    }
}
