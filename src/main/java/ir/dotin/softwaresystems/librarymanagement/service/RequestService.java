package ir.dotin.softwaresystems.librarymanagement.service;

import ir.dotin.softwaresystems.librarymanagement.dto.*;
import ir.dotin.softwaresystems.librarymanagement.entity.UserRequestEntity;
import ir.dotin.softwaresystems.librarymanagement.mapper.BookMapper;
import ir.dotin.softwaresystems.librarymanagement.mapper.RequestMapper;
import ir.dotin.softwaresystems.librarymanagement.repository.Books;
import ir.dotin.softwaresystems.librarymanagement.repository.Requests;
import ir.dotin.softwaresystems.librarymanagement.repository.SessionRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestService {
    private final Requests requests;
    private final SessionRepository sessionRepository;
    private final Books booksRepository;
    private final RequestMapper requestMapper;
    private final BookMapper bookMapper;
    private BookService bookService;

    @Autowired
    public RequestService(Requests requests, SessionRepository sessionRepository, Books booksRepository, RequestMapper requestMapper, BookService bookService, BookMapper bookMapper) {
        this.requests = requests;
        this.sessionRepository = sessionRepository;
        this.booksRepository = booksRepository;
        this.requestMapper = requestMapper;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
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
            requests.save(requestMapper.toEntity(book,sessionRepository));
            return new Requestdto(new UserDTO(sessionRepository.getUsernameSession()),book,RequestStatus.PENDING_APPROVAL);
        }catch(Exception e){
            log.error(e.getMessage());
            throw new Exception(e.getMessage(),e);
        }
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    public Requestdto responseRequests(Requestdto requestdto) throws Exception {
        try {
            if(requestdto.getRequestStatus().equals(RequestStatus.REJECTED)){
                requests.updateStatus(requestMapper.toEntity(requestdto));
                return requestdto;
            }

            if(bookService.findBookById(requestdto.getBook()).getStatus().equals(BookStatus.NOT_BOOKABLE)){
                requestdto.getBook().setId(bookService.findBook(requestdto.getBook()).getId());
                requests.updateBookIdAndStatus(requestMapper.toEntity(requestdto));

            }
            else{requests.updateStatus(requestMapper.toEntity(requestdto));}

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
            return requests.findAllByUserId(sessionRepository.getUserIdSession()).parallelStream()
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