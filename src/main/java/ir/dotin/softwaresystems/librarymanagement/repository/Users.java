package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
