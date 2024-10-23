package fun.funny.service;

import java.util.List;

import fun.funny.entity.Customer;
import fun.funny.exception.CustomerNotFoundException;
import fun.funny.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}