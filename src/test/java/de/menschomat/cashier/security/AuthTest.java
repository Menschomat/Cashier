package de.menschomat.cashier.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class AuthTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLogin(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/api/users/login?username=admin&password=cashier", HttpMethod.GET, entity, String.class);
        String authHeader = Objects.requireNonNull(responseEntity.getHeaders().get("Authorization")).get(0);
        System.out.println(authHeader);
        assertThat(authHeader).contains("Bearer");
    }

}
