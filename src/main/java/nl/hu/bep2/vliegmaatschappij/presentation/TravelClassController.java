package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.TravelClassService;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.TravelClassDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/new")
	public TravelClassDTO newTravelClass(@Validated @RequestBody TravelClassDTO travelclass){
		TravelClass newTravelClass = service.newTravelClass(travelclass);
		TravelClassDTO travelDTO = new TravelClassDTO(newTravelClass);
		return travelDTO;
	}

//	@PostMapping("/update/{id}")
//	public TravelClassDTO updateTravelClass(@PathVariable int id){
//		TravelClass newTravelClass = service.updateTravelClass(id);
//		TravelClassDTO travelDTO = new TravelClassDTO(newTravelClass);
//		return travelDTO;
//	}
}
