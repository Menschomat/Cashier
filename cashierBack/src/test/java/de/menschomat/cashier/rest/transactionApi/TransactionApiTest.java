package de.menschomat.cashier.rest.transactionApi;

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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Date;
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
        return new ObjectMapper().readValue(this.restTemplate.exchange(
                "/api/user/current",
                HttpMethod.GET,
                new HttpEntity<>("body", headers),
                String.class).getBody(), DBUser.class);
    }

    @Before
    public void init() {
        headers.set("Authorization",
                this.restTemplate.exchange("/api/users/login?username=admin&password=admin123",
                        HttpMethod.GET, new HttpEntity<>("body", headers),
                        String.class).getHeaders().get("Authorization").get(0));
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
        this.restTemplate.exchange("/api/transaction", HttpMethod.POST, new HttpEntity<>(toAdd, headers), String.class);
        toAdd.setUser(getCurrent()); //set user here, to check if automated user-readding is working
        assertThat(sameTransaction(transactionRepository.findByTitle(title), toAdd)).isTrue();
    }
}
