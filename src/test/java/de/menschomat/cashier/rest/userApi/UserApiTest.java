package de.menschomat.cashier.rest.userApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.menschomat.cashier.database.jpa.model.DBUser;
import de.menschomat.cashier.rest.model.RestUser;
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
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class UserApiTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void init() {
        headers.set(
                "Authorization",
                Objects.requireNonNull(this.restTemplate.exchange(
                        "/api/users/login?username=admin&password=cashier",
                        HttpMethod.GET,
                        new HttpEntity<>("body", headers),
                        String.class).getHeaders().get("Authorization")).get(0));
    }

    @After
    public void finalize() {
        headers = new HttpHeaders();
    }

    private DBUser getCurrent() throws IOException {
        return new ObjectMapper().readValue(
                this.restTemplate.exchange(
                        "/api/user/current",
                        HttpMethod.GET,
                        new HttpEntity<>(
                                "body",
                                headers),
                        String.class).getBody(),
                DBUser.class);
    }

    @Test
    public void getCurrentReturnsValid() throws IOException {
        assertThat(new ObjectMapper().readValue(
                this.restTemplate.exchange(
                        "/api/user/current",
                        HttpMethod.GET,
                        new HttpEntity<>(
                                "body",
                                headers),
                        String.class).getBody(),
                DBUser.class)).isInstanceOf(DBUser.class);
    }

    @Test
    public void checkUsernameMatching() throws IOException {
        assertThat(getCurrent().getUsername()).isEqualTo("admin");
    }

    @Test
    public void checkRoleMatching() throws IOException {
        assertThat(getCurrent().getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void checkCurrentUpdatable() throws IOException {
        DBUser user = getCurrent();
        user.setName("Tester");
        assertThat(new ObjectMapper().readValue(this.restTemplate.exchange(
                "/api/user/current",
                HttpMethod.POST,
                new HttpEntity<>(user, headers),
                String.class).getBody(),
                RestUser.class).name).isEqualTo("Tester");
    }
}
