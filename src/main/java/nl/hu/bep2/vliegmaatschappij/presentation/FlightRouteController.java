package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.data.SpringFlightRouteRepository;
import nl.hu.bep2.vliegmaatschappij.domein.FlightRoute;
import nl.hu.bep2.vliegmaatschappij.exceptions.FlightRouteNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.FlightRouteAssembler;
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
@RequestMapping("/flightroute")
public class FlightRouteController {
	private final SpringFlightRouteRepository flightRouteRepo;
	private final FlightRouteAssembler assembler;

	public FlightRouteController(SpringFlightRouteRepository flightRouteRepo, FlightRouteAssembler assembler) {
		this.flightRouteRepo = flightRouteRepo;
		this.assembler = assembler;
	}

	@RolesAllowed("EMPLOYEE")
	@PostMapping
	public ResponseEntity<?> newFlightRoute(@RequestBody FlightRoute flightRoute) {
		EntityModel<FlightRoute> entityModel = assembler.toModel(flightRouteRepo.save(flightRoute));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@RolesAllowed("EMPLOYEE")
	@GetMapping("/{id}")
	public EntityModel<FlightRoute> one(@PathVariable int id) {
		FlightRoute flightRoute = flightRouteRepo.findById(id)
				.orElseThrow(() -> new FlightRouteNotFoundException("Travel Class not found"));
		return assembler.toModel(flightRoute);
	}

	@RolesAllowed("EMPLOYEE")
	@GetMapping("/all")
	public CollectionModel<EntityModel<FlightRoute>> all() {
		List<EntityModel<FlightRoute>> flightroutes = flightRouteRepo.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(flightroutes, linkTo(methodOn(FlightRouteController.class).all()).withSelfRel());
	}

	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceFlightRoute(@RequestBody FlightRoute newFlightRoute, @PathVariable int id) {
		FlightRoute updatedFlightRoute = flightRouteRepo.findById(id)
				.map(flightroute -> {
					flightroute.setDeparture(newFlightRoute.getDeparture());
					flightroute.setDestination(newFlightRoute.getDestination());
					return flightRouteRepo.save(flightroute);
				})
				.orElseGet(() -> {
					newFlightRoute.setId(id);
					return flightRouteRepo.save(newFlightRoute);
				});
		EntityModel<FlightRoute> entityModel = assembler.toModel(updatedFlightRoute);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{id}")
	public void deleteFlightRoute(@PathVariable int id) {
		flightRouteRepo.deleteById(id);
	}
}
