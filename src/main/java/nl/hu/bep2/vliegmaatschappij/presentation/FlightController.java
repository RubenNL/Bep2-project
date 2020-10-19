package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.FlightService;
import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.exceptions.AirportNotFoundException;
import nl.hu.bep2.vliegmaatschappij.exceptions.FlightNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.FlightDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flight")
public class FlightController {
    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @PostMapping
    public int createFlight(@Validated @RequestBody FlightDTO flight) {
        return this.service.createFlight(
                flight.departureTime,
                flight.arrivalTime
        ).getId();
    }

    @GetMapping("/{id}")
    public FlightDTO showFlight(@PathVariable int id) throws FlightNotFoundException {
        Flight flight = service.showFlight(id);
        return new FlightDTO(flight);
    }

    /*@PostMapping("/{id}")
    public void updateFlight(@Validated @RequestBody Flight flight){
        this.service.createFlight(
                flight.getId(),
                flight.getdepartureTime(),
                flight.getarrivalTime()
        );
    }*/

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable int id) {
        this.service.delete(id);
    }


}