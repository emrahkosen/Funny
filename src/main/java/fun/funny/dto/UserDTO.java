package fun.funny.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserDTO {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private String nationality;
    private String password;
    private Set<String> roles; // Assuming roles contain only role names
    private List<Long> readBookIds; // Assuming books contain only IDs in DTO
    private List<Long> followingBookIds; // Assuming books contain only IDs in DTO

    public UserDTO() {
    }

    // Getter and Setter methods

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public List<Long> getReadBookIds() {
        return readBookIds;
    }

    public void setReadBookIds(List<Long> readBookIds) {
        this.readBookIds = readBookIds;
    }

    public List<Long> getFollowingBookIds() {
        return followingBookIds;
    }

    public void setFollowingBookIds(List<Long> followingBookIds) {
        this.followingBookIds = followingBookIds;
    }
}

