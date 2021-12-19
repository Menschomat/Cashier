package de.menschomat.cashier.rest.staticContent;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class StaticContentTestImpl {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testIfIndexHtmlGeneratedProbably() {
		String body = this.restTemplate.getForObject("/", String.class);
		assertThat(body).contains("<title>Cashier</title>");
	}

}