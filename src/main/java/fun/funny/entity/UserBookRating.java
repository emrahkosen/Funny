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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    public void setBook(Book book){
        this.book = book;
    }

    public void setUser(User user){
        this.user = user;
    }

}
