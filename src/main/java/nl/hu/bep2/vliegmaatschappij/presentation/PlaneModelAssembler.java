package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlaneModelAssembler implements RepresentationModelAssembler<Plane, EntityModel<Plane>> {
	@Override
	public EntityModel<Plane> toModel(Plane plane) {
		return EntityModel.of(plane,
				linkTo(methodOn(PlaneController.class).one(plane.getCode())).withSelfRel(),
				linkTo(methodOn(PlaneController.class).all()).withRel("planes"));
	}
}
