package com.akh.SpringSecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.akh.SpringSecurity.models.RegisterDto;
import com.akh.SpringSecurity.repositories.AppUserRepository;

@Controller
public class AccountController {
@Autowired
private AppUserRepository repo;

@GetMapping("/register")
public String register(Model model) {
	RegisterDto registerDto = new RegisterDto();
	model.addAttribute(registerDto);
	return "register";
}
}
