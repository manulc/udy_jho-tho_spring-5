package com.mlorenzo.sfgpetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping({"","/","index","index.html"}) // Por defecto, el tipo de método de la petición http es GET
	public String index() {
		return "index";
	}
	
	@RequestMapping("/oups") // Por defecto, el tipo de método de la petición http es GET
	public String oups() {
		return "notimplemented";
	}

}
