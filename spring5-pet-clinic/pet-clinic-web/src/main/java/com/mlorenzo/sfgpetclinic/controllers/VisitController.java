package com.mlorenzo.sfgpetclinic.controllers;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mlorenzo.sfgpetclinic.model.Pet;
import com.mlorenzo.sfgpetclinic.model.Visit;
import com.mlorenzo.sfgpetclinic.services.PetService;
import com.mlorenzo.sfgpetclinic.services.VisitService;

@Controller
public class VisitController {
	private final VisitService visitService;
	private final PetService petService;
	
	public VisitController(VisitService visitService, PetService petService) {
		this.visitService = visitService;
		this.petService = petService;
	}
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
		// Para que la propiedad "date" de tipo "LocalDate" de una entidad de tipo Visit se valide correctamente cuando se envían los datos del formulario y se procesan en el método handler "processNewVisitForm" de este controlador 
		// Cualquier propiedad de tipo "LocalDate" se manejará o se tratará aquí
		// Otra opción a este código es usar la anotación @DateTimeFormat en la propiedad "date" de tipo LocalDate de la entidad Visit estableciendo el formato de fecha deseado en el atrbuto "pattern" de esa anotación(Por ejemplo, @DateTimeFormat(pattern = "yyyy-MM-dd"))
		// La diferencia entre usar @DateTimeFormat y el código de abajo es que la anotación sólo afecta a la propiedad de la entidad en la que se establece dicha anotación y el código de abajo actua de manera global para todas las propiedades de tipo LocalDate que llegan a este controlador
		dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
	
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
	}
	
	// La anotación @ModelAttribute crea un atributo global para todas las vistas devueltas por los métodos handler de este controlador
	// En este caso crea el atributo global "visit" con la visita devuelta por este método
	// El valor del parámetro de entrada "petId" de tipo Long se obtiene de la url "/owners/{ownerId}/pets/{petId}/visits/new" asociada al método handler "initNewVisitForm". Esto funciona poque cuando se invoque al método handler "initNewVisitForm", Spring MVC invocará automaticamente antes a este método por estar anotado con @ModelAttribute
	@ModelAttribute("visit")
	public Visit loadPetWithVisit(@PathVariable Long petId, Map<String, Object> model) {
		Pet pet = petService.findById(petId);
		model.put("pet", pet);
		Visit visit = Visit.builder().build();
		pet.getVisits().add(visit);
		visit.setPet(pet);
		return visit;
	}
	
	// Spring MVC llamará al método "loadPetWithVisit" antes de invocar a este método porque está anotado con la anotación @ModelAttribute
	@GetMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable Long petId, Map<String, Object> model) {
		return "pets/createOrUpdateVisitForm";
	}
	
	// Spring MVC llamará al método "loadPetWithVisit" antes de invocar a este método porque está anotado con la anotación @ModelAttribute
	@PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
		if(result.hasErrors())
			return "pets/createOrUpdateVisitForm";
		visitService.save(visit);
		return "redirect:/owners/{ownerId}";
	}
}
