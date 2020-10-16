package nl.hu.bep2.vliegmaatschappij.security.presentation.controller;

import nl.hu.bep2.vliegmaatschappij.security.application.UserService;
import nl.hu.bep2.vliegmaatschappij.security.presentation.dto.SetEmployee;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	private final UserService userService;
	public EmployeeController(UserService userService) {
		this.userService = userService;
	}
	@PostMapping("/setEmployee")
	public void setEmployee(@Validated @RequestBody SetEmployee setEmployee) {
		userService.setEmployee(setEmployee.username,setEmployee.employee);
	}
}
