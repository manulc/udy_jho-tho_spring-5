package com.mlorenzo.springrestclientexamples.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	// Como este proyecto tiene la dependencia httpclient, para tener soporte de peticiones http de tipo Patch, y se comunica con otros servidores mediante https,
	// como no tenemos certificados válidos y para evitar el error o excepción "javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated", realizamos esta
	// configuramos SSL en este cliente RestTemplate 
	@Bean
	public RestTemplate restTemplate() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLHostnameVerifier(new NoopHostnameVerifier())
				.build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);
	}
}
