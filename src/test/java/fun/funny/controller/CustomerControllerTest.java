package fun.funny.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import fun.funny.entity.Customer;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getCustomer()  {
        ResponseEntity<Customer> response = restTemplate.getForEntity("/customer", Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Customer customer = response.getBody();
        assertNotNull(customer);
        assertThat(customer.getFirstName()).isEqualTo("emrah");
        assertThat(customer.getLastName()).isEqualTo("kosen");
    }

    @Test
    public void getById(){
        ResponseEntity<Customer> response = restTemplate.getForEntity("/customer/111", Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Customer customer = response.getBody();
        assertNotNull(customer);
        assertThat(customer.getFirstName()).isEqualTo("John2");

    }

    @Test
    public void getByIdNotFound(){
        ResponseEntity<Customer> response = restTemplate.getForEntity("/customer/1231", Customer.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }


    @Test
        //@DirtiesContext
    void shouldCreateANewCustomer() {
        Customer newCustomer = new Customer("emrah", "kosen");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/customer", newCustomer, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = createResponse.getHeaders().getLocation();
        ResponseEntity<Customer> entityResponse = restTemplate.getForEntity(location, Customer.class);
        assertThat(entityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(entityResponse.getBody());
        assertThat(entityResponse.getBody().getId()).isNotNull();
        assertThat(entityResponse.getBody().getFirstName()).isEqualTo("emrah");

    }


    @Test
    void shouldReturnAllCustomers() {
       // ResponseEntity<List> response = restTemplate.getForEntity("/customer/all", List.class);
        ResponseEntity<List<Customer>> response = restTemplate.exchange(
                "/customer/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Customer>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Customer> body = response.getBody();
        assertNotNull(body);
        assertThat(body.size()).isEqualTo(4);

    }

    @Test
    void shouldReturnAllCustomersInAString(){
        ResponseEntity<String> stResponse = restTemplate.getForEntity("/customer/all", String.class);
        assertThat(stResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(stResponse.getBody());
        int customerCount = documentContext.read("$.length()");
        assertThat(customerCount).isEqualTo(4);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(111, 211, 311, 411);

    }

    @Test
    void shouldReturnPageOfCustomer(){
        ResponseEntity<String> response = restTemplate.getForEntity("/customer/all?page=0&size=2", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());

        JSONArray page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(2);
    }

    @Test
    void shouldReturnPageOfCustomerDecsOrder(){
        ResponseEntity<String> response = restTemplate.getForEntity("/customer/all?page=0&size=2&sort=lastName,asc", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");

        assertThat(page.size()).isEqualTo(2);

        String lastName = documentContext.read("$[0].lastName");
        assertThat(lastName).isEqualTo("aSmith");
    }


    @Test
    void shouldReturnPageOfCustomerDefaultOrder(){
        ResponseEntity<String> response = restTemplate.getForEntity("/customer/all?page=0&size=2", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        JSONArray page = documentContext.read("$[*]");

        assertThat(page.size()).isEqualTo(2);

        String lastName = documentContext.read("$[0].firstName");
        assertThat(lastName).isEqualTo("Jane1");
    }




}
