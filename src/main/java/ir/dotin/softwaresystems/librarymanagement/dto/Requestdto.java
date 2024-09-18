package ir.dotin.softwaresystems.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Requestdto {
    private int id;
    private int user_id;
    private int book_id;
    private RequestStatus requestStatus = RequestStatus.PENDING_APPROVAL;

    public Requestdto() {
    }

    public Requestdto(int user_id, int book_id) {
        this.user_id = user_id;
        this.book_id = book_id;
    }

    public Requestdto(int user_id, int book_id, RequestStatus requestStatus) {
        this.user_id = user_id;
        this.book_id = book_id;
        this.requestStatus = requestStatus;
    }
}
