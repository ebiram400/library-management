package ir.dotin.softwaresystems.librarymanagement.mapper;

import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.entity.BookEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T22:30:01+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
public class BookMapperImpl implements BookMapper {

    @Override
    public BookEntity toEntity(Bookdto bookdto) {
        if ( bookdto == null ) {
            return null;
        }

        BookEntity bookEntity = new BookEntity();

        bookEntity.setId( bookdto.getId() );
        bookEntity.setBookName( bookdto.getBookName() );
        bookEntity.setAuthor( bookdto.getAuthor() );
        bookEntity.setPublisher( bookdto.getPublisher() );
        bookEntity.setStatus( bookdto.getStatus() );
        bookEntity.setTheLastRecipient( bookdto.getTheLastRecipient() );
        bookEntity.setLastDateOfReservation( bookdto.getLastDateOfReservation() );

        return bookEntity;
    }

    @Override
    public Bookdto toDto(BookEntity bookentity) {
        if ( bookentity == null ) {
            return null;
        }

        Bookdto bookdto = new Bookdto();

        bookdto.setId( bookentity.getId() );
        bookdto.setBookName( bookentity.getBookName() );
        bookdto.setAuthor( bookentity.getAuthor() );
        bookdto.setPublisher( bookentity.getPublisher() );
        bookdto.setStatus( bookentity.getStatus() );
        bookdto.setTheLastRecipient( bookentity.getTheLastRecipient() );
        bookdto.setLastDateOfReservation( bookentity.getLastDateOfReservation() );

        return bookdto;
    }
}
