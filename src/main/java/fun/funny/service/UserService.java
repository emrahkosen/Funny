package fun.funny.service;

import fun.funny.entity.Book;
import fun.funny.entity.Role;
import fun.funny.entity.User;
import fun.funny.repository.BookRepository;
import fun.funny.repository.RoleRepository;
import fun.funny.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> findUsersByNationality(String nationality) {
        // Implement this method to search users by nationality.
        return null;
    }

    @Transactional
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setBirthDate(updatedUser.getBirthDate());
            existingUser.setNationality(updatedUser.getNationality());
            existingUser.setRoles(updatedUser.getRoles());
            existingUser.setFollowingBooks(updatedUser.getFollowingBooks());
            existingUser.setReadBooks(updatedUser.getReadBooks());
            existingUser.setRatings(updatedUser.getRatings());
            return userRepository.save(existingUser);
        }).orElseGet(() -> {
            updatedUser.setUserId(userId);
            return userRepository.save(updatedUser);
        });
    }

    @Transactional(readOnly = true)
    public List<Book> findReadBooksByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getReadBooks).orElse(null);
    }

    @Transactional
    public void addReadBook(Long userId, Long bookId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (userOptional.isPresent() && bookOptional.isPresent()) {
            User user = userOptional.get();
            Book book = bookOptional.get();
            user.getReadBooks().add(book);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User or Book not found");
        }
    }

    @Transactional
    public void addUserRole(Long userId, Long roleId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Role> roleOptional = roleRepository.findById(roleId);

        if (userOptional.isPresent() && roleOptional.isPresent()) {
            User user = userOptional.get();
            Role role = roleOptional.get();
            user.getRoles().add(role);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User or Role not found");
        }
    }
}