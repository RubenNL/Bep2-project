package nl.hu.bep2.vliegmaatschappij.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectToIndex {
	@RequestMapping("")
	public String homePage() {
		return "swagger-ui.html";
	}
}
