package com.mlorenzo.spring5mvcrest.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig{ // extends WebMvcConfigurationSupport{ // Sólo para sobrescribir la implementación del método "addResourceHandlers"
	
	// La url "http://localhost:8080/v2/api-docs"(Suponiendo que nuestra aplicación se ejecuta en local en el puerto 8080) nos da un objeto Json con los datos de la documentación
	// La url "http://localhost:8080/swagger-ui.html"(Suponiendo que nuestra aplicación se ejecuta en local en el puerto 8080) nos da una interfaz web gráfica con los datos de la documentación
	
	// Esta configuración es la configuración por defecto de Swagger y podemos indicarla o no con este método ,pero si queremos cambiar algo de esta configuración por defecto, sí que es necesario implementar obligatoriamente este método con la implementación de nuestra configuración
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.apiInfo(metaData()) // Ésto ya no forma parte de la configuración por defecto de Swagger. Registramos nuestros metadatos en la documentación 
				.tags(new Tag("customers","This is my Customer Controller")) // Ésto ya no forma parte de la configuración por defecto de Swagger. Creamos un tag para personalizar la descripción de nuestro controlador Customer Controller
				.tags(new Tag("vendors","This is my Vendor Controller")); // Ésto ya no forma parte de la configuración por defecto de Swagger. Creamos un tag para personalizar la descripción de nuestro controlador Vendor Controller
	}
	
	// Método con nuestros metadatos personalizados que queremos que aparezcan en la documentación de Swagger
	private ApiInfo metaData(){
        Contact contact = new Contact("John Thompson", "https://springframework.guru/about/",
                "john@springfrmework.guru");
        return new ApiInfo(
                "Spring Framework Guru",
                "Spring Framework 5: Beginner to Guru",
                "1.0",
                "Terms of Service: blah",
                contact,
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

	// Esta implementación permite habilitar los recursos de la interfaz web gráfica de Swagger en aplicaciones o proyectos que no son de tipo Spring Boot para porder acceder correctamente a dicha interfaz gráfica desde el navegador
	// No hace falta sobrescribir este método de la clase padre "WebMvcConfigurationSupport" con esta implementación si estamos en una aplicación Spring Boot, ya que Spring Boot automáticanete configura estos recursos por nosotros
	// Por esta razón, como esta aplicación es de tipo Spring Boot, comentamos esta implementación porque no hace falta
	/*@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}*/
}
