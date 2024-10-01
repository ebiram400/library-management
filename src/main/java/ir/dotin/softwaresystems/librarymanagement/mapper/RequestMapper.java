package ir.dotin.softwaresystems.librarymanagement.mapper;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.RequestStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Requestdto;
import ir.dotin.softwaresystems.librarymanagement.entity.BookEntity;
import ir.dotin.softwaresystems.librarymanagement.entity.UserEntity;
import ir.dotin.softwaresystems.librarymanagement.entity.UserRequestEntity;
import ir.dotin.softwaresystems.librarymanagement.repository.Requests;
import ir.dotin.softwaresystems.librarymanagement.repository.SessionRepository;
import ir.dotin.softwaresystems.librarymanagement.repository.Users;
import ir.dotin.softwaresystems.librarymanagement.service.Authentication;
import ir.dotin.softwaresystems.librarymanagement.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;

@Slf4j
@Mapper
public class RequestMapper {

    private final BookService bookService;
    private final Authentication authentication;
    @Autowired
    public RequestMapper(BookService bookService, Authentication authentication) {
        this.bookService = bookService;
        this.authentication = authentication;
    }

    public UserRequestEntity toEntity(Bookdto book, SessionRepository sessionRepository) {
        try {
            return new UserRequestEntity(sessionRepository.getUserIdSession(),bookService.findBook(book).getId(),RequestStatus.PENDING_APPROVAL);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public Requestdto toDto(UserRequestEntity requestentity) throws Exception {
        try{
            return new Requestdto(authentication.getUserById(new UserEntity(requestentity.getUserId())),bookService.findBookById(new BookEntity(requestentity.getBookId())),requestentity.getRequestStatus());
        }catch(Exception e){
            log.error("failed to map Request Entity to Dto:{}", e.getMessage(), e);
            throw new Exception(e.getMessage(),e);
        }
    }

    public UserRequestEntity toEntity(Requestdto requestdto) throws Exception {
        try {
            return new UserRequestEntity(authentication.getUserByUsername(requestdto.getUser()).getId(),requestdto.getBook().getId(),requestdto.getRequestStatus());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
