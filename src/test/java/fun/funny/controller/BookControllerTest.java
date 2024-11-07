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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = "spring.security.enabled=false")
public class BookControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @WithMockUser
    public void getBookByIdNotFound(){
        ResponseEntity<Book> entity = restTemplate.getForEntity("/gray/book/4321", Book.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


    }

    @Test
    public void shouldGetBookById(){
        ResponseEntity<Book> entity = restTemplate.getForEntity("/gray/book/1234", Book.class);

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

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/gray/book", newBook, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = createResponse.getHeaders().getLocation();
        ResponseEntity<Book> createdBookResponse = restTemplate.getForEntity(location, Book.class);
        assertThat(createdBookResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book createdBook = createdBookResponse.getBody();
        assertThat(createdBook.getId()).isNotNull();
        assertThat(createdBook.getTitle()).isEqualTo(newBook.getTitle());
    }

    @Test
    void shouldReturnAllBooks(){
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "/gray/book/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Book> books = response.getBody();
        assertThat(books.size()).isEqualTo(4);

    }


    @Test
    void shouldReturnBookWithGivenIsbn(){
        ResponseEntity<Book> entity = restTemplate.getForEntity("/gray/book/isbn/ISBN1", Book.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Book body = entity.getBody();

        assertThat(body.getTitle()).isEqualTo("Book 1");
    }

    @Test
    void shouldNotReturnBookWithGivenIsbn(){
        ResponseEntity<Book> entity = restTemplate.getForEntity("/gray/book/isbn/INVALID_ISBN", Book.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void checkUniquenessOfIsbn(){
        Book newBook = new Book();
        newBook.setTitle("Already Exists ISBN Book");
        newBook.setIsbn("ISBN1");
        newBook.setPublishedYear(2024);

        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/gray/book", newBook, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);


        newBook.setIsbn("ISNB_UNIQUE");

        ResponseEntity<Void> createValidResponse = restTemplate.postForEntity("/gray/book", newBook, Void.class);
        assertThat(createValidResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }
    @Test
    void shouldReturnByPublishedYear(){
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "/gray/book/year?publishedYear=2021&page=0&size=2",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
    }

    @Test
    void shouldReturnByGenre(){
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                "/gray/book/genre?genre=Fiction&page=0&size=3",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
    }

}
