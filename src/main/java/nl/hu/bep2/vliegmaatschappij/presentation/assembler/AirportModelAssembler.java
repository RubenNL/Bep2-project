package nl.hu.bep2.vliegmaatschappij.presentation.assembler;

import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.presentation.AirportController;
import nl.hu.bep2.vliegmaatschappij.presentation.FlightController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AirportModelAssembler implements RepresentationModelAssembler<Airport, EntityModel<Airport>> {
	@Override
	public EntityModel<Airport> toModel(Airport airport) {
		return EntityModel.of(airport,
				linkTo(methodOn(AirportController.class).one(airport.getCode())).withSelfRel(),
				linkTo(methodOn(AirportController.class).all()).withRel("flights"));
	}
}
