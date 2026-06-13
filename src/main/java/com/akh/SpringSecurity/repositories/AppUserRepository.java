package com.akh.SpringSecurity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akh.SpringSecurity.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

	public AppUser findByEmail(String email);
}
