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

    @ManyToMany(mappedBy = "followingBooks")
    private List<User> followers;



    public Long getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void addReaders(User reader){
        readers.add(reader);
    }

    public void  removeReader(User reader){
        readers.remove(reader);
    }

    public void addFollower(User follower){
        followers.add(follower);
    }

    public void  removeFollower(User follower){
        followers.remove(follower);
    }
}
