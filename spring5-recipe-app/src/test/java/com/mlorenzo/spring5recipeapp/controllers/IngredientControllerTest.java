package com.mlorenzo.spring5recipeapp.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mlorenzo.spring5recipeapp.commands.IngredientCommand;
import com.mlorenzo.spring5recipeapp.services.IngredientService;
import com.mlorenzo.spring5recipeapp.services.UnitOfMeasureService;

public class IngredientControllerTest {
	
	@Mock // Crea una Mock del servicio "ingredientService"
    IngredientService ingredientService;
	
	@Mock // Crea una Mock del servicio "unitOfMeasureService"
	UnitOfMeasureService unitOfMeasureService;

	@InjectMocks // Esta anotación crea una instancia del controlador "IngredientController" e inyecta los Mocks de los servicios "recipeService","ingredientService" y "unitOfMeasureService"
    IngredientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
    	//Para poder usar Mockito en esta clase de pruebas
        MockitoAnnotations.initMocks(this); // Otra opción a esta línea es anotar la clase con @ExtendWith(MockitoExtension.class)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredientsTest() throws Exception {
    	//given
        Set<IngredientCommand> ingredientCommands = new HashSet<>();
        when(ingredientService.getIngredients(anyLong())).thenReturn(ingredientCommands);
        //when
        mockMvc.perform(get("/recipe/1/ingredients"))
        	//then
        	.andExpect(status().isOk())
        	.andExpect(view().name("recipe/ingredient/list"))
            .andExpect(model().attributeExists("recipeId"))
            .andExpect(model().attributeExists("ingredients"));
        // Si no se indica el número de llamadas en el método "times", por defecto es 1
        verify(ingredientService, times(1)).getIngredients(anyLong());
    }
    
    @Test
    public void showIngredientTest() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        //when
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
        	//then
        	.andExpect(status().isOk())
            .andExpect(view().name("recipe/ingredient/show"))
            .andExpect(model().attributeExists("ingredient"));
        // Si no se indica el número de llamadas en el método "times", por defecto es 1
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
    }
    
    @Test
    public void newIngredientFormTest() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        when(ingredientService.createNewIngredient(anyLong())).thenReturn(ingredientCommand);
        // En realidad no hace falta este "when" porque, por defecto, Mockito crea instancias de las colecciones
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
        //when
        mockMvc.perform(get("/recipe/1/ingredient/new"))
        	//then
            .andExpect(status().isOk())
            .andExpect(view().name("recipe/ingredient/ingredientForm"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(model().attributeExists("uomList"));
        verify(ingredientService, times(1)).createNewIngredient(anyLong());

    }
    
    @Test
    public void updateIngredientFormTest() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        // En realidad no hace falta este "when" porque, por defecto, Mockito crea instancias de las colecciones
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
        //when
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
        	//then
    		.andExpect(status().isOk())
            .andExpect(view().name("recipe/ingredient/ingredientForm"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(model().attributeExists("uomList"));
        // Si no se indica el número de llamadas en el método "times", por defecto es 1
        verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(anyLong(), anyLong());
        verify(unitOfMeasureService, times(1)).listAllUoms();
    }
    
    @Test
    public void saveOrUpdateTest() throws Exception {
        //given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);
        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);
        //when
        mockMvc.perform(post("/recipe/2/ingredient")
        		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
        	//then
        	.andExpect(status().is3xxRedirection())
        	.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
        // Si no se indica el número de llamadas en el método "times", por defecto es 1
        verify(ingredientService).saveIngredientCommand(any());
    }
    
    @Test
    public void deleteIngredientTest() throws Exception {
    	//when
    	mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
    		//then
		   .andExpect(status().is3xxRedirection())
		   .andExpect(view().name("redirect:/recipe/2/ingredients"));
        // Si no se indica el número de llamadas en el método "times", por defecto es 1
        verify(ingredientService).deleteById(anyLong(), anyLong());
    }
}
