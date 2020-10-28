package nl.hu.bep2.vliegmaatschappij.presentation.assembler;

import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.presentation.FlightController;
import nl.hu.bep2.vliegmaatschappij.presentation.FlightRouteController;
import nl.hu.bep2.vliegmaatschappij.presentation.PlaneController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FlightModelAssembler implements RepresentationModelAssembler<Flight, EntityModel<Flight>> {
	@Override
	public EntityModel<Flight> toModel(Flight flight) {
		return EntityModel.of(flight,
				linkTo(methodOn(FlightController.class).one(flight.getId())).withSelfRel(),
				linkTo(methodOn(FlightRouteController.class).one(flight.getRoute().getId())).withRel("route"),
				linkTo(methodOn(PlaneController.class).one(flight.getPlane().getCode())).withRel("plane"),
				linkTo(methodOn(FlightController.class).all()).withRel("flights"));
	}
}