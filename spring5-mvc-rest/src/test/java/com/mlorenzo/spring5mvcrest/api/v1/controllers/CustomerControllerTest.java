package com.mlorenzo.spring5mvcrest.api.v1.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.CustomerDTO;
import com.mlorenzo.spring5mvcrest.exceptions.ResourceNotFoundException;
import com.mlorenzo.spring5mvcrest.services.CustomerService;

public class CustomerControllerTest {
	private static final String FIRSTNAME = "Alice";
	private static final String LASTNAME = "Eastman";
	private static final Long ID = 123L;

    @Mock
    CustomerService customerService;

    @InjectMocks // Esta anotación hace que todas las propiedad anotadas con @Mock(En este caso, sólo "customerService" de tipo "CustomerService") se inyecten dentro de este controlador "customerController"
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Comentamos esta línea porque ya no hace falta debido a que estamos usando la anotación @InjectMocks sobre la propiedad del controlador "customerController"
        //customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
        			.setControllerAdvice(new RestResponseEntityExceptionHandler())
        			.build();
    }

    @Test
    public void testListCustomers() throws Exception {
    	//given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);
        CustomerDTO customer2 = new CustomerDTO();
        customer2.setFirstname("Freddy");
        customer2.setLastname("Meyers");
        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);
        when(customerService.getAllCustomers()).thenReturn(customers);
        //when
        mockMvc.perform(get(CustomerController.BASE_URL)
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                //then
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
    	//given
    	CustomerDTO customer1 = new CustomerDTO();
    	customer1.setFirstname(FIRSTNAME);
    	customer1.setLastname(LASTNAME);
    	customer1.setCustomerUrl(CustomerController.BASE_URL + "/" + ID);
        when(customerService.getCustomerById(any(Long.class))).thenReturn(customer1);
        //when
        mockMvc.perform(get(CustomerController.BASE_URL + "/" + ID)
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xmlaplicación es capaz de enviar el contenido de la respuesta de esa petición http en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                //then
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/" + ID)));
    }
    
    @Test
    public void testCreateNewCustomer() throws Exception {
    	//given
    	ObjectMapper objectMapper = new ObjectMapper();
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + ID);
        when(customerService.saveOrUpdateCustomer(customer,null)).thenReturn(returnDTO);
        //when
        mockMvc.perform(post(CustomerController.BASE_URL)
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                // Es obligatorio indicar la propiedad "ContentType" en la cabecera de la petición http cuando enviemos contenido en esa petición porque esta aplicación es capaz de recibir contenido en formato Json o Xml
        		.contentType(MediaType.APPLICATION_JSON) // Indicamos que envíamos el contenido de la petición http en formato Json
                .content(objectMapper.writeValueAsString(customer)))
        		//then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/" + ID)));
    }
    
    @Test
    public void testUpdateCustomer() throws Exception {
    	//given
    	ObjectMapper objectMapper = new ObjectMapper();
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);
        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(customer.getLastname());
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + ID);
        when(customerService.saveOrUpdateCustomer(customer,ID)).thenReturn(returnDTO);
        //when
        mockMvc.perform(put(CustomerController.BASE_URL + "/" + ID)
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
        		// Es obligatorio indicar la propiedad "ContentType" en la cabecera de la petición http cuando enviemos contenido en esa petición porque esta aplicación es capaz de recibir contenido en formato Json o Xml
        		.contentType(MediaType.APPLICATION_JSON) // Indicamos que envíamos el contenido de la petición http en formato Json
                .content(objectMapper.writeValueAsString(customer)))
        		//then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/" + ID)));
    }
    
    @Test
    public void testPatchCustomer() throws Exception {
    	//given
    	ObjectMapper objectMapper = new ObjectMapper();
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstname(FIRSTNAME);
        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customer.getFirstname());
        returnDTO.setLastname(LASTNAME);
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + ID);
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
        //when
        mockMvc.perform(patch(CustomerController.BASE_URL + "/" + ID)
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
        		// Es obligatorio indicar la propiedad "ContentType" en la cabecera de la petición http cuando enviemos contenido en esa petición porque esta aplicación es capaz de recibir contenido en formato Json o Xml
        		.contentType(MediaType.APPLICATION_JSON) // Indicamos que envíamos el contenido de la petición http en formato Json
                .content(objectMapper.writeValueAsString(customer)))
        		//then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
                .andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "/" + ID)));
    }
    
    @Test
    public void testDeleteCustomer() throws Exception {
    	//when
        mockMvc.perform(delete(CustomerController.BASE_URL + "/" + ID))
        		//then
                .andExpect(status().isOk());
        verify(customerService).deleteCustomerById(anyLong());
    }
    
    @Test
    public void testNotFoundException() throws Exception {
    	//given
        when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);
        //when
        mockMvc.perform(get(CustomerController.BASE_URL + "/" + ID)
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                //then
        		.andExpect(status().isNotFound());
    }

}
