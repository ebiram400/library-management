package ir.dotin.softwaresystems.librarymanagement.service;

import ir.dotin.softwaresystems.librarymanagement.dto.BookStatus;
import ir.dotin.softwaresystems.librarymanagement.dto.Bookdto;
import ir.dotin.softwaresystems.librarymanagement.dto.GoogleBooksdto;
import ir.dotin.softwaresystems.librarymanagement.dto.VolumeInfo;
import ir.dotin.softwaresystems.librarymanagement.entity.BookEntity;
import ir.dotin.softwaresystems.librarymanagement.mapper.BookMapper;
import ir.dotin.softwaresystems.librarymanagement.repository.Books;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Service
public class BookService {
    private Books books;
    private WebClient webClient;
    private RequestService requestService;

    public BookService() {
    }

    @Autowired
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Autowired
    public void setBooks(Books books) {
        this.books=books;
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService=requestService;
    }

    public ArrayList<Bookdto> watchBooks() throws Exception {
        try{
            return books.findAll().parallelStream().map(BookMapper.INSTANCE::toDto).collect(Collectors.toCollection(ArrayList::new));
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("watch books failed");
        }
    }

    public VolumeInfo offerBooks() throws Exception {
        try{
            String category=findMostCategory();
            GoogleBooksdto googleBooksdto = webClient.get().uri(uriBuilder -> uriBuilder.path("books/v1/volumes")
                                    .queryParam("q",category)
                                    .queryParam("langRestrict","fa")
                                    .queryParam("filter","free-ebooks")
                                    .queryParam("orderBy","newest")
                                    .queryParam("key","AIzaSyAWpNAJInTypk6Vcben-4VymviQdtbbnRQ")
                                    .build())
                            .retrieve().bodyToMono(GoogleBooksdto.class).block();
            assert googleBooksdto != null : "your request offer failed";
            googleBooksdto.getVolumeInfo().setCategory(category);
            return googleBooksdto.getVolumeInfo();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("your request offer failed");
        }
    }

    public Bookdto addBooks(Bookdto book) throws Exception {
        try{
            books.save(BookMapper.INSTANCE.toEntity(book));
            return book;
        }catch(Exception e){
            log.error(e.getMessage());
            throw new Exception("add book fail!");
        }
    }

    public Bookdto deleteBooks(Bookdto book) throws Exception {
        try{
            books.delete(BookMapper.INSTANCE.toEntity(book));
            return book;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("delete book failed");
        }
    }

    public BookEntity findBook(Bookdto book) throws Exception {
            return books.searchAllByBookNameAndAuthorAndPublisher(book.getBookName(), book.getAuthor(), book.getPublisher())
                    .parallelStream().filter(firstBook->firstBook.getStatus().equals(BookStatus.BOOKABLE)).findFirst().orElseThrow(Exception::new);
    }

    public Bookdto findBookById(BookEntity book) throws Exception {
        try{
            return BookMapper.INSTANCE.toDto(books.findById(book.getId()).orElseThrow(Exception::new));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BookEntity findBookEntityById(Bookdto book) throws Exception {
        try {
            return books.findById(book.getId()).orElseThrow(Exception::new);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatusBook(Bookdto book) throws Exception {
        books.save(BookMapper.INSTANCE.toEntity(book));
    }

    public String findMostCategory() throws Exception {
        return requestService.userRequests().stream()
                .map(r->r.getBook().getCategory())
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("computer");
    }
}
