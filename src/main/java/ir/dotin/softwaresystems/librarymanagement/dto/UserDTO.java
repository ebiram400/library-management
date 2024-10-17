package ir.dotin.softwaresystems.librarymanagement.dto;

import ir.dotin.softwaresystems.librarymanagement.validate.ValidNationalCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;


@Getter
@Setter
public class UserDTO {
    @ValidNationalCode
    private String username;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$\n",message = "Password must be at least 8 characters long, and include at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;
    private Role role;

    public UserDTO(String username) {
        this.username = username;
    }
}
