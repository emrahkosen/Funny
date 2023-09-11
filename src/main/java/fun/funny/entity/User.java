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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }



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

    public void addReadBook(Book book){
        readBooks.add(book);
    }

    public void removeReadBook(Book book){
        readBooks.remove(book);
    }

    public void addFollowingBooks(Book book){
        followingBooks.add(book);
    }

    public void removeFollowingBooks(Book book){
        followingBooks.remove(book);
    }
}
