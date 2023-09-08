package com.mlorenzo.spring5mvcrest.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mlorenzo.spring5mvcrest.api.v1.model.VendorDTO;
import com.mlorenzo.spring5mvcrest.api.v1.model.VendorListDTO;
import com.mlorenzo.spring5mvcrest.services.VendorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

//Anotación de Swagger para personalizar la descripción por defecto de este controlador mediante el tag "vendors"
@Api(tags = {"vendors"})
@AllArgsConstructor
@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {
	public static final String BASE_URL = "/api/v1/vendors";
	
	private final VendorService vendorService;
	
	// Anotación de Swagger para personalizar la información de esta operación de este controlador
	@ApiOperation(value = "View list of Vendors.", notes = "These are some notes about the API.")
	@GetMapping
	@ResponseStatus(HttpStatus.OK) // Si no se indica esta anotación, por defecto el estado de la respuesta es OK(200)
	public VendorListDTO getAllVendors(){
		return new VendorListDTO(vendorService.getAllVendors());
	}
	
	// Anotación de Swagger para personalizar la información de esta operación de este controlador
	@ApiOperation(value = "Get Vendor By Id")
	@GetMapping("/{idVendor}")
	public VendorDTO getVendorById(@PathVariable Long idVendor){
		return vendorService.getVendorById(idVendor);
	}
	
	// Anotación de Swagger para personalizar la información de esta operación de este controlador
	@ApiOperation(value = "Create a new Vendor")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
		return vendorService.saveOrUpdateVendor(vendorDTO,null);
	}
	
	// Anotación de Swagger para personalizar la información de esta operación de este controlador
	@ApiOperation(value = "Update an existing Vendor")
	@PutMapping("/{idVendor}")
	public VendorDTO updateVendor(@RequestBody VendorDTO vendorDTO,@PathVariable(value = "idVendor") Long id){
		return vendorService.saveOrUpdateVendor(vendorDTO,id);
	}
	
	// Anotación de Swagger para personalizar la información de esta operación de este controlador
	@ApiOperation(value = "Update a Vendor property")
	@PatchMapping("/{idVendor}")
	public VendorDTO patchVendor(@RequestBody VendorDTO vendorDTO,@PathVariable(value = "idVendor") Long id){
		return vendorService.patchVendor(id, vendorDTO);
	}
	
	// Anotación de Swagger para personalizar la información de esta operación de este controlador
	@ApiOperation(value = "Delete an existing Vendor")
	@DeleteMapping("/{id}")
	public void deleteVendor(@PathVariable Long id){
		vendorService.deleteVendorById(id);
	}

}
