package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringAirportRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRouteRepository;
import nl.hu.bep2.vliegmaatschappij.domein.FlightRoute;
import nl.hu.bep2.vliegmaatschappij.exceptions.FlightRouteNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.FlightRouteDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.FlightRouteAssembler;
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
@RequestMapping("/flightroute")
public class FlightRouteController {
	private final SpringFlightRouteRepository flightRouteRepo;
	private final FlightRouteAssembler assembler;
	private final SpringAirportRepository airportRepository;

	public FlightRouteController(SpringFlightRouteRepository flightRouteRepo, FlightRouteAssembler assembler, SpringAirportRepository airportRepository) {
		this.flightRouteRepo = flightRouteRepo;
		this.assembler = assembler;
		this.airportRepository = airportRepository;
	}

	@Operation(summary = "Create a new flightroute")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A new flightroute has been created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = FlightRoute.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "FlightRoute couldn't be created",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PostMapping
	public ResponseEntity<?> newFlightRoute(@RequestBody FlightRouteDTO flightRouteDTO) {
		FlightRoute flightRoute=new FlightRoute();
		flightRoute.setDeparture(airportRepository.getOne(flightRouteDTO.departure));
		flightRoute.setDestination(airportRepository.getOne(flightRouteDTO.destination));
		EntityModel<FlightRoute> entityModel = assembler.toModel(flightRouteRepo.save(flightRoute));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Get a flightroute by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flightroute found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = FlightRoute.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flightroute couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/{id}")
	public EntityModel<FlightRoute> one(@PathVariable int id) {
		FlightRoute flightRoute = flightRouteRepo.findById(id)
				.orElseThrow(() -> new FlightRouteNotFoundException("Travel Class not found"));
		return assembler.toModel(flightRoute);
	}

	@Operation(summary = "Get all flightroutes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flightroutes found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = FlightRoute.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flightroutes couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/all")
	public CollectionModel<EntityModel<FlightRoute>> all() {
		List<EntityModel<FlightRoute>> flightroutes = flightRouteRepo.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flightroutes, linkTo(methodOn(FlightRouteController.class).all()).withSelfRel());
	}

	@Operation(summary = "Replace a flightroute by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flightroute replaced",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = FlightRoute.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flightroute couldn't be replaced",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceFlightRoute(@RequestBody FlightRouteDTO flightRouteDTO, @PathVariable int id) {
		FlightRoute newFlightRoute=new FlightRoute();
		newFlightRoute.setDeparture(airportRepository.getOne(flightRouteDTO.departure));
		newFlightRoute.setDestination(airportRepository.getOne(flightRouteDTO.destination));
		FlightRoute updatedFlightRoute = flightRouteRepo.findById(id)
				.map(flightroute -> {
					flightroute.setDeparture(newFlightRoute.getDeparture());
					flightroute.setDestination(newFlightRoute.getDestination());
					return flightRouteRepo.save(flightroute);
				})
				.orElseGet(() -> {
					newFlightRoute.setId(id);
					return flightRouteRepo.save(newFlightRoute);
				});
		EntityModel<FlightRoute> entityModel = assembler.toModel(updatedFlightRoute);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Delete a flightroute by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Flightroute deleted",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = FlightRoute.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Flightroute couldn't be deleted",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{id}")
	public void deleteFlightRoute(@PathVariable int id) {
		flightRouteRepo.deleteById(id);
	}
}
