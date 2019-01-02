package com.holkem.HateoasSpringBoot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 

@SpringBootApplication
public class HateoasSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HateoasSpringBootApplication.class, args);
	}

}

@RestController
class GreetController {
	final static Logger logger = LoggerFactory.getLogger(GreetController.class);
	
	@Autowired
	Environment env;
	
	@GetMapping("/")
	public Greet greet() {
		logger.info("bootrest.customProperty=" + env.getProperty("bootrest.customProperty"));
		return new Greet("Hello World!");
	}
	
	@GetMapping("/greetMe")
	public HttpEntity<Greet> greetMe(
			@RequestParam(value="name", required=false, defaultValue="Nobody") String name) {
		Greet greet = new Greet("Hello " + name);
		greet.add(ControllerLinkBuilder.linkTo(
				ControllerLinkBuilder.methodOn(GreetController.class).greetMe(name))
				.withSelfRel());
		return new ResponseEntity<Greet>(greet, HttpStatus.OK);
	}
}

class Greet extends ResourceSupport {
	private String greeting;
	Greet() {}
	Greet(String greeting) {
		this.greeting = greeting;
	}
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
}