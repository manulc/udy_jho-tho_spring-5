package com.mlorenzo.springrestclientexamples.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

import com.mlorenzo.springrestclientexamples.services.ApiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	private final ApiService apiService;

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange) {
        model.addAttribute("users",
                apiService.getUsers(serverWebExchange.getFormData()
                                .map(data -> Integer.valueOf(data.getFirst("limit")))));
        return "userlist";
    }
}
