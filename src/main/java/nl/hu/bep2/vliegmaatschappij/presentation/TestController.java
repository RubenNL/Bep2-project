package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.TestClass;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	private final TestClass testClass;
	public TestController(TestClass testClass) {
		this.testClass = testClass;
	}
	@GetMapping()
	public String test() {
		return "Test van spring! response van spring resource: "+ testClass.returnTrue();
	}
}