package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassRepository;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.TravelClassAssembler;
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
@RequestMapping("/travelclass")
public class TravelClassController {
	private final SpringTravelClassRepository travelClassRepo;
	private final TravelClassAssembler assembler;

	public TravelClassController(SpringTravelClassRepository travelClassRepo, TravelClassAssembler assembler) {
		this.travelClassRepo = travelClassRepo;
		this.assembler = assembler;
	}

	@PostMapping
	public ResponseEntity<?> newTravelClass(@RequestBody TravelClass travelclass) {
		EntityModel<TravelClass> entityModel = assembler.toModel(travelClassRepo.save(travelclass));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@GetMapping("/{id}")
	public EntityModel<TravelClass> one(@PathVariable int id) {
		TravelClass travelClass = travelClassRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Travel Class not found"));
		return assembler.toModel(travelClass);
	}

	@GetMapping("/all")
	public CollectionModel<EntityModel<TravelClass>> all() {
		List<EntityModel<TravelClass>> travelclasses = travelClassRepo.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(travelclasses, linkTo(methodOn(TravelClassController.class).all()).withSelfRel());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> replaceTravelClass(@RequestBody TravelClass newTravelClass, @PathVariable int id) {
		TravelClass updatedTravelClass = travelClassRepo.findById(id)
				.map(travelclass -> {
					travelclass.setName(newTravelClass.getName());
					travelclass.setMaxSeats(newTravelClass.getMaxSeats());
					travelclass.setAvailableSeats(newTravelClass.getAvailableSeats());
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

	@DeleteMapping("/{id}")
	public void deleteTravelClass(@PathVariable int id) {
		travelClassRepo.deleteById(id);
	}
}
