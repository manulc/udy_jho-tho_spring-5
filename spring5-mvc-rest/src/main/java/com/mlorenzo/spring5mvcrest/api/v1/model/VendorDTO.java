package com.mlorenzo.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
	
	// Anotación de Swagger para personalizar la información de esta propiedad de este modelo
	@ApiModelProperty(value = "This is the name of the Vendor", required = true) // Indicamos que es un campo obligatorio
	private String name;
	
	@JsonProperty("vendor_url")
	private String vendorUrl;
}
