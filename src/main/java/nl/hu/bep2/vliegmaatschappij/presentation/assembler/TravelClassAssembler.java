package nl.hu.bep2.vliegmaatschappij.presentation.assembler;

import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import nl.hu.bep2.vliegmaatschappij.presentation.TravelClassController;;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TravelClassAssembler implements RepresentationModelAssembler<TravelClass, EntityModel<TravelClass>> {
	@Override
	public EntityModel<TravelClass> toModel(TravelClass travelClass) {
		return EntityModel.of(travelClass,
				WebMvcLinkBuilder.linkTo(methodOn(TravelClassController.class).one(travelClass.getId())).withSelfRel(),
				linkTo(methodOn(TravelClassController.class).all()).withRel("travelClasses"));
	}
}
