package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.TestClass;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/test")
public class TestController {
	private final TestClass testClass;
	public TestController(TestClass testClass) {
		this.testClass = testClass;
	}
	@GetMapping
	@RolesAllowed("EMPLOYEE")
	public String test(Authentication authentication) {
		System.out.println(authentication);
		return "Test van spring! response van spring resource: "+ testClass.returnTrue();
	}
}