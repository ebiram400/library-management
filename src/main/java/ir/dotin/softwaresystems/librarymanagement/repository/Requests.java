package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.entity.UserRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface Requests extends JpaRepository<UserRequestEntity, Long> {

    ArrayList<UserRequestEntity> findAllByUserId(Long userId);

    void updateStatus(UserRequestEntity userRequestEntity);

    void updateBookIdAndStatus(UserRequestEntity userRequestEntity);
}
