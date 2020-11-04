package nl.hu.bep2.vliegmaatschappij.presentation.assembler;

import nl.hu.bep2.vliegmaatschappij.domein.Booking;
import nl.hu.bep2.vliegmaatschappij.presentation.BookingController;
import nl.hu.bep2.vliegmaatschappij.presentation.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookingModelAssembler implements RepresentationModelAssembler<Booking, EntityModel<Booking>> {
    @Override
    public EntityModel<Booking> toModel(Booking booking){
        return EntityModel.of(booking,
                linkTo(methodOn(BookingController.class).one(booking.getId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).one(booking.getCustomer().getId())).withRel("customer"),
                linkTo(methodOn(BookingController.class).all()).withRel("bookings"));
    }
}
