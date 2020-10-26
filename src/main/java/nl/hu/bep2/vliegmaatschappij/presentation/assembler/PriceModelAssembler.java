package nl.hu.bep2.vliegmaatschappij.presentation.assembler;

import nl.hu.bep2.vliegmaatschappij.domein.Price;
import nl.hu.bep2.vliegmaatschappij.presentation.PriceController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PriceModelAssembler implements RepresentationModelAssembler<Price, EntityModel<Price>> {
	@Override
	public EntityModel<Price> toModel(Price price) {
		return EntityModel.of(price,
				linkTo(methodOn(PriceController.class).one(price.getId())).withSelfRel(),
				linkTo(methodOn(PriceController.class).all()).withRel("prices"));
	}
}
