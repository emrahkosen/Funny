package fun.funny.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private String nationality;

    @ManyToMany
    @JoinTable(
            name = "UserReadBooks",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "bookId")
    )
    private List<Book> readBooks;

    @ManyToMany
    @JoinTable(
            name = "UserFollowingBooks",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "bookId")
    )
    private List<Book> followingBooks;

    @OneToMany(mappedBy = "user")
    private List<UserBookRating> ratings;
}
