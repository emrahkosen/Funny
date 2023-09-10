package fun.funny.entity;

import jakarta.persistence.*;


import java.util.Date;
import java.util.List;


@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorId;
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books;
}
