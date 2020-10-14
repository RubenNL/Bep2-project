package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.FlightService;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.exceptions.FlightNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.FlightDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight")
public class FlightController {
    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public FlightDTO showFlight(@PathVariable int id) throws FlightNotFoundException {
        Flight flight = service.showFlight(id);
        return new FlightDTO(flight);
    }
}