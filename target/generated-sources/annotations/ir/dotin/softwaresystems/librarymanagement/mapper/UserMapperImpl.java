package ir.dotin.softwaresystems.librarymanagement.mapper;

import ir.dotin.softwaresystems.librarymanagement.dto.Role;
import ir.dotin.softwaresystems.librarymanagement.dto.UserDTO;
import ir.dotin.softwaresystems.librarymanagement.entity.UserEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T22:30:02+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(UserDTO userdTO) {
        if ( userdTO == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername( userdTO.getUsername() );
        userEntity.setPassword( userdTO.getPassword() );
        if ( userdTO.getRole() != null ) {
            userEntity.setRole( userdTO.getRole().name() );
        }

        return userEntity;
    }

    @Override
    public UserDTO toDto(UserEntity userentity) {
        if ( userentity == null ) {
            return null;
        }

        String username = null;

        username = userentity.getUsername();

        UserDTO userDTO = new UserDTO( username );

        if ( userentity.getRole() != null ) {
            userDTO.setRole( Enum.valueOf( Role.class, userentity.getRole() ) );
        }

        return userDTO;
    }
}
