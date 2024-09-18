package ir.dotin.softwaresystems.librarymanagement.service;

import com.google.gson.Gson;
import ir.dotin.softwaresystems.librarymanagement.dto.BookStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.RequestStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Requestdto;
import ir.dotin.softwaresystems.librarymanagement.repository.Books;
import ir.dotin.softwaresystems.librarymanagement.repository.Requests;
import ir.dotin.softwaresystems.librarymanagement.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class RequestService {
    private Requests requests;
    private SessionRepository sessionRepository;
    private Books booksRepository;

    @Autowired
    public RequestService(Requests requests, SessionRepository sessionRepository, Books booksRepository) {
        this.requests = requests;
        this.sessionRepository = sessionRepository;
        this.booksRepository = booksRepository;
    }

    public String watchRequests(){
        try{
            return new Gson().toJson(requests.watchRequest());
        }catch(Exception e){
            return "get requests failed";
        }
    }

    public String addRequest(Bookdto book){
        try{
            requests.addRequest(new Requestdto(bookTargetId(book),sessionRepository.getUserIdSession()));
            return "add request success";
        }catch(Exception e){
            log.error(e.getMessage());
            return "add request failed";
        }
    }

    public String responseRequests(Requestdto requestdto){
        try {
            requests.updateRequestStatus(requestdto);
            if (requestdto.getRequestStatus().equals(RequestStatus.APPROVED)){
                try {
                    booksRepository.updateStatusBook(requestdto);
                }catch (Exception e){
                    requestdto.setRequestStatus(RequestStatus.PENDING_APPROVAL);
                    requests.updateRequestStatus(requestdto);
                    return "update book Status failed";
                }
            }
            return "update request success";
        }catch(Exception e){
            return "update request failed";
        }
    }

    public String userRequests(){
        try{
            return new Gson().toJson(requests.watchRequest(sessionRepository.getUserIdSession()));
        }catch(Exception e){
            return "get requests failed";
        }
    }

    public Integer bookTargetId(Bookdto book){
        try{
            ArrayList<Bookdto> booksTarget= booksRepository.findBook(book);
            for(Bookdto b:booksTarget){
                if(b.getStatus().equals(BookStatus.BOOKABLE)){
                    return b.getBook_id();
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException();
        }
        return 0;
    }
}