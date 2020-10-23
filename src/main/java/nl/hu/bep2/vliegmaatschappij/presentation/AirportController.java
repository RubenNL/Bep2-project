package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringAirportRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.AirportModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/airport")
public class AirportController {
	private final SpringAirportRepository repository;
	private final AirportModelAssembler assembler;

	public AirportController(SpringAirportRepository repository, AirportModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@Operation(summary = "Create a new airport.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Airport Created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Airport.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid Information provided",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Airport was not created!",
					content = @Content) })
	@PostMapping
	ResponseEntity<?> newAirport(@RequestBody Airport airport) {
		EntityModel<Airport> entityModel = assembler.toModel(repository.save(airport));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Get an Airport by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the airport",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Airport.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid code supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Airport not found",
					content = @Content) })
	@GetMapping("/{code}")
	public EntityModel<Airport> one(@PathVariable String code) throws NotFoundException {
		Airport airport = repository.findById(code)
				.orElseThrow(() -> new NotFoundException("Airport not found"));
		return assembler.toModel(airport);
	}

	@Operation(summary = "Get all airports")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Airports found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Airport.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Airports not found",
					content = @Content) })
	@GetMapping("/all")
	public CollectionModel<EntityModel<Airport>> all() {
		List<EntityModel<Airport>> airports = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(airports, linkTo(methodOn(AirportController.class).all()).withSelfRel());
	}

	@Operation(summary = "Replace an airport")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Airport replaced",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Airport.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid code supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Airport could not be replaced",
					content = @Content) })
	@PutMapping("/{code}")
	ResponseEntity<?> replaceAirport(@RequestBody Airport newAirport, @PathVariable String code) {
		Airport updatedAirport = repository.findById(code)
				.map(airport -> {
					airport.setName(newAirport.getName());
					airport.setLat(newAirport.getLat());
					airport.setLng(newAirport.getLng());
					airport.setCountry(newAirport.getCountry());
					airport.setPlace(newAirport.getPlace());
					return repository.save(airport);
				})
				.orElseGet(() -> {
					newAirport.setCode(code);
					return repository.save(newAirport);
				});
		EntityModel<Airport> entityModel = assembler.toModel(updatedAirport);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Delete an airport")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Airport deleted",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Airport.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid code supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Airport was not deleted",
					content = @Content) })
	@DeleteMapping("/{code}")
	public void deleteFlight(@PathVariable String code) {
		repository.deleteById(code);
	}
}
