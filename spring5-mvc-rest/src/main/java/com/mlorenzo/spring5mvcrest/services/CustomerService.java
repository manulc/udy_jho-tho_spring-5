package com.mlorenzo.spring5mvcrest.services;

import java.util.List;

import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerDTO;

public interface CustomerService {
	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomerById(Long id);
	CustomerDTO saveOrUpdateCustomer(CustomerDTO customerDTO,Long id);
	CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
	void deleteCustomerById(Long id);
}
