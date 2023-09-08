package com.mlorenzo.spring5mvcrest.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mlorenzo.spring5mvcrest.api.v1.controllers.CustomerController;
import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mlorenzo.spring5mvcrest.domain.Customer;

public class CustomerMapperTest {
	private static final String FIRSTNAME = "Jhon";
	private static final String LASTNAME = "Doe";
	private static final long ID = 1L;
	private static final String URL = CustomerController.BASE_URL + "/" + ID;
	
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
	@Test
    public void customerToCustomerDTO() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        customer.setId(ID);
        //when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        //then
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
    }
	
	@Test
    public void customerDTOToCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        customerDTO.setCustomerUrl(URL);
        //when
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        //then
        assertEquals(FIRSTNAME, customer.getFirstname());
        assertEquals(LASTNAME, customer.getLastname());
    }

}
