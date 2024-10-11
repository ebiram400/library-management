package ir.dotin.softwaresystems.librarymanagement.repository;

import ir.dotin.softwaresystems.librarymanagement.dto.RequestStatus;
import ir.dotin.softwaresystems.librarymanagement.entity.UserRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface Requests extends JpaRepository<UserRequestEntity, Long> {

    ArrayList<UserRequestEntity> findAllByUserId(Long userId);

    UserRequestEntity findUserRequestEntitiesByBookIdAndUserIdAndRequestStatus(Long bookId, Long userId, RequestStatus requestStatus);

    @Modifying
    @Query(value = "UPDATE requests SET requestStatus= :requestStatus WHERE id= :id",nativeQuery = true)
    void updateStatus(@Param("id") Long Id,@Param("requestStatus") String requestStatus);

    @Modifying
    @Query(value = "UPDATE requests SET book_id= :bookId ,requestStatus= :requestStatus WHERE id= :id",nativeQuery = true)
    void updateBookIdAndStatus(@Param("id") Long Id,@Param("bookId") Long bookId,@Param("requestStatus") String status);
}
