package com.mlorenzo.spring5mvcrest.api.v1.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlorenzo.spring5mvcrest.api.v1.model.VendorDTO;
import com.mlorenzo.spring5mvcrest.services.VendorService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerIT {
	
	@MockBean //provided by Spring Context
    VendorService vendorService;

    @Autowired
    MockMvc mockMvc; //provided by Spring Context
    
    @Autowired
    ObjectMapper objectMapper;

    VendorDTO vendorDTO_1;
    VendorDTO vendorDTO_2;
    
    @Before
    public void setUp() throws Exception {
        vendorDTO_1 = new VendorDTO("Vendor 1", VendorController.BASE_URL + "/1");
        vendorDTO_2 = new VendorDTO("Vendor 2", VendorController.BASE_URL + "/2");
    }

    @Test
    public void getVendorList() throws Exception {
        List<VendorDTO> vendorsDTO = Arrays.asList(vendorDTO_1, vendorDTO_2);
        given(vendorService.getAllVendors()).willReturn(vendorsDTO);
        mockMvc.perform(get(VendorController.BASE_URL)
        			// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
                    .accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                	.andExpect(status().isOk())
                	.andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void getVendorById() throws Exception {
        given(vendorService.getVendorById(anyLong())).willReturn(vendorDTO_1);
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
                .accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void createNewVendor() throws Exception {
        given(vendorService.saveOrUpdateVendor(vendorDTO_1,null)).willReturn(vendorDTO_1);
        mockMvc.perform(post(VendorController.BASE_URL)
        			// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
                	.accept(MediaType.APPLICATION_JSON) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                    .contentType(MediaType.APPLICATION_JSON) // Indicamos que envíamos el contenido de la petición http en formato Json
                    .content(objectMapper.writeValueAsString(vendorDTO_1))) 
                	.andExpect(status().isCreated())
                	.andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void updateVendor() throws Exception {
        given(vendorService.saveOrUpdateVendor(any(),anyLong())).willReturn(vendorDTO_1);
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
            	.accept(MediaType.APPLICATION_JSON) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                .contentType(MediaType.APPLICATION_JSON) // Indicamos que envíamos el contenido de la petición http en formato Json
                .content(objectMapper.writeValueAsString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void patchVendor() throws Exception {
        given(vendorService.patchVendor(anyLong(),any())).willReturn(vendorDTO_1);
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
            	.accept(MediaType.APPLICATION_JSON) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                .contentType(MediaType.APPLICATION_JSON) // Indicamos que envíamos el contenido de la petición http en formato Json
                .content(objectMapper.writeValueAsString(vendorDTO_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vendorDTO_1.getName())));
    }

    @Test
    public void deleteVendor() throws Exception {
        mockMvc.perform(delete(VendorController.BASE_URL + "/1"))
                .andExpect(status().isOk());
        then(vendorService).should().deleteVendorById(anyLong());
    }

}
