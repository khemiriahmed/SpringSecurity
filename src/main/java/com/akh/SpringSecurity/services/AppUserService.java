package com.akh.SpringSecurity.services;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.akh.SpringSecurity.models.AppUser;
import com.akh.SpringSecurity.repositories.AppUserRepository;

@Service
public class AppUserService implements UserDetailsService {

	@Autowired
	private AppUserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    AppUser appUser = repo.findByEmail(email);
	    if (appUser != null) {
	        return  User.withUsername(appUser.getEmail())
	          
	            .password(appUser.getPassword())
                .roles(appUser.getRole())
                .build();
	            

	    } 
	    throw new UsernameNotFoundException("User not found: " + email);
	    }
	}
