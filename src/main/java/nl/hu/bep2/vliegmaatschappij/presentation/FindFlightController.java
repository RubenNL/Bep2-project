package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.FindFlightService;
import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Booking;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.Planetype;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.FindFlightDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.FlightModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class FindFlightController {
	private final FlightModelAssembler assembler;
	private final FindFlightService service;

	public FindFlightController(FindFlightService service, FlightModelAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@GetMapping("/findflight")
	public CollectionModel<EntityModel<Flight>> findFlights(@RequestBody FindFlightDTO findFlightDTO) {
		List<Flight> flights = service.FindFlights(findFlightDTO.departureCode, findFlightDTO.arrivalCode, findFlightDTO.departureDate);
		List<EntityModel<Flight>> flightEntitys = flights.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flightEntitys, linkTo(methodOn(BookingController.class).all()).withSelfRel());
	}
}

