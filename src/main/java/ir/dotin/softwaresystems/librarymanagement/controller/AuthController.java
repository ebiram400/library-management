package ir.dotin.softwaresystems.librarymanagement.controller;

import ir.dotin.softwaresystems.librarymanagement.dto.UserDTO;
import ir.dotin.softwaresystems.librarymanagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class AuthController {
    private final AuthService authentication;

    @Autowired
    public AuthController(AuthService authentication) {
        this.authentication = authentication;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> signupController(@RequestBody UserDTO Unauthenticateduser) throws Exception {
        try {
            return ResponseEntity.ok(authentication.signup(Unauthenticateduser));
        }catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
