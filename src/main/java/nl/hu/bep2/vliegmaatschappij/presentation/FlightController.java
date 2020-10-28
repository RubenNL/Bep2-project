package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRouteRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringPlaneRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;

import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.FlightDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.FlightModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/flight")
public class FlightController {
	private final SpringFlightRepository repository;
	private final FlightModelAssembler assembler;
	private final SpringFlightRouteRepository routeRepository;
	private final SpringPlaneRepository planeRepository;

	public FlightController(SpringFlightRepository repository, FlightModelAssembler assembler, SpringFlightRouteRepository routeRepository, SpringPlaneRepository planeRepository) {
		this.repository = repository;
		this.assembler = assembler;
		this.routeRepository = routeRepository;
		this.planeRepository = planeRepository;
	}

	@Operation(summary = "Create a new flight")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A new flight is ready for departure",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Flight.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flight couldn't be created",
					content = @Content) })

	@RolesAllowed("EMPLOYEE")
	@PostMapping
	ResponseEntity<?> newFlight(@RequestBody FlightDTO flightDTO) {
		Flight flight=new Flight();
		flight.setArrivalTime(flightDTO.arrivalTime);
		flight.setDepartureTime(flightDTO.departureTime);
		flight.setRoute(routeRepository.getOne(flightDTO.route));
		flight.setPlane(planeRepository.getOne(flightDTO.plane));
		EntityModel<Flight> entityModel = assembler.toModel(repository.save(flight));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	@Operation(summary = "Get a flight by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flight found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Flight.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flight has disappeared",
					content = @Content) })


	@GetMapping("/{id}")
	public EntityModel<Flight> one(@PathVariable int id) {
		Flight flight = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("flight not found"));
		return assembler.toModel(flight);
	}
	@Operation(summary = "Get all flights")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All flights found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Flight.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flights weren't found!",
					content = @Content) })


	@GetMapping("/all")
	public CollectionModel<EntityModel<Flight>> all() {
		List<EntityModel<Flight>> flights = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flights, linkTo(methodOn(FlightController.class).all()).withSelfRel());
	}

	@Operation(summary = "replace a flight by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flight has been replaced",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Flight.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flight couldn't be replaced",
					content = @Content) })

	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{id}")
	ResponseEntity<?> replaceFlight(@RequestBody Flight newFlight, @PathVariable int id) {
		Flight updatedFlight = repository.findById(id)
				.map(flight -> {
					flight.setArrivalTime(newFlight.getArrivalTime());
					flight.setDepartureTime(newFlight.getDepartureTime());
					return repository.save(flight);
				})
				.orElseGet(() -> {
					newFlight.setId(id);
					return repository.save(newFlight);
				});
		EntityModel<Flight> entityModel = assembler.toModel(updatedFlight);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}



	@Operation(summary = "Delete a flight by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flight has been deleted",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Flight.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flight couldn't be deleted",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{id}")
	public void deleteFlight(@PathVariable int id) {
		repository.deleteById(id);
	}
}