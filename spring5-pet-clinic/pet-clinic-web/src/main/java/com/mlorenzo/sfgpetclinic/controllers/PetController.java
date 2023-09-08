package com.mlorenzo.sfgpetclinic.controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.model.Pet;
import com.mlorenzo.sfgpetclinic.model.PetType;
import com.mlorenzo.sfgpetclinic.services.OwnerService;
import com.mlorenzo.sfgpetclinic.services.PetService;
import com.mlorenzo.sfgpetclinic.services.PetTypeService;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {
	private final PetService petService;
	private final PetTypeService petTypeService;
	private final OwnerService ownerService;
	
	public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
		this.petService = petService;
		this.ownerService = ownerService;
		this.petTypeService = petTypeService;
	}

	// La anotación @ModelAttribute crea un atributo global para todas las vistas devueltas por los métodos handler de este controlador
	// En este caso crea el atributo global "types" con todos los tipos de mascotas devueltos por este método
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes(){
		return petTypeService.findAll();
	}
	
	// La anotación @ModelAttribute crea un atributo global para todas las vistas devueltas por los métodos handler de este controlador
	// En este caso crea el atributo global "owner" con el propietario devuelto por este método
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable Long ownerId) {
		return ownerService.findById(ownerId);
	}
	
	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	// Spring MVC llamará a los métodos "populatePetTypes" y "findOwner" antes de invocar a este método porque están anotados con la anotación @ModelAttribute
	// El parámetro de entrada "owner" de tipo Owner es inyectado en este método handler porque se creó el atributo global "owner" del modelo a través del método "findOwner" y de la anotación @ModelAttribute
	@GetMapping("/new")
	public String initCreationForm(Owner owner,Model model) {
		Pet pet = Pet.builder().build();
		pet.setOwner(owner);
		model.addAttribute("pet",pet);
		return "pets/createOrUpdatePetForm";
	}
	
	// Spring MVC llamará a los métodos "populatePetTypes" y "findOwner" antes de invocar a este método porque están anotados con la anotación @ModelAttribute
	// El parámetro de entrada "owner" de tipo Owner es inyectado en este método handler porque se creó el atributo global "owner" del modelo a través del método "findOwner" y de la anotación @ModelAttribute
	@PostMapping("/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, Model model) {
		if(StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(),true) != null)
			result.rejectValue("name","duplicate","alredy exists");
		pet.setOwner(owner);
		if(result.hasErrors()) {
			model.addAttribute("pet",pet);
			return "pets/createOrUpdatePetForm";
		}
		else {
			owner.getPets().add(pet);
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}
	
	// Spring MVC llamará a los métodos "populatePetTypes" y "findOwner" antes de invocar a este método porque están anotados con la anotación @ModelAttribute
	@GetMapping("/{petId}/edit")
	public String initUpdateForm(@PathVariable(name = "petId") Long id, Model model) {
		model.addAttribute("pet",petService.findById(id));
		return "pets/createOrUpdatePetForm";
	}
	
	// Spring MVC llamará a los métodos "populatePetTypes" y "findOwner" antes de invocar a este método porque están anotados con la anotación @ModelAttribute
	// El parámetro de entrada "owner" de tipo Owner es inyectado en este método handler porque se creó el atributo global "owner" del modelo a través del método "findOwner" y de la anotación @ModelAttribute
	@PostMapping("/{petId}/edit")
	public String processUpdateForm(Owner owner, @Valid Pet pet, BindingResult result, Model model) {
		pet.setOwner(owner);
		if(result.hasErrors()) {
			model.addAttribute("pet",pet);
			return "pets/createOrUpdatePetForm";
		}
		else {
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}
}
