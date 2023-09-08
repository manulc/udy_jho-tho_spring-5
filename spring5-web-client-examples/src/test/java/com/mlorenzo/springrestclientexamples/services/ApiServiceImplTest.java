package com.mlorenzo.springrestclientexamples.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceImplTest {
	
	@Autowired
	ApiService apiService;
	
	@Test
	public void testGetUsers() {
		StepVerifier.create(apiService.getUsers(Mono.just(3)).count())
			.expectNext(3l);
				
	}

}
