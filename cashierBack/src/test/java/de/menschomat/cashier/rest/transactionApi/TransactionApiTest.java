package de.menschomat.cashier.rest.transactionApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.database.jpa.model.Transaction;
import de.menschomat.cashier.database.jpa.repositories.TransactionRepository;
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

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class TransactionApiTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    private HttpHeaders headers = new HttpHeaders();

    public boolean sameTransaction(Transaction a, Transaction b) {
        return a.getTitle().equals(b.getTitle()) &&
                Objects.equals(a.getAmount(), b.getAmount()) &&
                a.getDate().getTime() == b.getDate().getTime() &&
                Objects.equals(a.getUser().getId(), b.getUser().getId());
    }

    private DBUser getCurrent() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/api/user/current", HttpMethod.GET, entity, String.class);
        return new ObjectMapper().readValue(responseEntity.getBody(), DBUser.class);
    }

    @Before
    public void init() {
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/api/users/login?username=admin&password=admin123", HttpMethod.GET, entity, String.class);
        String authHeader = responseEntity.getHeaders().get("Authorization").get(0);
        System.out.println(authHeader);
        headers.set("Authorization", authHeader);
    }

    @After
    public void finalize() {
        headers = new HttpHeaders();
    }


    @Test
    public void checkUsernameMatching() throws IOException {
        String title = UUID.randomUUID().toString();
        Transaction toAdd = new Transaction();
        toAdd.setAmount((float) 200);
        toAdd.setDate(new Date());
        toAdd.setIngestion(true);
        toAdd.setTitle(title);
        HttpEntity<Transaction> entity = new HttpEntity<>(toAdd, headers);
        System.out.println(headers.get("Authorization"));
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/api/transaction", HttpMethod.POST, entity, String.class);
        System.out.println(responseEntity.getBody());
        toAdd.setUser(getCurrent());
        assertThat(sameTransaction(transactionRepository.findByTitle(title), toAdd)).isTrue();
    }
}
