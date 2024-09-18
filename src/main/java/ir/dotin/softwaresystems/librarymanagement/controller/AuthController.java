package ir.dotin.softwaresystems.librarymanagement.controller;

import ir.dotin.softwaresystems.librarymanagement.dto.Userdto;
import ir.dotin.softwaresystems.librarymanagement.service.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;


@RestController
public class AuthController {
    private final Authentication authentication;
    @Autowired
    public AuthController(Authentication authentication) {
        this.authentication = authentication;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String loginController(@RequestBody Userdto Unauthenticateduser ) throws SQLException {
        return authentication.login(Unauthenticateduser);
    }
    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String signupController(@RequestBody Userdto Unauthenticateduser ) throws SQLException {
        return authentication.signup(Unauthenticateduser);
    }

}
