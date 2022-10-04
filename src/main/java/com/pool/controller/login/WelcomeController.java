package com.pool.controller.login;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/welcome")
public class WelcomeController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/hi")
	public String hi() {
		return "Hello! H!";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/bye")
	public String bye() {
		return "Bye!";
	}
	
}
