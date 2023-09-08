package com.mlorenzo.sfgpetclinic.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mlorenzo.sfgpetclinic.model.Vet;
import com.mlorenzo.sfgpetclinic.services.VetService;

@Controller
public class VetController {
	private final VetService vetService;
	
	public VetController(VetService vetService) {
		this.vetService = vetService;
	}
	
	@RequestMapping({"/vets","/vets.html","/vets/index","/vets/index.html"}) // Por defecto, el tipo de método de la petición http es GET
	public String listVets(Model model) {
		model.addAttribute("vets",vetService.findAll());
		return "vets/index";
	}
	
	// La anotación @ResponseBody renderiza el retorno de este método en un JSON por defecto pero se puede configurar para que lo haga en un XML
	@RequestMapping("/api/vets")
	public @ResponseBody Set<Vet> getVetsJson(){
		return vetService.findAll();
	}
}
