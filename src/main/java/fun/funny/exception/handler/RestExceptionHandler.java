package fun.funny.exception.handler;

import fun.funny.entity.Customer;
import fun.funny.exception.CustomerNotFoundException;
import fun.funny.exception.UserNotFoundException;
import fun.funny.exception.UsernameAlreadyExistsException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid JWT token: The token format is incorrect.");
    }



    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException(SignatureException ex) {
        System.err.println("Invalid JWT signature: " + ex.getMessage());

        // Return a 401 Unauthorized response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid JWT signature: Unauthorized access.");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException ex) {
        System.err.println("Expired JWT token: " + ex.getMessage());

        // Return a 401 Unauthorized response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("JWT token has expired. Please login again.");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        System.err.println("JWT error: " + ex.getMessage());

        // Return a 400 Bad Request response for general JWT errors
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("JWT processing error: " + ex.getMessage());
    }

}