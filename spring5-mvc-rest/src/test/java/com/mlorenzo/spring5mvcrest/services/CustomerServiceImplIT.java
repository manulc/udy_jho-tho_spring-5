package com.mlorenzo.spring5mvcrest.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mlorenzo.spring5mvcrest.api.v1.mapper.CustomerMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mlorenzo.spring5mvcrest.bootstrap.Bootstrap;
import com.mlorenzo.spring5mvcrest.domain.Customer;
import com.mlorenzo.spring5mvcrest.repositories.CategoryRepository;
import com.mlorenzo.spring5mvcrest.repositories.CustomerRepository;
import com.mlorenzo.spring5mvcrest.repositories.VendorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {
	
	@Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;
	
	@Before
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());
        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run(); //load data
        customerService = new CustomerServiceImpl(customerRepository,CustomerMapper.INSTANCE);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);
        customerService.patchCustomer(id, customerDTO);
        Customer updatedCustomer = customerRepository.findById(id).get();
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstname());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstname())));
        assertThat(originalLastName, is(equalTo(updatedCustomer.getLastname())));
    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        String updatedName = "UpdatedName";
        long id = getCustomerIdValue();
        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        //save original first/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);
        customerService.patchCustomer(id, customerDTO);
        Customer updatedCustomer = customerRepository.findById(id).get();
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastname());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstname()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
    }

    private Long getCustomerIdValue(){
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Customers Found: " + customers.size());
        //return first id
        return customers.get(0).getId();
    }

}
