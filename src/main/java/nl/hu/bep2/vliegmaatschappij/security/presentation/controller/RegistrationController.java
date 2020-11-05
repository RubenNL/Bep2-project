package nl.hu.bep2.vliegmaatschappij.security.presentation.controller;

import nl.hu.bep2.vliegmaatschappij.security.application.UserService;
import nl.hu.bep2.vliegmaatschappij.security.presentation.dto.Registration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/register")
public class RegistrationController {
	private final UserService userService;

	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public void register(@Validated @RequestBody Registration registration) {
		this.userService.register(
				registration.firstName,
				registration.lastName,
				registration.birthday,
				registration.email,
				registration.password,
				registration.phone,
				registration.nationality
		);
	}
	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{username}")
	public void register(@PathVariable("username") String username) {
		this.userService.delete(username);
	}
}
