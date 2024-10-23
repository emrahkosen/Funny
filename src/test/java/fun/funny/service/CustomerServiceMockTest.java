package fun.funny.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import fun.funny.entity.Customer;
import fun.funny.exception.CustomerNotFoundException;
import fun.funny.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceMockTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer1;
    private Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer();
        customer1.setId(1L);
        customer1.setFirstName("John");
        customer1.setLastName("Doe");

        customer2 = new Customer();
        customer2.setId(2L);
        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");
    }

    @Test
    void testFindAll() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<Customer> customers = customerService.findAll();
        assertEquals(2, customers.size());
        assertEquals("John", customers.get(0).getFirstName());
        assertEquals("Jane", customers.get(1).getFirstName());
    }

    @Test
    void testFindByIdFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        Customer customer = customerService.findById(1L);
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
    }

    @Test
    void testFindByIdNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.findById(1L));
    }

    @Test
    void testSave() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);

        Customer savedCustomer = customerService.save(customer1);
        assertEquals("John", savedCustomer.getFirstName());
    }

    @Test
    void testDeleteById() {
        customerService.deleteById(1L);
    }
}