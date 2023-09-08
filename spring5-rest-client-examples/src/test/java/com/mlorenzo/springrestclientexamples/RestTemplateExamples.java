package com.mlorenzo.springrestclientexamples;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
//import org.apache.http.conn.ssl.NoopHostnameVerifier;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

// Swagger Url: https://api.predic8.de/shop/docs

public class RestTemplateExamples {
	private static final String API_ROOT = "https://api.predic8.de:443/shop/v2/";

    @Test
    public void getProducts() {
        String apiUrl = API_ROOT + "/products";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void getCustomers() {
        String apiUrl = API_ROOT + "/customers";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void createProduct() {
        String apiUrl = API_ROOT + "/products";
        RestTemplate restTemplate = new RestTemplate();
        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Mangos");
        postMap.put("price", 2.79);
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
    }

    @Test
    public void updateProduct() {
        //create product to update
        String apiUrl = API_ROOT + "/products/";
        RestTemplate restTemplate = new RestTemplate();
        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Oranges");
        postMap.put("price", 3.69);
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
        long id = jsonNode.get("id").longValue();
        System.out.println("Created product id: " + id);
        postMap.put("name", "Oranges 2");
        postMap.put("price", 3.43);
        restTemplate.put(apiUrl + id, postMap);
        JsonNode updatedNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);
        System.out.println(updatedNode.toString());

    }

    @Test(expected = ResourceAccessException.class)
    public void updateProductUsingPatchSunHttp() {
        //create Product to update
        String apiUrl = API_ROOT + "/products/";
        RestTemplate restTemplate = new RestTemplate();
        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Mangos");
        postMap.put("price", 2.79);
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
        long id = jsonNode.get("id").longValue();
        System.out.println("Created product id: " + id);
        postMap.put("name", "Mangos 2");
        postMap.put("price", 3.01);
        //example of setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);
        //fails due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
	    JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);
	    System.out.println(updatedNode.toString());
    }

    @Test
    public void updateProductUsingPatch() {
    	//create Product to update
        String apiUrl = API_ROOT + "/products/";
        // Use Apache HTTP client factory
        //see: https://github.com/spring-cloud/spring-cloud-netflix/issues/1777
        // Como este proyecto tiene la dependencia httpclient, para tener soporte de peticiones http de tipo Patch, y se comunica con otros servidores mediante https,
    	// como no tenemos certificados válidos y para evitar el error o excepción "javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated", realizamos esta
    	// configuramos SSL en este cliente RestTemplate r
        CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Bananas");
        postMap.put("price", 1.60);
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
        long id = jsonNode.get("id").longValue();
        System.out.println("Created product id: " + id);
        postMap.put("name", "Bananas 2");
        postMap.put("price", 1.78);
        //example of setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);
        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);
        System.out.println(updatedNode.toString());
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteProduct() {
    	//create Product to update
        String apiUrl = API_ROOT + "/products/";
        RestTemplate restTemplate = new RestTemplate();
        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("name", "Apples");
        postMap.put("price", 5.32);
        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode.toString());
        long id = jsonNode.get("id").longValue();
        System.out.println("Created product id: " + id);
        restTemplate.delete(apiUrl + id); //expects 200 status
        System.out.println("Customer deleted");
        //should go boom on 404
	    restTemplate.getForObject(apiUrl + id, JsonNode.class);
    }

}
