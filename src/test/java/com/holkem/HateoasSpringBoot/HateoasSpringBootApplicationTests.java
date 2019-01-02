package com.holkem.HateoasSpringBoot;

import java.util.Base64;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HateoasSpringBootApplicationTests {
	final static Base64.Encoder encoder = Base64.getEncoder();

	@Test
	public void contextLoads() {
		RestTemplate restTemplate = new RestTemplate();
		Greet greet = restTemplate.getForObject("http://localhost:8080/", Greet.class); 
		
		Assert.assertEquals("Hello World!!", greet.getGreeting());
	}
	
	@Test
	public void testSecureService() {
		// create request headers with the encoded credentials 
		String plainCredenitals = "user:secret123";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + new String(encoder.encodeToString(plainCredenitals.getBytes())));
		
		// make headers an HttpEntity so we can pass it to RestTemplate.exchange()
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		// Rest execution // instead of getting the returned object, this is getting the ResponseEntity
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Greet> response = restTemplate.exchange("http://localhost:8080/", HttpMethod.GET, request, Greet.class);
		
		// validate response
		Assert.assertEquals("Hello World!", response.getBody().getGreeting());
		
	}

}

