package ir.dotin.softwaresystems.librarymanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "role",nullable = false)
    private String role;

    public UserEntity() {
    }

    public UserEntity(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserEntity(long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserEntity(Long userId) {
        this.id = userId;
    }
}
