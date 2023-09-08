package com.mlorenzo.spring5recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mlorenzo.spring5recipeapp.commands.RecipeCommand;
import com.mlorenzo.spring5recipeapp.services.ImageService;
import com.mlorenzo.spring5recipeapp.services.RecipeService;

//Anotación para poder usar Mockito en esta clase de pruebas
@RunWith(MockitoJUnitRunner.class) // Otra opción a esta anotación es usar la expresión o línea "MockitoAnnotations.initMocks(this);" en el método "setUp"
public class ImageControllerTest {
	
	@Mock // Crea una Mock del servicio "ImageService"
	ImageService imageService;
	
	@Mock // Crea una Mock del servicio "RecipeService"
	RecipeService recipeService;
	
	@InjectMocks // Esta anotación crea una instancia del controlador "ImageController" e inyecta los Mocks de los servicios "imageService" y "recipeService"
	ImageController controller;
	
	MockMvc mockMvc;
	 
	@Before
	public void setUp() throws Exception {
	    mockMvc = MockMvcBuilders.standaloneSetup(controller)
	    		.setControllerAdvice(new ControllerExceptionHandler())
	    		.build();
	}

    @Test
    public void getImageFormTest() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);
        when(recipeService.findRecipeCommandById(anyLong())).thenReturn(command);
        //when
        mockMvc.perform(get("/recipe/1/image"))
        	//then
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/imageUploadForm"))
            .andExpect(model().attributeExists("recipe"));
        // Si no se indica el número de llamadas en el método "times", por defecto es 1
        verify(recipeService, times(1)).findRecipeCommandById(anyLong());

    }
    
    @Test
    public void getImageNumberFormatExceptionTest() throws Exception {
    	//when
        mockMvc.perform(get("/recipe/asdf/recipeimage"))
        	//then
            .andExpect(status().isBadRequest())
            .andExpect(view().name("400error"));
        verifyZeroInteractions(recipeService);
    }

    @Test
    public void handleImagePostTest() throws Exception {
    	//given
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                		"fake image text".getBytes());
        //when
        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
        	//then
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/1/show"))
            .andExpect(header().string("Location", "/recipe/1/show")); // Cuando se hace una redirección, se establece la url destino en la propiedad "Location" de la cabecera de la petición http 
        // Si no se indica el número de llamadas en el método "times", por defecto es 1
        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }
    
    @Test
    public void renderImageFromDBTest() throws Exception {
        //given
        String s = "fake image text";
        when(imageService.getImage(anyLong())).thenReturn(s.getBytes());
        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
        	//then
            .andExpect(status().isOk())
            .andReturn().getResponse();
        byte[] reponseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, reponseBytes.length);
    }
}
