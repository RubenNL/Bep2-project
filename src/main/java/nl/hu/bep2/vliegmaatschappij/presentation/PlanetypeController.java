package nl.hu.bep2.vliegmaatschappij.presentation;//package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.data.SpringPlanetypeRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Planetype;
import nl.hu.bep2.vliegmaatschappij.exceptions.DuplicateException;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.PlanetypeModelAssembler;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

	@GetMapping("/{id}")
	public EntityModel<Planetype> one(@PathVariable int id) throws NotFoundException {
		 Planetype planetype = repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Plane not found"));
		return assembler.toModel(planetype);
	}

	@GetMapping("/all")
	public CollectionModel<EntityModel<Planetype>> all() throws NotFoundException {
		List<EntityModel<Planetype>> planetypes = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(planetypes, linkTo(methodOn(PlanetypeController.class).all()).withSelfRel());
	}

	@PostMapping("/create")
	public ResponseEntity<?> newPlane(@RequestBody Planetype planetype) throws DuplicateException {
			EntityModel<Planetype> entityModel = assembler.toModel(repository.save(planetype));
			return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
		}

	@PutMapping("/{id}")
	ResponseEntity<?> replaceFlight(@RequestBody Planetype newPlanetype, @PathVariable int id) {
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

	@DeleteMapping("/{id}")
	public void deletePlane(@PathVariable int id){
		repository.deleteById(id);
	}
}
