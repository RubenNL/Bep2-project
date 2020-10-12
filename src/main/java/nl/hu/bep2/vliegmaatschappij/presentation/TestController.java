package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.Main;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	private final Main main;
	public TestController(Main main) {
		this.main=main;
	}
	@GetMapping("/")
	public String test() {
		return "Test van spring! response van spring resource: "+main.returnTrue();
	}
}