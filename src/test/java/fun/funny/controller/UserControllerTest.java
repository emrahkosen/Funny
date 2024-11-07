package fun.funny.controller;


import fun.funny.dto.UserDTO;
import fun.funny.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = "spring.security.enabled=false")
public class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getUserByIdTest() {
        ResponseEntity<UserDTO> response = restTemplate.getForEntity("/black/users/11111", UserDTO.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals("emrahkosen", response.getBody().getUsername());
    }

    @Test
    public void getUserByIdNotFoundTest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/black/users/123424", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
        //@DirtiesContext
    void createANewUserTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("emrahkosen123123");
        userDTO.setPassword("123456");
        userDTO.setEmail("emrahkosen@gmail.com");
        userDTO.setFirstName("emrah");
        userDTO.setLastName("Kosen");
        ResponseEntity<UserDTO> createResponse = restTemplate.postForEntity("/black/users", userDTO, UserDTO.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = createResponse.getHeaders().getLocation();
        ResponseEntity<UserDTO> entityResponse = restTemplate.getForEntity(location, UserDTO.class);
        assertThat(entityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(entityResponse.getBody());
        assertThat(entityResponse.getBody().getUserId()).isNotNull();
        assertThat(entityResponse.getBody().getFirstName()).isEqualTo("emrah");

    }

    @Test
    void createANewUserFailTest() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("emrahkosen2");
        userDTO.setPassword("123456");
        userDTO.setEmail("emrahkosen@gmail.com");
        userDTO.setFirstName("emrah");
        userDTO.setLastName("Kosen");
        ResponseEntity<?> createResponse = restTemplate.postForEntity("/black/users", userDTO, String.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }


}
