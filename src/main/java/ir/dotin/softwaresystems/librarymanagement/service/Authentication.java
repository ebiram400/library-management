package ir.dotin.softwaresystems.librarymanagement.service;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.Role;
import ir.dotin.softwaresystems.librarymanagement.dto.UserDTO;

import ir.dotin.softwaresystems.librarymanagement.entity.BookEntity;
import ir.dotin.softwaresystems.librarymanagement.entity.UserEntity;
import ir.dotin.softwaresystems.librarymanagement.exceptions.UserNotFoundException;
import ir.dotin.softwaresystems.librarymanagement.mapper.UserMapper;
import ir.dotin.softwaresystems.librarymanagement.repository.SessionRepository;
import ir.dotin.softwaresystems.librarymanagement.repository.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    public Object login(UserDTO Unauthenticateduser) {
        try {
            UserEntity userOnDB = users.findByUsername(UserMapper.INSTANCE.toEntity(Unauthenticateduser));
            if (userOnDB.getPassword().equals(Unauthenticateduser.getPassword())) {
                sessionRepository.createUserSession(userOnDB);
                return Unauthenticateduser;
            } else {
                return "password incorrect";
            }
        } catch (UserNotFoundException e) {
            return "username not found";
        }
    }

    public Object signup(UserDTO Unauthenticateduser) {
        Unauthenticateduser.setRole(Role.MEMBER);
        try {
            UserEntity userOnDB=users.save(UserMapper.INSTANCE.toEntity(Unauthenticateduser));
            sessionRepository.createUserSession(userOnDB);
            return Unauthenticateduser;
        } catch (Exception exep) {
            log.error("save user not success:{}", exep.getMessage(), exep);
            return "save user not success";
        }
    }

    public UserDTO getUserById(UserEntity user) throws Exception {
        return UserMapper.INSTANCE.toDto(users.findById(user.getId()).orElseThrow(Exception::new));
    }

    public UserEntity getUserByUsername(UserDTO user) throws Exception {
        return users.findByUsername(UserMapper.INSTANCE.toEntity(user));
    }
}