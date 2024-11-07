package fun.funny.controller;  

import fun.funny.dto.UserDTO;  
import fun.funny.entity.Book;  
import fun.funny.service.UserService;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.http.HttpStatus;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController  
@RequestMapping("/black/users")
public class UserController {  

    private final UserService userService;  

    @Autowired  
    public UserController(UserService userService) {  
        this.userService = userService;  
    }  

    @GetMapping  
    public ResponseEntity<List<UserDTO>> findAllUsers() {  
        List<UserDTO> users = userService.findAllUsers();  
        return new ResponseEntity<>(users, HttpStatus.OK);  
    }  

    @GetMapping("/{userId}")  
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId) {  
        UserDTO userDTO = userService.findUserById(userId);  
        return new ResponseEntity<>(userDTO, HttpStatus.OK);  
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getUserId()) // Assuming getId() returns the ID of the created user
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping("/{userId}")  
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {  
        UserDTO updatedUser = userService.updateUser(userId, userDTO);  
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);  
    }  

    @DeleteMapping("/{userId}")  
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {  
        userService.deleteUserById(userId);  
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
    }  

    @GetMapping("/username/{username}")  
    public ResponseEntity<UserDTO> findUserByUsername(@PathVariable String username) {  
        UserDTO userDTO = userService.findUserByUsername(username);  
        return new ResponseEntity<>(userDTO, HttpStatus.OK);  
    }  

    @GetMapping("/nationality/{nationality}")  
    public ResponseEntity<List<UserDTO>> findUsersByNationality(@PathVariable String nationality) {  
        List<UserDTO> users = userService.findUsersByNationality(nationality);  
        return new ResponseEntity<>(users, HttpStatus.OK);  
    }  

    @GetMapping("/{userId}/read-books")  
    public ResponseEntity<List<Book>> findReadBooksByUserId(@PathVariable Long userId) {  
        List<Book> books = userService.findReadBooksByUserId(userId);  
        return new ResponseEntity<>(books, HttpStatus.OK);  
    }  

    @PostMapping("/{userId}/read-books/{bookId}")  
    public ResponseEntity<Void> addReadBook(@PathVariable Long userId, @PathVariable Long bookId) {  
        userService.addReadBook(userId, bookId);  
        return new ResponseEntity<>(HttpStatus.CREATED);  
    }  

    @PostMapping("/{userId}/roles/{roleId}")  
    public ResponseEntity<Void> addUserRole(@PathVariable Long userId, @PathVariable Long roleId) {  
        userService.addUserRole(userId, roleId);  
        return new ResponseEntity<>(HttpStatus.CREATED);  
    }  
}