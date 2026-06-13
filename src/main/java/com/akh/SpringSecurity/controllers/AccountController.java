package com.akh.SpringSecurity.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.akh.SpringSecurity.models.AppUser;
import com.akh.SpringSecurity.models.RegisterDto;
import com.akh.SpringSecurity.repositories.AppUserRepository;

import jakarta.validation.Valid;

@Controller
public class AccountController {

    @Autowired
    private AppUserRepository repo;

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("registerDto")) {
            model.addAttribute("registerDto", new RegisterDto());
        }
        if (!model.containsAttribute("success")) {
            model.addAttribute("success", false);
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerDto") RegisterDto registerDto,
                                BindingResult result,
                                RedirectAttributes redirectAttrs,
                                Model model) {

        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(new FieldError("registerDto", "confirmPassword", "Passwords do not match"));
        }

        AppUser existing = repo.findByEmail(registerDto.getEmail());
        if (existing != null) {
            result.addError(new FieldError("registerDto", "email", "Email already exists"));
        }

        if (result.hasErrors()) {
            model.addAttribute("success", false);
            return "register";
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();
            AppUser user = new AppUser();
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setEmail(registerDto.getEmail());
            user.setPhone(registerDto.getPhone());
            user.setAddress(registerDto.getAddress());
            user.setRole("client");
            user.setCreatedAt(new Date());
            user.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
            repo.save(user);

            redirectAttrs.addFlashAttribute("success", true);
            redirectAttrs.addFlashAttribute("registerDto", new RegisterDto());
            return "redirect:/register";

        } catch (Exception ex) {
            result.addError(new FieldError("registerDto", "firstName", ex.getMessage()));
            model.addAttribute("success", false);
            return "register";
        }
    }
}