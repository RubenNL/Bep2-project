package nl.hu.bep2.vliegmaatschappij.presentation;//package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringPlanetypeRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.Planetype;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.PlanetypeModelAssembler;
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

//TODO Tests maken in postman!
@RestController
@RequestMapping("/planetype")
public class PlanetypeController {
	private final SpringPlanetypeRepository repository;
	private final PlanetypeModelAssembler assembler;

	public PlanetypeController(SpringPlanetypeRepository repository, PlanetypeModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@Operation(summary = "Get a planeType by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "planeType found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Planetype.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "planeType couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/{id}")
	public EntityModel<Planetype> one(@PathVariable int id) throws NotFoundException {
		 Planetype planetype = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Plane not found"));
		return assembler.toModel(planetype);
	}

	@Operation(summary = "get all PlaneTypes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Planetypes found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Planetype.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Planetypes couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/all")
	public CollectionModel<EntityModel<Planetype>> all() throws NotFoundException {
		List<EntityModel<Planetype>> planetypes = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(planetypes, linkTo(methodOn(PlanetypeController.class).all()).withSelfRel());
	}

	@Operation(summary = "Create a Planetype")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Planetype created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Planetype.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Planetype couldn't be created",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PostMapping
	public ResponseEntity<?> newPlane(@RequestBody Planetype planetype){
		EntityModel<Planetype> entityModel = assembler.toModel(repository.save(planetype));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}


	@Operation(summary = "Replace a planetype by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Planetype found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Planetype.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Planetype couldn't be replaced",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{id}")
	ResponseEntity<?> replacePlanetype(@RequestBody Planetype newPlanetype, @PathVariable int id) {
		Planetype updatedPlanetype = repository.findById(id)
				.map(planetype -> {
					planetype.setId(newPlanetype.getId());
					planetype.setName(newPlanetype.getName());
					return repository.save(planetype);
				})
				.orElseGet(() -> {
					newPlanetype.setId(id);
					return repository.save(newPlanetype);
				});
		EntityModel<Planetype> entityModel = assembler.toModel(updatedPlanetype);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Delete a flight by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Planetype deleted",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Planetype.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Planetype has disappeared",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{id}")
	public void deletePlane(@PathVariable int id){
		repository.deleteById(id);
	}
}
