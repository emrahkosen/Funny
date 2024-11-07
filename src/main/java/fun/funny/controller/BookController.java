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
@RequestMapping("gray/book")
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
        URI locationOfNewCustomer = ucb.path("/gray/book/{id}")
                .buildAndExpand(savedBook.getId())
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

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getByIsbn(@PathVariable String isbn){
        Optional<Book> book = bookRepository.findBookByIsbn(isbn);
        if(book.isPresent()){
            return ResponseEntity.ok(book.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/year")
    public ResponseEntity<List<Book>> getBooksByPublishedYear(
            @RequestParam Integer publishedYear,
            Pageable pageable) {
        Page<Book> bookPage = bookRepository.findByPublishedYear(publishedYear, pageable);

        return ResponseEntity.ok(bookPage.getContent());
    }


    @GetMapping("/genre")
    public ResponseEntity<List<Book>> getBookByGenre(@RequestParam String genre, Pageable pageable){
        Page<Book> booksByGenre = bookRepository.findByGenre(genre, pageable);
        if(booksByGenre.getTotalElements() > 0){
            return ResponseEntity.ok(booksByGenre.getContent());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
