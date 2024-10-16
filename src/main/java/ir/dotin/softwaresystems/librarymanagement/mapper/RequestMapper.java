package ir.dotin.softwaresystems.librarymanagement.mapper;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.RequestStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Requestdto;
import ir.dotin.softwaresystems.librarymanagement.entity.BookEntity;
import ir.dotin.softwaresystems.librarymanagement.entity.UserEntity;
import ir.dotin.softwaresystems.librarymanagement.entity.UserRequestEntity;
import ir.dotin.softwaresystems.librarymanagement.service.AuthService;
import ir.dotin.softwaresystems.librarymanagement.service.BookService;
import ir.dotin.softwaresystems.librarymanagement.service.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestMapper {

    private BookService bookService;
    private AuthService authentication;

    public RequestMapper() {
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setAuthService(AuthService authentication) {
        this.authentication = authentication;
    }

    public UserRequestEntity toEntity(Bookdto book) {
        try {
            Long userId = authentication.getUserIdFromContext();
            return new UserRequestEntity(userId,bookService.findBook(book).getId(),RequestStatus.PENDING_APPROVAL);
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
