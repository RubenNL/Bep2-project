package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.exceptions.FlightNotFoundException;
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
@RequestMapping("/flight")
public class FlightController {
	private final SpringFlightRepository repository;
	private final FlightModelAssembler assembler;

	public FlightController(SpringFlightRepository repository, FlightModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}
	@PostMapping
	ResponseEntity<?> newFlight(@RequestBody Flight flight) {
		EntityModel<Flight> entityModel = assembler.toModel(repository.save(flight));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	@GetMapping("/{id}")
	EntityModel<Flight> one(@PathVariable int id) {
		Flight flight = repository.findById(id)
				.orElseThrow(() -> new FlightNotFoundException("flight not found"));
		return assembler.toModel(flight);
	}
	@GetMapping("/all")
	CollectionModel<EntityModel<Flight>> all() {
		List<EntityModel<Flight>> flights = repository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flights, linkTo(methodOn(FlightController.class).all()).withSelfRel());
	}
	@PutMapping("/{id}")
	ResponseEntity<?> replaceFlight(@RequestBody Flight newFlight, @PathVariable int id) {
		Flight updatedFlight = repository.findById(id)
				.map(flight -> {
					flight.setarrivalTime(newFlight.getarrivalTime());
					flight.setdepartureTime(newFlight.getdepartureTime());
					return repository.save(flight);
				})
				.orElseGet(() -> {
					newFlight.setId(id);
					return repository.save(newFlight);
				});
		EntityModel<Flight> entityModel = assembler.toModel(updatedFlight);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@DeleteMapping("/{id}")
	public void deleteFlight(@PathVariable int id) {
		repository.deleteById(id);
	}
}