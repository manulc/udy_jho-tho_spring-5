package com.mlorenzo.spring5mvcrest.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mlorenzo.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mlorenzo.spring5mvcrest.domain.Customer;
import com.mlorenzo.spring5mvcrest.repositories.CustomerRepository;

public class CustomerServiceImplTest {
	private static final Long ID = 2L;
    private static final String FIRSTNAME = "Freddy";
    private static final String LASTNAME = "Meyers";
    private static final String URL = "/api/v1/customers/" + ID;
    
    @Mock
    CustomerRepository customerRepository;
    
    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository,CustomerMapper.INSTANCE);
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        //when
        List<CustomerDTO> customersDTOS = customerService.getAllCustomers();
        //then
        assertEquals(3, customersDTOS.size());
    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        Optional<Customer> customerOptional = Optional.of(customer);
        when(customerRepository.findById(ID)).thenReturn(customerOptional);
        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);
        //then
        assertEquals(FIRSTNAME, customerDTO.getFirstname());
        assertEquals(LASTNAME, customerDTO.getLastname());
        assertEquals(URL, customerDTO.getCustomerUrl());
    }
    
    @Test
    public void saveCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRSTNAME);
        customerDTO.setLastname(LASTNAME);
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(ID);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        //when
        CustomerDTO savedDto = customerService.saveOrUpdateCustomer(customerDTO,null);
        //then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals(customerDTO.getLastname(), savedDto.getLastname());
        assertEquals(URL, savedDto.getCustomerUrl());
    }
    
    @Test
    public void updateCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jhon");
        customerDTO.setLastname("Doe");
        Customer customer = new Customer();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        customer.setId(ID);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        //when
        CustomerDTO updatedCustomer = customerService.saveOrUpdateCustomer(customerDTO,ID);
        //then
        assertEquals(FIRSTNAME, updatedCustomer.getFirstname());
        assertEquals(LASTNAME, updatedCustomer.getLastname());
        assertEquals(URL, updatedCustomer.getCustomerUrl());
    }
    
    @Test
    public void deleteCustomerById() throws Exception {
        Long id = 1L;
        customerService.deleteCustomerById(id);
        verify(customerRepository, times(1)).deleteById(anyLong());
    }

}
