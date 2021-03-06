package nl.hu.bep2.vliegmaatschappij.presentation.assembler;

import nl.hu.bep2.vliegmaatschappij.domein.FlightRoute;
import nl.hu.bep2.vliegmaatschappij.presentation.AirportController;
import nl.hu.bep2.vliegmaatschappij.presentation.FlightRouteController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FlightRouteAssembler implements RepresentationModelAssembler<FlightRoute, EntityModel<FlightRoute>> {
	@Override
	public EntityModel<FlightRoute> toModel(FlightRoute flightroute) {
		return EntityModel.of(flightroute,
				WebMvcLinkBuilder.linkTo(methodOn(FlightRouteController.class).one(flightroute.getId())).withSelfRel(),
				linkTo(methodOn(AirportController.class).one(flightroute.getDeparture().getCode())).withRel("departure"),
				linkTo(methodOn(AirportController.class).one(flightroute.getDestination().getCode())).withRel("destination"));
	}
}
