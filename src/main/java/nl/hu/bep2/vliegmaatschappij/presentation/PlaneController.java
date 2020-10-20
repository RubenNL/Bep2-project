package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.data.SpringPlaneRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import nl.hu.bep2.vliegmaatschappij.exceptions.PlaneNotFoundException;
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
@RequestMapping("/plane")
public class PlaneController {
	private final SpringPlaneRepository repository;
	private final PlaneModelAssembler assembler;

	public PlaneController(SpringPlaneRepository repository, PlaneModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/{code}")
	public EntityModel<Plane> one(@PathVariable String code) throws PlaneNotFoundException {
		Plane plane = repository.findById(code)
				.orElseThrow(() -> new PlaneNotFoundException("Plane not found"));
		return assembler.toModel(plane);
	}

	@GetMapping("/all")
	public CollectionModel<EntityModel<Plane>> all() throws PlaneNotFoundException {
		List<EntityModel<Plane>> planes = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(planes, linkTo(methodOn(PlaneController.class).all()).withSelfRel());
	}

	@PostMapping("/create") //TODO Why with/without the link?
	public ResponseEntity<?> newPlane(@RequestBody Plane plane){
		EntityModel<Plane> entityModel = assembler.toModel(repository.save(plane));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

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

	@DeleteMapping("/{code}")
	public void deletePlane(@PathVariable String code){
		repository.deleteById(code);
	}
}
