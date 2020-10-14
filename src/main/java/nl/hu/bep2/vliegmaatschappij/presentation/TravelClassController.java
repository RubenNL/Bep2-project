package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.TravelClassService;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.TravelClassDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travelclass")
public class TravelClassController {
	private final TravelClassService service;

	public TravelClassController(TravelClassService service) {
		this.service = service;
	}

	@GetMapping("/{id}")
	public TravelClassDTO getById(@PathVariable("id") int id) {
		TravelClass travelClass = service.getById(id);
		TravelClassDTO travelDTO = new TravelClassDTO(travelClass);
		return travelDTO;
	}
}
