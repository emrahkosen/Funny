package fun.funny.service;  

import fun.funny.converter.UserModelConverter;
import fun.funny.dto.UserDTO;  
import fun.funny.entity.Book;  
import fun.funny.entity.Role;  
import fun.funny.entity.User;  
import fun.funny.exception.UserNotFoundException;  
import fun.funny.repository.BookRepository;  
import fun.funny.repository.RoleRepository;  
import fun.funny.repository.UserRepository;  
import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;  
import static org.mockito.ArgumentMatchers.any;  
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceMockTest {

    @Mock  
    private UserRepository userRepository;  

    @Mock  
    private BookRepository bookRepository;  

    @Mock  
    private RoleRepository roleRepository;


    @Mock  
    private UserModelConverter userModelConverter;  

    @InjectMocks  
    private UserService userService;  

    private User user;  
    private UserDTO userDTO;  

    @BeforeEach  
    void setUp() {  
//        MockitoAnnotations.openMocks(this);

        user = new User();  
        user.setId(1L);  
        user.setUsername("testUser");  
        user.setPassword("plainPassword");  
        user.setReadBooks(new ArrayList<>());
        user.setRoles(new HashSet<>());  

        userDTO = new UserDTO();  
        userDTO.setUserId(1L);
        userDTO.setUsername("testUser");  
        userDTO.setPassword("plainPassword");  
    }  

    @Test  
    void findAllUsers() {  
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));  
        when(userModelConverter.entity2Dto(user)).thenReturn(userDTO);  

        List<UserDTO> result = userService.findAllUsers();  

        assertEquals(1, result.size());  
        assertEquals(userDTO, result.get(0));  
    }  

    @Test  
    void findUserById_UserFound() {  
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));  
        when(userModelConverter.entity2Dto(user)).thenReturn(userDTO);  

        UserDTO result = userService.findUserById(1L);  

        assertNotNull(result);  
        assertEquals(userDTO, result);  
    }  

    @Test  
    void findUserById_UserNotFound() {  
        when(userRepository.findById(1L)).thenReturn(Optional.empty());  

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findUserById(1L));  
        assertNotNull(exception);  
    }  

    @Test  
    void createUser() {  
        //when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userModelConverter.createDto2Entity(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);  
        when(userModelConverter.entity2Dto(user)).thenReturn(userDTO);  

        UserDTO result = userService.createUser(userDTO);  
        assertNotNull(result);
        assertEquals(userDTO.getUsername(), result.getUsername());
        verify(userRepository).save(user);  
    }  

    @Test  
    void deleteUserById() {  
        doNothing().when(userRepository).deleteById(1L);  

        userService.deleteUserById(1L);  

        verify(userRepository).deleteById(1L);  
    }  

    @Test  
    void findUserByUsername_UserFound() {  
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));  
        when(userModelConverter.entity2Dto(user)).thenReturn(userDTO);  

        UserDTO result = userService.findUserByUsername("testUser");  

        assertNotNull(result);  
        assertEquals(userDTO, result);  
    }  

    @Test  
    void findUserByUsername_UserNotFound() {  
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());  

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findUserByUsername("testUser"));  
        assertNotNull(exception);  
    }

    @Test
    void updateUser_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // This mock ensures that the updateDto2Entity method updates the user in place
        doNothing().when(userModelConverter).updateDto2Entity(userDTO, user);
        when(userRepository.save(user)).thenReturn(user);
        when(userModelConverter.entity2Dto(user)).thenReturn(userDTO); // Assuming no changes for the DTO mapping in this case

        UserDTO updatedUserDTO = userService.updateUser(1L, userDTO);

        assertNotNull(updatedUserDTO);
        verify(userModelConverter).updateDto2Entity(userDTO, user); // Verify the update was called
        verify(userRepository).save(user); // Verify save is invoked with the updated user
    }

    @Test  
    void updateUser_UserNotFound() {  
        when(userRepository.findById(1L)).thenReturn(Optional.empty());  

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, userDTO));  
        assertNotNull(exception);  
    }  

    @Test  
    void addReadBook_UserAndBookFound() {  
        Book book = new Book();  
        book.setId(1L);  
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));  
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));  

        userService.addReadBook(1L, 1L);  

        assertTrue(user.getReadBooks().contains(book));  
        verify(userRepository).save(user);  
    }  

    @Test  
    void addReadBook_UserNotFound() {  
        when(userRepository.findById(1L)).thenReturn(Optional.empty());  

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addReadBook(1L, 1L));  
        assertEquals("User or Book not found", exception.getMessage());  
    }  

    @Test  
    void addUserRole_UserAndRoleFound() {  
        Role role = new Role();  
        role.setId(1L);  

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));  
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));  

        userService.addUserRole(1L, 1L);  

        assertTrue(user.getRoles().contains(role));  
        verify(userRepository).save(user);  
    }  

    @Test  
    void addUserRole_UserNotFound() {  
        when(userRepository.findById(1L)).thenReturn(Optional.empty());  

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.addUserRole(1L, 1L));  
        assertEquals("User or Role not found", exception.getMessage());  
    }  
}