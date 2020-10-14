package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.AirportService;
import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.exceptions.AirportNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.AirportDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airport")
public class AirportController {
    private final AirportService service;

    public AirportController(AirportService service) {
        this.service = service;
    }

    @GetMapping("/{code}")
    public AirportDTO showAirport(@PathVariable String code) throws AirportNotFoundException {
        Airport airport = service.showAirport(code);
        return new AirportDTO(airport);
    }
}
