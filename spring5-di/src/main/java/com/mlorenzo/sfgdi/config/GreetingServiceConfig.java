package com.mlorenzo.sfgdi.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.mlorenzo.sfgdi.datasource.FakeDataSource;
import com.mlorenzo.sfgdi.repositories.EnglishGreetingRepository;
import com.mlorenzo.sfgdi.repositories.EnglishGreetingRepositoryImpl;
import com.mlorenzo.sfgdi.services.GreetingService;
import com.mlorenzo.sfgdi.services.I18nEnglishGreetingService;
import com.mlorenzo.sfgdi.services.I18nSpanishGreetingService;
import com.mlorenzo.sfgdi.services.PrimaryGreetService;
import com.mlorenzo.sfgdi.services.PropertyInjectedGreetingService;
import com.mlorenzo.sfgdi.services.SetterInjectedGreetingService;
import com.springframework.pets.PetService;
import com.springframework.pets.PetServiceFactory;

// Se comenta esta anotación porque ahora las propiedades están en el archivo de propiedades por defecto de Spring Boot "application.properties" y, por lo tanto, ya no hace falta esta anotación
//@PropertySource("classpath:datasource.properties") // Anotación pasa usar las propiedades del archivo "datasource.properties" e inyectarlas
@EnableConfigurationProperties(SfgConstructorConfig.class)
// Combinamos esta clase de configuración y el xml de configuración "sfgdi-config.xml" para la creación de beans de Spring
@ImportResource("classpath:sfgdi-config.xml")
@Configuration
public class GreetingServiceConfig {
	
	// Nota: Los métodos de ccreación de beans de Spring pueden ser public o default
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	// Inyectamos los valores de las propiedades indicadas en los argumentos de entrada de este método usando la anotación @Value
	// Dichas propiedades vienen del archivo de propiedades "datasource.properties"
	// Se comenta porque ahora usamos la clase de configuración de Spring "SfgConfiguration"(inyección por métodos setter) para obtener los valores de las propiedades(ver método de abajo)
	/*@Bean
	FakeDataSource fakeDataSource(@Value("${guru.username}") String username,
			@Value("${guru.password}") String password, @Value("${guru.jdbc-url}") String jdbcUrl) {
		FakeDataSource fakeDataSource = new FakeDataSource();
		fakeDataSource.setUsername(username);
		fakeDataSource.setPassword(password);
		fakeDataSource.setJdbcUrl(jdbcUrl);
		return fakeDataSource;
	}*/
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	// El argumento de entrada de este método será inyectado automáticamente por Spring ya que estamos en una clase de configuración(@Configuration)
	// Se comenta porque ahora usamos la clase de configuración de Spring "SfgConstructorConfig"(inyección por constructor - forma recomendada por ser inmutable) para obtener los valores de las propiedades(ver método de abajo)
	/*@Bean
	FakeDataSource fakeDataSource(SfgConfiguration sfgConfiguration) {
		FakeDataSource fakeDataSource = new FakeDataSource();
		fakeDataSource.setUsername(sfgConfiguration.getUsername());
		fakeDataSource.setPassword(sfgConfiguration.getPassword());
		fakeDataSource.setJdbcUrl(sfgConfiguration.getJdbcUrl());
		return fakeDataSource;
	}*/
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	// El argumento de entrada de este método será inyectado automáticamente por Spring ya que estamos en una clase de configuración(@Configuration)
	@Bean
	FakeDataSource fakeDataSource(SfgConstructorConfig sfgConstructorConfig) {
		FakeDataSource fakeDataSource = new FakeDataSource();
		fakeDataSource.setUsername(sfgConstructorConfig.getUsername());
		fakeDataSource.setPassword(sfgConstructorConfig.getPassword());
		fakeDataSource.setJdbcUrl(sfgConstructorConfig.getJdbcUrl());
		return fakeDataSource;
	}
	
	// Como tenemos varias implementaciones de la interfaz "GreetingService", con esta anotación @Primary hacemos que la implementación
	// de esta clase sea la implementación por defecto a la hora de inyectarse en caso de que no se indique ninguna otra implementación
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	@Primary
	@Bean
	public GreetingService primaryGreetService() {
		return new PrimaryGreetService();
	}
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	// Se comenta  porque ahora este bean de Spring se crea a partir del xml de configuración de Spring "sfgdi-config.xml"
	/*@Bean(name = "constructorInjectedGreetingService")
	public GreetingService constructorGreetingService() {
		return new ConstructorInjectedGreetingService();
	}*/
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	@Bean(value = "propertyInjectedGreetingService")
	public GreetingService propertyGreetingService() {
		return new PropertyInjectedGreetingService();
	}
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	@Bean(name = "setterInjectedGreetingService")
	public GreetingService setterGreetingService() {
		return new SetterInjectedGreetingService();
	}
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	@Bean
	EnglishGreetingRepository englishGreetingRepository() {
		return new EnglishGreetingRepositoryImpl();
	}
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	// El argumento de entrada de este método será inyectado automáticamente por Spring ya que estamos en una clase de configuración(@Configuration)
	@Profile("EN") // Con esta anotación, se creará este bean de Spring cuando el perfil activo sea "EN"
	@Bean(name = "i18nService")
	GreetingService i18nEnglishService(EnglishGreetingRepository repository) {
		return new I18nEnglishGreetingService(repository);
	}

	// Spring utiliza por defecto el nombre del método para el nombre del bean
	@Profile({"ES", "default"}) // Con esta anotación, se creará este bean de Spring cuando el perfil activo sea "ES" o "default"(perfil por defecto cuando no hay ninguno activo)
	@Bean(name = "i18nService")
	GreetingService i18nSpanishService() {
		return new I18nSpanishGreetingService();
	}
	
	// Bean de Spring que implementa el patrón Factory para devolver implementaciones de la interfaz "PetService"
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	@Bean
	public PetServiceFactory petServiceFactory() {
		return new PetServiceFactory();
	}
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	// El argumento de entrada de este método será inyectado automáticamente por Spring ya que estamos en una clase de configuración(@Configuration)
	@Profile({"dog", "default"}) // Con esta anotación, se creará el bean de Spring para este servicio cuando el perfil activo sea "dog" o "default"(perfil por defecto cuando no hay ninguno activo)
	@Bean
	public PetService dogPetService(PetServiceFactory petServiceFactory) {
		return petServiceFactory.getPetService("dog");
	}
	
	// Spring utiliza por defecto el nombre del método para el nombre del bean
	// El argumento de entrada de este método será inyectado automáticamente por Spring ya que estamos en una clase de configuración(@Configuration)
	@Profile("cat") // Con esta anotación, se creará el bean de Spring para este servicio cuando el perfil activo sea "cat"
	@Bean
	public PetService catPetService(PetServiceFactory petServiceFactory) {
		return petServiceFactory.getPetService("cat");
	}
}
