package ir.dotin.softwaresystems.librarymanagement.converter;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PasswordConverter implements AttributeConverter<String, String>{

    @Override
    public String convertToDatabaseColumn(String password) {
        return BCrypt.hashpw(password,password);
    }

    @Override
    public String convertToEntityAttribute(String password) {
        return password;
    }

}
