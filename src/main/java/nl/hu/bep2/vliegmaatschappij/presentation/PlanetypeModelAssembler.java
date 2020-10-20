package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import nl.hu.bep2.vliegmaatschappij.domein.Planetype;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlanetypeModelAssembler implements RepresentationModelAssembler<Planetype, EntityModel<Planetype>> {
	@Override
	public EntityModel<Planetype> toModel(Planetype planetype) {
		return EntityModel.of(planetype,
				linkTo(methodOn(PlanetypeController.class).one(planetype.getId())).withSelfRel(),
				linkTo(methodOn(PlanetypeController.class).all()).withRel("planetypes"));
	}
}
