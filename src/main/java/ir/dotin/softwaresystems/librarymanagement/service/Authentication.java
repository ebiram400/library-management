package ir.dotin.softwaresystems.librarymanagement.service;

import ir.dotin.softwaresystems.librarymanagement.dto.Role;
import ir.dotin.softwaresystems.librarymanagement.dto.Userdto;
import ir.dotin.softwaresystems.librarymanagement.exceptions.UserNotFoundException;
import ir.dotin.softwaresystems.librarymanagement.repository.SessionRepository;
import ir.dotin.softwaresystems.librarymanagement.repository.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Slf4j
@Service
public class Authentication {
    private final Users users;
    private final SessionRepository sessionRepository;

    @Autowired
    public Authentication(Users users, SessionRepository sessionRepository) {
        this.users = users;
        this.sessionRepository = sessionRepository;
    }

    public String login(Userdto Unauthenticateduser) throws SQLException {
        try {
            Userdto userOnDB = users.findUserByUsername(Unauthenticateduser);
            if (userOnDB.getPassword().equals(Unauthenticateduser.getPassword())) {
                sessionRepository.createUserSession(userOnDB);
                return "welcome to your library";
            }
            return "username or password incorrect";
        } catch (UserNotFoundException e) {
            return "username not found";
        }
    }

    public String signup(Userdto Unauthenticateduser) throws SQLException {
        try {
            users.findUserByUsername(Unauthenticateduser.getUsername());
            return "username already exists";
        } catch (UserNotFoundException e) {
            Unauthenticateduser.setRole(Role.MEMBER);
            try {
                users.saveUser(Unauthenticateduser);
                sessionRepository.createUserSession(Unauthenticateduser);
                return "welcome to your library";
            } catch (SQLException exep) {
                log.error("save user not success:{}", exep.getMessage(), exep);
                return "save user not success";
            }
        }
    }

}
