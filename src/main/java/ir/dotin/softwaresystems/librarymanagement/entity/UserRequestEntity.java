package ir.dotin.softwaresystems.librarymanagement.entity;

import ir.dotin.softwaresystems.librarymanagement.dto.RequestStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter @Getter
@Entity
@Table(name = "request")
public class UserRequestEntity {

    @Id
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "book_id" , nullable = false)
    private Long bookId;

    @Column(name = "requestStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    public UserRequestEntity(Long userId, Long bookId, RequestStatus requestStatus) {
        this.userId = userId;
        this.bookId = bookId;
        this.requestStatus = requestStatus;
    }

    public UserRequestEntity() {
    }
}
