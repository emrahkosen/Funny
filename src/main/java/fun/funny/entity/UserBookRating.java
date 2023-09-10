package fun.funny.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UserBookRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;
    private Integer rating;
    private String review;
    private Date ratingDate;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
}
