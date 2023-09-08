package com.mlorenzo.sfgdi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Nota: La desventaja de usar esta clase de configuración con respecto a la clase de configuración "SfgConstructorConfig" es que, como las propiedades
// de esta clase no están declaradas como "final" y usamos sus métodos setter para la inyección de sus valores, hace que está clase sea mutable, es decir,
// es posible modificar los valores de estas propiedades en un fututo debido a que existen sus métodos setter

// Usando esta anotación, Spring irá a los archivos de propiedades a buscar aquellas propiedades que empiecen por "guru"
// e inyectará sus valores en las propiedades de esta clase usando los método setter. Para ello, los nombres de las propiedades
// de esta clase tienen que ser iguales a los nombres de las propiedades que coincidan con el prefijo "guru" 
@ConfigurationProperties("guru")
@Configuration
public class SfgConfiguration {
	private String username;
	private String password;
	private String jdbcUrl;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
}
