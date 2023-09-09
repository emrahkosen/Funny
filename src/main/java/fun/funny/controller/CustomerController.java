package fun.funny.controller;

import fun.funny.entity.Customer;
import fun.funny.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    CustomerRepository customerRepository;
    public CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @GetMapping
    public ResponseEntity<Customer> getDummyCustomer(){
        Customer customer = new Customer("emrah", "kosen");
        return ResponseEntity.ok(customer);
    }


    @GetMapping("/{requestedId}")
    public ResponseEntity<Customer> findById(@PathVariable Long requestedId) {
        Optional<Customer> customerOptional = customerRepository.findById(requestedId);


        if(customerOptional.isPresent()){
            return ResponseEntity.ok(customerOptional.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<Void> saveCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder ucb){

        Customer savedCustomer = customerRepository.save(newCustomer);
        URI locationOfNewCustomer = ucb.path("/customer/{id}")
                .buildAndExpand(savedCustomer.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCustomer).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll(Pageable pageable){

        Page<Customer> page = customerRepository.findAll(
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "firstName"))
                ));

        return ResponseEntity.ok(page.getContent());
    }


}
