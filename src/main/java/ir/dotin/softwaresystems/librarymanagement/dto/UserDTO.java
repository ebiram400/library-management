package ir.dotin.softwaresystems.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private Role role;

    public UserDTO(String username) {
        this.username = username;
    }
}
