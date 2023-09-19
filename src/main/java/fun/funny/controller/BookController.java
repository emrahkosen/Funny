package fun.funny.controller;


import fun.funny.entity.Book;
import fun.funny.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id){
        Optional<Book> bookOpt = bookRepository.findById(id);

        if(bookOpt.isPresent()){
            return ResponseEntity.ok(bookOpt.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> saveBook(@RequestBody Book newBook, UriComponentsBuilder ucb){
        Book savedBook = bookRepository.save(newBook);
        URI locationOfNewCustomer =ucb.path("/book/{id}")
                .buildAndExpand(savedBook.getBookId())
                .toUri();

        return ResponseEntity.created(locationOfNewCustomer).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> findAll(Pageable pageable){
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "title"))));

        return ResponseEntity.ok(bookPage.getContent());
    }
}
