package ir.dotin.softwaresystems.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Userdto {
    private int id;
    private String username;
    private String password;
    private Role role;

    public Userdto() {
    }

    public Userdto(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
