package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.PlaneService;
import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import nl.hu.bep2.vliegmaatschappij.exceptions.PlaneNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.PlaneDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plane")
public class PlaneController {
	private final PlaneService service;

	public PlaneController(PlaneService service) {
		this.service = service;
	}

	@GetMapping("/{code}")
	public PlaneDTO showPlane(@PathVariable String code) throws PlaneNotFoundException {
		Plane plane = service.showPlane(code);
		return new PlaneDTO(plane);
	}
}
