package fun.funny.repository;

import fun.funny.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIsbn(String isbn);
    @Query("SELECT b FROM Book b WHERE b.publishedYear = :publishedYear")
    Page<Book> findAllByPublishedYear(@Param("publishedYear") Integer publishedYear, Pageable pageable);

    Page<Book> findByPublishedYear(Integer publishedYear, Pageable pageable);
    Page<Book> findByGenre(String genre, Pageable pageable);
}
