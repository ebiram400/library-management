package ir.dotin.softwaresystems.librarymanagement.dto;

import ir.dotin.softwaresystems.librarymanagement.service.MyUserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
public class Requestdto {
    private UserDTO user;
    private Bookdto book;
    private RequestStatus requestStatus = RequestStatus.PENDING_APPROVAL;


    public Requestdto(UserDTO user, Bookdto book, RequestStatus requestStatus) {
        this.user = user;
        this.book = book;
        this.requestStatus = requestStatus;
    }

}
