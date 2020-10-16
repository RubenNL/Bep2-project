package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.Flightroute;
import nl.hu.bep2.vliegmaatschappij.exceptions.FlightNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FlightService {
    private final SpringFlightRepository flightService;

    public FlightService(SpringFlightRepository flightService) {
        this.flightService = flightService;
    }


    public Flight showFlight(int flightCode) throws FlightNotFoundException {
        return flightService.findById(flightCode)
                .orElseThrow(() -> new FlightNotFoundException("Airport with the code: " + flightCode + " not found."));
    }

    public void createFlight(int id, LocalDate departureTime, LocalDate arrivalTime) {

        Flight flight = new Flight(id,departureTime,arrivalTime);

        this.flightService.save(flight);
    }

    public void delete(int code) {
        this.flightService.deleteById(code);
    }
}
