package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.data.SpringPlaneRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
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

	public PlaneController(SpringPlaneRepository repository, PlaneModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@RolesAllowed("EMPLOYEE")
	@GetMapping("/{code}")
	public EntityModel<Plane> one(@PathVariable String code) throws NotFoundException {
		Plane plane = repository.findById(code)
				.orElseThrow(() -> new NotFoundException("Plane not found"));
		return assembler.toModel(plane);
	}

	@RolesAllowed("EMPLOYEE")
	@GetMapping("/all")
	public CollectionModel<EntityModel<Plane>> all() throws NotFoundException {
		List<EntityModel<Plane>> planes = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(planes, linkTo(methodOn(PlaneController.class).all()).withSelfRel());
	}

	@RolesAllowed("EMPLOYEE")
	@PostMapping("/create") //TODO Why with/without the link?
	public ResponseEntity<?> newPlane(@RequestBody Plane plane){
		EntityModel<Plane> entityModel = assembler.toModel(repository.save(plane));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{code}")
	ResponseEntity<?> replaceFlight(@RequestBody Plane newPlane, @PathVariable String code) {
		Plane updatedPlane = repository.findById(code)
				.map(plane -> {
					plane.setCode(newPlane.getCode());
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

	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{code}")
	public void deletePlane(@PathVariable String code){
		repository.deleteById(code);
	}
}
