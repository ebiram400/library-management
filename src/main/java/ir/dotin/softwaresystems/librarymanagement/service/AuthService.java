package ir.dotin.softwaresystems.librarymanagement.service;

import ir.dotin.softwaresystems.librarymanagement.dto.Role;
import ir.dotin.softwaresystems.librarymanagement.dto.UserDTO;

import ir.dotin.softwaresystems.librarymanagement.entity.UserEntity;
import ir.dotin.softwaresystems.librarymanagement.mapper.UserMapper;
import ir.dotin.softwaresystems.librarymanagement.repository.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class AuthService {
    private final Users users;

    @Autowired
    public AuthService(Users users) {
        this.users = users;
    }

    public UserDTO signup(UserDTO Unauthenticateduser) throws Exception {
        Unauthenticateduser.setRole(Role.MEMBER);
        try {
            UserEntity userOnDB=users.save(UserMapper.INSTANCE.toEntity(Unauthenticateduser));
            return UserMapper.INSTANCE.toDto(userOnDB);
        } catch (Exception exep) {
            log.error("save user not success:{}", exep.getMessage(), exep);
            throw new Exception(exep.getMessage());
        }
    }

    public UserDTO getUserById(UserEntity user) throws Exception {
        return UserMapper.INSTANCE.toDto(users.findById(user.getId()).orElseThrow(Exception::new));
    }

    public UserEntity getUserByUsername(UserDTO user) {
        return users.findByUsername(user.getUsername());
    }

    public Long getUserIdFromContext(){
        Authentication authSecurity = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authSecurity.getPrincipal();
        return userDetails.getId();
    }
}