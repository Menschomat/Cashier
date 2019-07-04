package de.menschomat.cashier.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class AuthTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void init() {
    }

    @After
    public void finalize() {

    }

    @Test
    public void testLogin(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/api/users/login?username=admin&password=admin123", HttpMethod.GET, entity, String.class);
        String authHeader = responseEntity.getHeaders().get("Authorization").get(0);
        System.out.println(authHeader);
        assertThat(authHeader).contains("Bearer");
    }

}
