package fun.funny.repository;

import java.util.List;

import fun.funny.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>, PagingAndSortingRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

}