package ir.dotin.softwaresystems.librarymanagement.entity;

import ir.dotin.softwaresystems.librarymanagement.converter.PasswordConverter;
import ir.dotin.softwaresystems.librarymanagement.converter.UserNameConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @Convert(converter = UserNameConverter.class)
    @Column(name = "username",nullable = false,unique = true)
    private String username;

    @Convert(converter = PasswordConverter.class)
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "role",nullable = false)
    private String role;

    public UserEntity(Long userId) {
        this.id = userId;
    }
}
