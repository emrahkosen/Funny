package fun.funny.controller;

import fun.funny.dto.UserDTO;
import fun.funny.service.UserService;  
import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;  
import org.mockito.MockitoAnnotations;  
import org.springframework.http.HttpStatus;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;  

import java.net.URI;  
import java.util.Arrays;  
import java.util.List;  

import static org.junit.jupiter.api.Assertions.*;  
import static org.mockito.Mockito.*;  


class UserControllerMockTest {
    
    @InjectMocks  
    private UserController userController;  

    @Mock  
    private UserService userService;  

    private UserDTO userDTO;  
    
    @BeforeEach  
    void setUp() {  
        MockitoAnnotations.openMocks(this);  
        userDTO = new UserDTO();
        userDTO.setUserId(1L);
        userDTO.setUsername("testuser");  
        // Set other necessary fields  
    }  
/*TODO
    @Test  
    void createUser_ShouldReturnCreatedUser() {  
        when(userService.createUser(userDTO)).thenReturn(userDTO);
        
        ResponseEntity<UserDTO> response = userController.createUser(userDTO);  
        
        // Verify the response status and body  
        assertEquals(HttpStatus.CREATED, response.getStatusCode());  
        assertEquals(userDTO, response.getBody());  

        // Check if the Location header is set correctly  
        URI expectedLocation = ServletUriComponentsBuilder.fromCurrentRequest()  
                .path("/{id}")  
                .buildAndExpand(userDTO.getUserId())
                .toUri();  
        assertEquals(expectedLocation, response.getHeaders().getLocation());  
    }  
*/
    @Test  
    void findAllUsers_ShouldReturnListOfUsers() {  
        UserDTO user1 = new UserDTO();  
        user1.setUserId(1L);
        UserDTO user2 = new UserDTO();  
        user2.setUserId(2L);

        List<UserDTO> userList = Arrays.asList(user1, user2);  
        when(userService.findAllUsers()).thenReturn(userList);  

        ResponseEntity<List<UserDTO>> response = userController.findAllUsers();  

        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(userList, response.getBody());  
    }  

    @Test  
    void findUserById_ShouldReturnUser() {  
        when(userService.findUserById(1L)).thenReturn(userDTO);  

        ResponseEntity<UserDTO> response = userController.findUserById(1L);  

        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(userDTO, response.getBody());  
    }  

    @Test  
    void updateUser_ShouldReturnUpdatedUser() {  
        UserDTO updatedUser = new UserDTO();  
        updatedUser.setUserId(1L);
        updatedUser.setUsername("updatedUser");  

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(updatedUser);  

        ResponseEntity<UserDTO> response = userController.updateUser(1L, updatedUser);  

        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(updatedUser, response.getBody());  
    }  

    @Test  
    void deleteUserById_ShouldReturnNoContent() {  
        doNothing().when(userService).deleteUserById(1L);  

        ResponseEntity<Void> response = userController.deleteUserById(1L);  

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());  
    }  

    @Test  
    void findUserByUsername_ShouldReturnUser() {  
        when(userService.findUserByUsername("testuser")).thenReturn(userDTO);  

        ResponseEntity<UserDTO> response = userController.findUserByUsername("testuser");  

        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(userDTO, response.getBody());  
    }  

    @Test  
    void findUsersByNationality_ShouldReturnListOfUsers() {  
        UserDTO user1 = new UserDTO();  
        user1.setUserId(1L);
        UserDTO user2 = new UserDTO();  
        user2.setUserId(2L);
        
        List<UserDTO> userList = Arrays.asList(user1, user2);  
        when(userService.findUsersByNationality("American")).thenReturn(userList);  

        ResponseEntity<List<UserDTO>> response = userController.findUsersByNationality("American");  

        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(userList, response.getBody());  
    }  

    // Add tests for remaining methods if needed  
}