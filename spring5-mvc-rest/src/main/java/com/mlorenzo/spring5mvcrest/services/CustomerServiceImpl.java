package com.mlorenzo.spring5mvcrest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mlorenzo.spring5mvcrest.api.v1.controllers.CustomerController;
import com.mlorenzo.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mlorenzo.spring5mvcrest.domain.Customer;
import com.mlorenzo.spring5mvcrest.exceptions.ResourceNotFoundException;
import com.mlorenzo.spring5mvcrest.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService{
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream()
				.map(customer -> {
					CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
					customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
					return customerDTO;
				})
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id).map(customer -> {
			CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
			customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
			return customerDTO;
		})
		.orElseThrow(ResourceNotFoundException::new); // Versión simplificada de la expresión "() -> new ResourceNotFoundException()"
	}

	@Override
	public CustomerDTO saveOrUpdateCustomer(CustomerDTO customerDTO,Long id) {
		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		if(id != null)
			customer.setId(id);
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
		return returnDTO;
	}
	
	@Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            if(customerDTO.getFirstname() != null){
                customer.setFirstname(customerDTO.getFirstname());
            }
            if(customerDTO.getLastname() != null){
                customer.setLastname(customerDTO.getLastname());
            }
            Customer savedCustomer = customerRepository.save(customer);
            CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
            returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));
            return returnDTO;
        })
        .orElseThrow(ResourceNotFoundException::new); // Versión simplificada de la expresión "() -> new ResourceNotFoundException()"
    }

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
	
	private String getCustomerUrl(Long id) {
		return CustomerController.BASE_URL + "/" + id;
	}

}
