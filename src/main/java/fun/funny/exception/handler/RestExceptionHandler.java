package fun.funny.exception.handler;

import fun.funny.entity.Customer;
import fun.funny.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
            (CustomerNotFoundException.class)
    public ResponseEntity<Customer> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


}