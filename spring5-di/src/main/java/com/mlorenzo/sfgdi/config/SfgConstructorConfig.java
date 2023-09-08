package com.mlorenzo.sfgdi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

// Nota: La ventaja de usar esta clase de configuración con respecto a la clase de configuración "SfgConfiguration" es que, como las propiedades
// de esta clase están declaradas como "final" y usamos el constructor para la inyección de sus valores, hace que está clase sea inmutable, es decir,
// no es posible modificar los valores de estas propiedades en un fututo ya que las propiedades marcadas con "final" no permiten usar métodos setter

// Usando esta anotación, Spring irá a los archivos de propiedades a buscar aquellas propiedades que empiecen por "guru"
// e inyectará sus valores en las propiedades de esta clase usando el constructor. Para ello, los nombres de las propiedades
// de esta clase tienen que ser iguales a los nombres de las propiedades que coincidan con el prefijo "guru" 
@ConfigurationProperties("guru")
@ConstructorBinding // Anotación necesario para que la inyección se haga usando el constructor
public class SfgConstructorConfig {
	private final String username;
	private final String password;
	private final String jdbcUrl;
	
	public SfgConstructorConfig(String username, String password, String jdbcUrl) {
		this.username = username;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getJdbcUrl() {
		return jdbcUrl;
	}
	
}
