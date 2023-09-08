package com.mlorenzo.spring5mvcrest.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerListDTO;
import com.mlorenzo.spring5mvcrest.services.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

// Anotación de Swagger para personalizar la descripción por defecto de este controlador mediante el tag "customers"
@Api(tags = {"customers"})
@AllArgsConstructor
@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
	public static final String BASE_URL = "/api/v1/customers";
	
	private final CustomerService customerService;
	
	// Anotación de Swagger para personalizar la información de esta operación de este controlador
	@ApiOperation(value = "This will get a list of customers.", notes = "These are some notes about the API.")
	@GetMapping
	public ResponseEntity<CustomerListDTO> getAllCustomers(){
		CustomerListDTO customerListDTO = new CustomerListDTO(customerService.getAllCustomers());
		return new ResponseEntity<CustomerListDTO>(customerListDTO,HttpStatus.OK);
	}
	
	@GetMapping("/{idCustomer}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long idCustomer){
		return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(idCustomer),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<CustomerDTO>(customerService.saveOrUpdateCustomer(customerDTO,null),HttpStatus.CREATED);
	}
	
	@PutMapping("/{idCustomer}")
	public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO,@PathVariable(value = "idCustomer") Long id){
		return new ResponseEntity<CustomerDTO>(customerService.saveOrUpdateCustomer(customerDTO,id),HttpStatus.OK);
	}
	
	@PatchMapping("/{idCustomer}")
	public ResponseEntity<CustomerDTO> patchCustomer(@RequestBody CustomerDTO customerDTO,@PathVariable(value = "idCustomer") Long id){
		return new ResponseEntity<CustomerDTO>(customerService.patchCustomer(id, customerDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
		customerService.deleteCustomerById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
