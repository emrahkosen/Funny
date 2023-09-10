package fun.funny.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String title;
    private String isbn;
    private Integer publishedYear;
    private String genre;

    @ManyToMany
    @JoinTable(
            name = "AuthorBook",
            joinColumns = @JoinColumn(name = "bookId"),
            inverseJoinColumns = @JoinColumn(name = "authorId")
    )
    private List<Author> authors;

    @ManyToMany(mappedBy = "readBooks")
    private List<User> readers;
}
