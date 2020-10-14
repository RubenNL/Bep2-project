package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.TravelClassService;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.TravelClassDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travelclass")
public class TravelClassController {
    private final TravelClassService service;

    public TravelClassController(TravelClassService service) {
        this.service = service;
    }

//    @GetMapping("/all")
//    public TravelClassDTO getAll() {
//        TravelClassDTO travelclasses = service.getAll();
//        return new TravelClassDTO();
//    }
}
