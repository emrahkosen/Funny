package fun.funny.controller;

import fun.funny.entity.Customer;
import fun.funny.repository.CustomerRepository;
import fun.funny.service.CustomerService;
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
@RequestMapping("gray/customer")
public class CustomerController {
    CustomerService customerService;
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }
    @GetMapping
    public ResponseEntity<Customer> getDummyCustomer(){
        Customer customer = new Customer("emrah", "kosen");
        return ResponseEntity.ok(customer);
    }


    @GetMapping("/{requestedId}")
    public ResponseEntity<Customer> findById(@PathVariable Long requestedId) {
        Customer customer = customerService.findById(requestedId);
        return ResponseEntity.ok(customer);

    }
    @PostMapping
    public ResponseEntity<Void> saveCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder ucb){

        Customer savedCustomer = customerService.save(newCustomer);
        URI locationOfNewCustomer = ucb.path("/gray/customer/{id}")
                .buildAndExpand(savedCustomer.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCustomer).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> findAll(Pageable pageable){

        Page<Customer> page = customerService.findAll(PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "firstName"))
        ));

        return ResponseEntity.ok(page.getContent());
    }


}
