package com.mlorenzo.springrestclientexamples.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.mlorenzo.springrestclientexamples.services.ApiService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class UserController {
	private final ApiService apiService;

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }
    
    @PostMapping("/users")
    public String formPost(Model model, Integer limit) {
    	 model.addAttribute("users",apiService.getUsers(limit));
    	 return "userlist";
    }
}
