package de.menschomat.cashier.rest.staticContent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
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