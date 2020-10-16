package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.AirportService;
import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.exceptions.AirportNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.AirportDTO;
import nl.hu.bep2.vliegmaatschappij.security.presentation.dto.Registration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/airport")
public class AirportController {
    private final AirportService service;

    public AirportController(AirportService service) {
        this.service = service;
    }

    @PostMapping
    public void createAirport(@Validated @RequestBody Airport airport) {
        this.service.createAirport(
                airport.getCode(),
                airport.getName(),
                airport.getLat(),
                airport.getLng(),
                airport.getPlace(),
                airport.getCountry(),
                airport.getFlightRoute()
        );
    }

    //@RolesAllowed("admin")
    @GetMapping("/{code}")
    public AirportDTO showAirport(@PathVariable String code) throws AirportNotFoundException {
        Airport airport = service.showAirport(code);
        return new AirportDTO(airport);
    }

    @PostMapping("/{code}")
    public void updateAirport(@Validated @RequestBody Airport airport){
        this.service.createAirport(
                airport.getCode(),
                airport.getName(),
                airport.getLat(),
                airport.getLng(),
                airport.getPlace(),
                airport.getCountry(),
                airport.getFlightRoute()
        );
    }

    @DeleteMapping("/{code}")
    public void deleteAirport(@PathVariable String code) throws AirportNotFoundException {
        this.service.delete(code);
    }
}
