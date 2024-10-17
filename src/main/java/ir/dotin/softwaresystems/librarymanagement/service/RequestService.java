package ir.dotin.softwaresystems.librarymanagement.service;

import ir.dotin.softwaresystems.librarymanagement.dto.*;
import ir.dotin.softwaresystems.librarymanagement.entity.UserRequestEntity;
import ir.dotin.softwaresystems.librarymanagement.mapper.RequestMapper;
import ir.dotin.softwaresystems.librarymanagement.repository.Requests;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestService {
    private Requests requests;
    private RequestMapper requestMapper;
    private BookService bookService;
    private AuthService authService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setRequests(Requests requests) {
        this.requests = requests;
    }

    @Autowired
    public void setRequestMapper(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }


    public ArrayList<Requestdto> watchRequests() throws Exception {
        try{
            return requests.findAll().parallelStream()
                    .map(r->{try {
                        return requestMapper.toDto(r);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }})
                    .collect(Collectors.toCollection(ArrayList::new));
        }catch(Exception e){
            log.error(e.getMessage());
            throw new RuntimeException("get requests failed",e);
        }
    }

    @Version
    public Requestdto addRequest(Bookdto book) throws Exception {
        try{
            Authentication authSecurity = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails userDetails = (MyUserDetails) authSecurity.getPrincipal();
            String username = userDetails.getUsername();

            requests.save(requestMapper.toEntity(book));
            return new Requestdto(new UserDTO(username),book,RequestStatus.PENDING_APPROVAL);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new Exception(e.getMessage(),e);
        }
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    public Requestdto responseRequests(Requestdto requestdto) throws Exception {
        try {
            UserRequestEntity userRequest= requests.findUserRequestEntitiesByBookIdAndUserIdAndRequestStatus(requestdto.getBook().getId(),authService.getUserByUsername(requestdto.getUser()).getId(),RequestStatus.PENDING_APPROVAL);
            if(requestdto.getRequestStatus().equals(RequestStatus.REJECTED)){
                requests.updateStatus(userRequest.getId(),requestdto.getRequestStatus().toString());
                return requestdto;
            }

            if(bookService.findBookEntityById(requestdto.getBook()).getStatus().equals(BookStatus.NOT_BOOKABLE)){
                requestdto.getBook().setId(bookService.findBook(requestdto.getBook()).getId());
                requests.updateBookIdAndStatus(userRequest.getId(),requestdto.getBook().getId(),requestdto.getRequestStatus().toString());
            }
            else{requests.updateStatus(userRequest.getId(),requestdto.getRequestStatus().toString());}

            requestdto.getBook().setStatus(BookStatus.NOT_BOOKABLE);
            requestdto.getBook().setLastDateOfReservation(LocalDateTime.now());
            requestdto.getBook().setTheLastRecipient(requestdto.getUser().getUsername());
            bookService.updateStatusBook(requestdto.getBook());

            return requestdto;
        }catch(Exception e){
            throw new Exception("update request failed: "+e.getMessage(),e);
        }
    }

    public ArrayList<Requestdto> userRequests() throws Exception {
        try{
            Authentication authSecurity = SecurityContextHolder.getContext().getAuthentication();
            MyUserDetails userDetails = (MyUserDetails) authSecurity.getPrincipal();
            Long userId = userDetails.getId();

            return requests.findAllByUserId(userId).parallelStream()
                    .map(r->{try {
                        return requestMapper.toDto(r);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }})
                    .collect(Collectors.toCollection(ArrayList::new));
        }catch(Exception e){
            throw new Exception("get requests failed:"+e.getMessage());
        }
    }
}