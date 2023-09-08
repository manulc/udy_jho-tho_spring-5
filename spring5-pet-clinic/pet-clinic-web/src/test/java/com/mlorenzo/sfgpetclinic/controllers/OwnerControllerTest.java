package com.mlorenzo.sfgpetclinic.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasProperty;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.services.OwnerService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class) // Para usar Mockito en esta clase de pruebas
class OwnerControllerTest {
	
	@Mock // Crea una Mock del servicio "OwnerService"
	OwnerService ownerService;
	
	@InjectMocks // Esta anotación crea una instancia del controlador "OwnerController" e inyecta el Mock del servicio "ownerService"
	OwnerController ownerController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
	}
	
	@Test
	void findOwnersTest() throws Exception {
		mockMvc.perform(get("/owners/find"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("owners/findOwners"))
			   .andExpect(model().attributeExists("owner"));
		verifyZeroInteractions(ownerService);
	}
	
	@Test
	void processFindFormReturnManyTest() throws Exception {
		Owner owners[] = {Owner.builder().id(1L).build(),Owner.builder().id(2L).build()};
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(owners));
		mockMvc.perform(get("/owners"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("owners/ownersList"))
			   .andExpect(model().attribute("selections",hasSize(2)));
	}
	
	@Test
    void processFindFormEmptyReturnManyTest() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(Arrays.asList(Owner.builder().id(1l).build(),
                        Owner.builder().id(2l).build()));
        mockMvc.perform(get("/owners")
                        .param("lastName",""))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));;
    }
	
	@Test
	void processFindFormReturnOneTest() throws Exception {
		Owner returnedOwner = Owner.builder().id(1L).build();
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(returnedOwner));
		mockMvc.perform(get("/owners"))
			   .andExpect(status().is3xxRedirection())
			   .andExpect(view().name("redirect:/owners/1"));
	}
	
	@Test
	void displayOwnerTest() throws Exception {
		Owner returnedOwner = Owner.builder().id(1L).build();
		when(ownerService.findById(anyLong())).thenReturn(returnedOwner);
		mockMvc.perform(get("/owners/123"))
			   .andExpect(status().isOk())
			   .andExpect(view().name("owners/ownerDetails"))
			   .andExpect(model().attribute("owner",hasProperty("id",is(1L))));
	}
	
	@Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        verifyZeroInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1l).build());
        mockMvc.perform(post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));
        // Si no se indica el número de llamadas, por defecto es 1
        verify(ownerService).save(any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1l).build());
        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/createOrUpdateOwnerForm"))
                .andExpect(model().attributeExists("owner"));
        // Si no se indica el número de llamadas, por defecto es 1
        verify(ownerService,times(1)).findById(anyLong());
    }

    @Test
    void processUpdateOwnerForm() throws Exception {
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1l).build());
        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));
        // Si no se indica el número de llamadas, por defecto es 1
        verify(ownerService).save(any(Owner.class));
    }
	
}
