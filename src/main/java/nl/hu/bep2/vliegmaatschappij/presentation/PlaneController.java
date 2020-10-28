package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringPlaneRepository;
import nl.hu.bep2.vliegmaatschappij.data.SpringPlanetypeRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.PlaneDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.PlaneModelAssembler;
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
@RequestMapping("/plane")
public class PlaneController {
	private final SpringPlaneRepository repository;
	private final PlaneModelAssembler assembler;
	private final SpringPlanetypeRepository planetypeRepository;

	public PlaneController(SpringPlaneRepository repository, PlaneModelAssembler assembler, SpringPlanetypeRepository planetypeRepository) {
		this.repository = repository;
		this.assembler = assembler;
		this.planetypeRepository = planetypeRepository;
	}

	@Operation(summary = "Get a plane by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plane found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Plane.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid code supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Plane has disappeared",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/{code}")
	public EntityModel<Plane> one(@PathVariable String code) throws NotFoundException {
		Plane plane = repository.findById(code)
				.orElseThrow(() -> new NotFoundException("Plane not found"));
		return assembler.toModel(plane);
	}

	@Operation(summary = "Get all planes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Planes found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Plane.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Planes couldn't be found, how did we lose such big objects?",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/all")
	public CollectionModel<EntityModel<Plane>> all() throws NotFoundException {
		List<EntityModel<Plane>> planes = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(planes, linkTo(methodOn(PlaneController.class).all()).withSelfRel());
	}

	@Operation(summary = "Create a Plane")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plane has been build",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Plane.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Plane couldn't be build",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PostMapping
	public ResponseEntity<?> newPlane(@RequestBody PlaneDTO planeDTO){
		Plane plane=new Plane();
		plane.setCode(planeDTO.code);
		plane.setType(planetypeRepository.getOne(planeDTO.type));
		EntityModel<Plane> entityModel = assembler.toModel(repository.save(plane));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Replace a Plane by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plane has been replaced",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Plane.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid code supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Plane couldn't be replaced",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{code}")
	ResponseEntity<?> replacePlane(@RequestBody PlaneDTO newPlaneDTO, @PathVariable String code) {
		Plane newPlane=new Plane();
		newPlane.setType(planetypeRepository.getOne(newPlaneDTO.type));
		Plane updatedPlane = repository.findById(code)
				.map(plane -> {
					plane.setType(newPlane.getType());
					return repository.save(plane);
				})
				.orElseGet(() -> {
					newPlane.setCode(code);
					return repository.save(newPlane);
				});
		EntityModel<Plane> entityModel = assembler.toModel(updatedPlane);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Delete a plane by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Plane has been destroyed",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Plane.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid code supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Plane couldn't be deleted",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{code}")
	public void deletePlane(@PathVariable String code){
		repository.deleteById(code);
	}
}
