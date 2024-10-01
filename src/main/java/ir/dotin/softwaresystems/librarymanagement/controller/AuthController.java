package ir.dotin.softwaresystems.librarymanagement.controller;

import ir.dotin.softwaresystems.librarymanagement.dto.UserDTO;
import ir.dotin.softwaresystems.librarymanagement.service.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
public class AuthController {
    private final Authentication authentication;

    @Autowired
    public AuthController(Authentication authentication) {
        this.authentication = authentication;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginController(@RequestBody UserDTO Unauthenticateduser) {
        if(authentication.login(Unauthenticateduser) instanceof String)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,(String) authentication.login(Unauthenticateduser));
        return ResponseEntity.ok(authentication.login(Unauthenticateduser));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Object> signupController(@RequestBody UserDTO Unauthenticateduser) {
        if(authentication.signup(Unauthenticateduser) instanceof String)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,(String) authentication.signup(Unauthenticateduser));
        return ResponseEntity.ok(authentication.signup(Unauthenticateduser));
    }
}
