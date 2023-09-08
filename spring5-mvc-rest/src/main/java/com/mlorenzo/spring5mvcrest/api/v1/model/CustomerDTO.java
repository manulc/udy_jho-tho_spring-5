package com.mlorenzo.spring5mvcrest.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerDTO {
	
	// Anotación de Swagger para personalizar la información de esta propiedad de este modelo
	@ApiModelProperty(value = "This is the first name", required = true) // Indicamos que es un campo obligatorio
	private String firstname;
	
	// Anotación de Swagger para personalizar la información de esta propiedad de este modelo
	@ApiModelProperty(value = "This is the last name", required = false) // Indicamos que es un campo opcional
	private String lastname;
	
	private String customerUrl;
}
