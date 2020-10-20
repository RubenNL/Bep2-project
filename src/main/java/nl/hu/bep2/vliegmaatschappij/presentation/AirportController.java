package nl.hu.bep2.vliegmaatschappij.presentation;

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

	@PostMapping
	ResponseEntity<?> newAirport(@RequestBody Airport airport) {
		EntityModel<Airport> entityModel = assembler.toModel(repository.save(airport));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	@GetMapping("/{code}")
	public EntityModel<Airport> one(@PathVariable String code) throws NotFoundException {
		Airport airport = repository.findById(code)
				.orElseThrow(() -> new NotFoundException("Airport not found"));
		return assembler.toModel(airport);
	}

	@GetMapping("/all")
	public CollectionModel<EntityModel<Airport>> all() {
		List<EntityModel<Airport>> airports = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(airports, linkTo(methodOn(AirportController.class).all()).withSelfRel());
	}
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

	@DeleteMapping("/{code}")
	public void deleteFlight(@PathVariable String code) {
		repository.deleteById(code);
	}
}
