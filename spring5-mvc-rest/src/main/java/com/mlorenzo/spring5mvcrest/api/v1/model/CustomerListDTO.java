package com.mlorenzo.spring5mvcrest.api.v1.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerListDTO {
	
	//@JacksonXmlElementWrapper(localName = "customers")
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "CustomerDTO")
	private List<CustomerDTO> customers;
}
