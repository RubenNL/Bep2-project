package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.application.CustomerFlightService;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.FindFlightDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.FlightModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//todo Swagger UI annotations
@RestController
@RequestMapping("/customer")
public class CustomerFlightController {
	private final FlightModelAssembler assembler;
	private final CustomerFlightService service;

	public CustomerFlightController(CustomerFlightService service, FlightModelAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@RolesAllowed("USER")
	@PostMapping("/findflight")
	public CollectionModel<EntityModel<Flight>> findFlights(@RequestBody FindFlightDTO findFlightDTO) {
		List<Flight> flights = service.FindAvailableFlights(findFlightDTO.departureCode, findFlightDTO.arrivalCode, findFlightDTO.departureDate, findFlightDTO.flightClass);
		List<EntityModel<Flight>> flightEntitys = flights.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flightEntitys, linkTo(methodOn(BookingController.class).all()).withSelfRel());
	}

	@RolesAllowed("USER")
	@GetMapping("/findflightbytime")
	public CollectionModel<EntityModel<Flight>> findFlightsbytime() {
		List<Flight> flights = service.FindAllAvailableFlights();
		List<EntityModel<Flight>> flightEntitys = flights.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flightEntitys, linkTo(methodOn(BookingController.class).all()).withSelfRel());
	}

}

