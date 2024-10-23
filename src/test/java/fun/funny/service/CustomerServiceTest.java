package fun.funny.service;

import fun.funny.FunnyApplication;
import fun.funny.entity.Customer;
import fun.funny.exception.CustomerNotFoundException;
import fun.funny.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = FunnyApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerServiceTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
       // customerRepository.deleteAll();
        Customer customer = new Customer("John", "Doe");
        customer.setId(444L);
        //customerRepository.save(customer);
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(999L));
    }

    @Test
    void testFindById() {
        Optional<Customer> customer = customerRepository.findById(111L);
        if (customer.isPresent()) {
            assertEquals("John2", customer.get().getFirstName());
            assertEquals("dDoe", customer.get().getLastName());
        } else {
            fail("Customer not found");
        }
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer("Jane", "Smith");
        Customer savedCustomer = customerService.save(customer);
        assertNotNull(savedCustomer);
        assertEquals("Jane", savedCustomer.getFirstName());
        assertEquals("Smith", savedCustomer.getLastName());
    }

    @Test
    void testFindAll() {
        Iterable<Customer> customers = customerService.findAll();
        assertNotNull(customers);
        assertEquals(4, customers.spliterator().getExactSizeIfKnown());
        assertTrue(customers.iterator().hasNext());
    }

    @Test
    void testFindAllPageable() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Customer> page = customerService.findAll(PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "firstName"))
        ));
        assertNotNull(page);
        assertEquals(2, page.getNumberOfElements());
        assertEquals(4, page.getTotalElements());
        assertEquals("Jane1", page.get().findFirst().get().getFirstName());
    }

    @Test
    void testDeleteById() {
        Customer customer = new Customer("Jane", "Smith");
        Customer savedCustomer = customerService.save(customer);
        Long id = savedCustomer.getId();
        customerService.deleteById(id);
        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(id));
    }

}