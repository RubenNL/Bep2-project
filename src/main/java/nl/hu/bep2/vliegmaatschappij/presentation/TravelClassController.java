package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Planetype;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.TravelClassAssembler;
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
@RequestMapping("/travelclass")
public class TravelClassController {
	private final SpringTravelClassRepository travelClassRepo;
	private final TravelClassAssembler assembler;

	public TravelClassController(SpringTravelClassRepository travelClassRepo, TravelClassAssembler assembler) {
		this.travelClassRepo = travelClassRepo;
		this.assembler = assembler;
	}

	@Operation(summary = "Create a TravelClass")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "TravelClass created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = TravelClass.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Travelclass couldn't be created",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PostMapping
	public ResponseEntity<?> newTravelClass(@RequestBody TravelClass travelclass) {
		EntityModel<TravelClass> entityModel = assembler.toModel(travelClassRepo.save(travelclass));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Get a TravelClass by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "TravelClass found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = TravelClass.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "TravelClass couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/{id}")
	public EntityModel<TravelClass> one(@PathVariable int id) {
		TravelClass travelClass = travelClassRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Travel Class not found"));
		return assembler.toModel(travelClass);
	}

	@Operation(summary = "Get all TravelClasses")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "TravelClasses found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = TravelClass.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid Information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "TravelClasses couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/all")
	public CollectionModel<EntityModel<TravelClass>> all() {
		List<EntityModel<TravelClass>> travelclasses = travelClassRepo.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(travelclasses, linkTo(methodOn(TravelClassController.class).all()).withSelfRel());
	}

	@Operation(summary = "Replace a TravelClass by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "TravelClass Replaced",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Planetype.class)) }),
			@ApiResponse(responseCode = "400", description = "TravelClass ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "TravelClass couldn't be Replaced",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceTravelClass(@RequestBody TravelClass newTravelClass, @PathVariable int id) {
		TravelClass updatedTravelClass = travelClassRepo.findById(id)
				.map(travelclass -> {
					travelclass.setName(newTravelClass.getName());
					travelclass.setMaxSeats(newTravelClass.getMaxSeats());
					return travelClassRepo.save(travelclass);
				})
				.orElseGet(() -> {
					newTravelClass.setId(id);
					return travelClassRepo.save(newTravelClass);
				});
		EntityModel<TravelClass> entityModel = assembler.toModel(updatedTravelClass);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Delete a TravelClass by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "TravelClass Deleted",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = TravelClass.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "TravelClass couldn't be Deleted",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{id}")
	public void deleteTravelClass(@PathVariable int id) {
		travelClassRepo.deleteById(id);
	}
}
