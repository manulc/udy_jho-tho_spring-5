package com.mlorenzo.spring5mvcrest.api.v1.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;

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

import com.mlorenzo.spring5mvcrest.api.v1.model.CategoryDTO;
import com.mlorenzo.spring5mvcrest.exceptions.ResourceNotFoundException;
import com.mlorenzo.spring5mvcrest.services.CategoryService;

public class CategoryControllerTest {
	private static final String NAME = "Jim";

    @Mock
    CategoryService categoryService;

    @InjectMocks // Esta anotación hace que todas las propiedad anotadas con @Mock(En este caso, sólo "categoryService" de tipo "CategoryService") se inyecten dentro de este controlador "categoryController"
    CategoryController categoryController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Comentamos esta línea porque ya no hace falta debido a que estamos usando la anotación @InjectMocks sobre la propiedad del controlador "categoryController"
        //categoryController = new CategoryController(categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
        			.setControllerAdvice(new RestResponseEntityExceptionHandler())
        			.build();
    }

    @Test
    public void testListCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1l);
        category1.setName(NAME);
        CategoryDTO category2 = new CategoryDTO();
        category2.setId(2l);
        category2.setName("Bob");
        List<CategoryDTO> categories = Arrays.asList(category1, category2);
        when(categoryService.getAllCategories()).thenReturn(categories);
        mockMvc.perform(get(CategoryController.BASE_URL)
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    public void testGetByNameCategories() throws Exception {
        CategoryDTO category1 = new CategoryDTO();
        category1.setId(1l);
        category1.setName(NAME);
        when(categoryService.getCategoryByName(anyString())).thenReturn(category1);
        mockMvc.perform(get(CategoryController.BASE_URL + "/Jim")
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));
    }
    
    @Test
    public void testGetByNameNotFound() throws Exception {
        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(CategoryController.BASE_URL + "/Foo")
        		// Es obligatorio indicar la propiedad "Accept" en la cabecera de la petición http cuando recibamos contenido en la respuesta de esa petición porque esta aplicación es capaz de enviar dicho contenido en formato Json o Xml
        		.accept(MediaType.APPLICATION_JSON)) // Indicamos que deseamos recibir el contenido de la respuesta de la petición http en formato Json
                .andExpect(status().isNotFound());
    }

}
