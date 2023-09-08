package com.mlorenzo.sfgpetclinic.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.services.OwnerService;

@Controller
@RequestMapping("/owners")
public class OwnerController {
	private final OwnerService ownerService;
	
	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}
	
	@InitBinder
	public void dataBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@RequestMapping("/find") // Por defecto, el tipo de método de la petición http es GET
	public String findOwners(Model model) {
		model.addAttribute("owner",Owner.builder().build());
		return "owners/findOwners";
	}
	
	@GetMapping
	public String processFindForm(Owner owner, BindingResult result, Model model) {
		// allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }
        // find owners by last name
        List<Owner> results = ownerService.findAllByLastNameLike("%"+ owner.getLastName() + "%");
        if (results.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (results.size() == 1) {
            // 1 owner found
            owner = results.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            // multiple owners found
            model.addAttribute("selections", results);
            return "owners/ownersList";
        }
	}
	
	@GetMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable(name = "ownerId") Long id) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails"); // Se le pasa el nombre de la vista a retornar
		mav.addObject("owner",ownerService.findById(id));
		return mav;
	}
	
	@GetMapping("/new")
	public String initCreationForm(Model model) {
		model.addAttribute("owner",Owner.builder().build());
		return "owners/createOrUpdateOwnerForm";
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if(result.hasErrors())
			return "owners/createOrUpdateOwnerForm";
		Owner savedOwner = ownerService.save(owner);
		return "redirect:/owners/" + savedOwner.getId();
	}
	
	@GetMapping("/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable Long ownerId, Model model) {
		model.addAttribute("owner",ownerService.findById(ownerId));
		return "owners/createOrUpdateOwnerForm";
	}
	
	@PostMapping("/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable(value = "ownerId") Long id) {
		if(result.hasErrors())
			return "owners/createOrUpdateOwnerForm";
		owner.setId(id);
		Owner updatedOwner = ownerService.save(owner);
		return "redirect:/owners/" + updatedOwner.getId();
	}
}
