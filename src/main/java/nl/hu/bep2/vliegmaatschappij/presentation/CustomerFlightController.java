package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.application.CustomerFlightService;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.FindFlightDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.FlightModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@RestController
@RequestMapping("/customer")
public class CustomerFlightController {
	private final FlightModelAssembler assembler;
	private final CustomerFlightService service;

	public CustomerFlightController(CustomerFlightService service, FlightModelAssembler assembler) {
		this.service = service;
		this.assembler = assembler;
	}

	@Operation(summary = "Find a flight")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flights were found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Flight.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flights couldn't be found",
					content = @Content) })

	@RolesAllowed({"USER"})
	@GetMapping("/findflight")
	public CollectionModel<EntityModel<Flight>> findFlights(@RequestParam String departureCode, @RequestParam String arrivalCode, @RequestParam String departureDate) {
		LocalDate departureDateFormatted=LocalDate.parse(departureDate);
		List<Flight> flights = service.FindAvailableFlights(departureCode, arrivalCode, departureDateFormatted);
		List<EntityModel<Flight>> flightEntitys = flights.stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flightEntitys, linkTo(methodOn(FlightController.class).all()).withSelfRel());
	}

	@Operation(summary = "Find a flight by time")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flights have been found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Flight.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flight couldn't be found",
					content = @Content) })

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

