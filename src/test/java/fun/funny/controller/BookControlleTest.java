package fun.funny.controller;


import fun.funny.entity.Book;
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

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControlleTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getBookByIdNotFound(){
        ResponseEntity<Book> entity = restTemplate.getForEntity("/book/4321", Book.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


    }

    @Test
    public void shouldGetBookById(){
        ResponseEntity<Book> entity = restTemplate.getForEntity("/book/1234", Book.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book body = entity.getBody();

        assertThat(body.getTitle()).isEqualTo("Book 1");
    }

    @Test
    public void shouldCreateNewBook(){
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setGenre("Horror");
        newBook.setIsbn("newIsbn");
        newBook.setPublishedYear(2024);

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/book", newBook, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = createResponse.getHeaders().getLocation();
        ResponseEntity<Book> createdBookResponse = restTemplate.getForEntity(location, Book.class);
        assertThat(createdBookResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book createdBook = createdBookResponse.getBody();
        assertThat(createdBook.getBookId()).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo(newBook.getTitle());
    }

    @Test
    void shouldReturnAllCustomers(){
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "/book/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Book> books = response.getBody();
        assertThat(books.size()).isEqualTo(4);

    }

}
