package com.mlorenzo.springrestclientexamples.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void index() {
    	HttpStatus status = testRestTemplate.getForEntity("/", Void.class).getStatusCode();
    	assertEquals(HttpStatus.OK, status);
    }

    @Test
    public void formPost() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("limit", "3");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpStatus status = testRestTemplate.postForEntity("/users", new HttpEntity<>(formData, headers), Void.class).getStatusCode();
        assertEquals(HttpStatus.OK, status);
    }
}
