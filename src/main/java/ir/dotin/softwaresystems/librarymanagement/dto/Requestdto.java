package ir.dotin.softwaresystems.librarymanagement.dto;

import ir.dotin.softwaresystems.librarymanagement.repository.SessionRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Requestdto {
    private UserDTO user;
    private Bookdto book;
    private RequestStatus requestStatus = RequestStatus.PENDING_APPROVAL;

    public Requestdto() {
    }

    public Requestdto(UserDTO user, Bookdto book, RequestStatus requestStatus) {
        this.user = user;
        this.book = book;
        this.requestStatus = requestStatus;
    }

    public Requestdto(Bookdto book,SessionRepository sessionRepository) {
        this.book = book;
        this.user=new UserDTO(sessionRepository.getUsernameSession());
    }
}
