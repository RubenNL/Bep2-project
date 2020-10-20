package nl.hu.bep2.vliegmaatschappij.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectToSwagger {
	@RequestMapping("")
	public String swagger() {
		return "redirect:/swagger-ui.html";
	}
}