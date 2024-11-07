package fun.funny.service;

import fun.funny.converter.ModelConverter;
import fun.funny.converter.UserModelConverter;
import fun.funny.dto.UserDTO;
import fun.funny.entity.Book;
import fun.funny.entity.Role;
import fun.funny.entity.User;
import fun.funny.exception.UserNotFoundException;
import fun.funny.exception.UsernameAlreadyExistsException;
import fun.funny.repository.BookRepository;
import fun.funny.repository.RoleRepository;
import fun.funny.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserModelConverter userModelConverter;
    private UserRepository userRepository;
    private BookRepository bookRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final ModelConverter<User, UserDTO> modelConverter;
    public UserService(UserRepository userRepository,
                       BookRepository bookRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       ModelConverter<User, UserDTO> modelConverter, UserModelConverter userModelConverter) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelConverter = modelConverter;
        this.userModelConverter = userModelConverter;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userModelConverter::entity2Dto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userModelConverter.entity2Dto(user);
    }


    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(userDTO.getUsername());
        }
        User user = modelConverter.createDto2Entity(userDTO);
        User saved = userRepository.save(user);
        return userModelConverter.entity2Dto(saved);
    }

    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return userModelConverter.entity2Dto(user);

    }

    @Transactional(readOnly = true)
    public List<UserDTO> findUsersByNationality(String nationality) {
        return userRepository.findByNationality(nationality).stream()
                .map(userModelConverter::entity2Dto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO updateUserDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userModelConverter.updateDto2Entity(updateUserDTO, existingUser);
        userRepository.save(existingUser); // Save the updated user entity

        return modelConverter.entity2Dto(existingUser);
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