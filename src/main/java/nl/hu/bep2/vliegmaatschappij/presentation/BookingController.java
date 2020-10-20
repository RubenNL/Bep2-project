package nl.hu.bep2.vliegmaatschappij.presentation;

import nl.hu.bep2.vliegmaatschappij.data.SpringBookingRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Booking;
import nl.hu.bep2.vliegmaatschappij.exceptions.BookingNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.BookingModelAssembler;
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
@RequestMapping("/booking")
public class BookingController {
    private final SpringBookingRepository repository;
    private final BookingModelAssembler assembler;

    public BookingController(SpringBookingRepository repository, BookingModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping
    ResponseEntity<?> newBooking(@RequestBody Booking booking) {
        EntityModel<Booking> entityModel = assembler.toModel(repository.save(booking));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PostMapping("/{id}")
    public EntityModel<Booking> one(@PathVariable int id){
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        return assembler.toModel(booking);
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<Booking>> all(){
        List<EntityModel<Booking>> bookings = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookings, linkTo(methodOn(BookingController.class).all()).withSelfRel());
    }

    @PutMapping("/{id}")
    ResponseEntity<?> replaceBooking(@RequestBody Booking newBooking, @PathVariable int id){
        Booking updatedBooking = repository.findById(id)
                .map(booking -> {
                    booking.setPersons(newBooking.getPersons());
                    booking.setPrice(newBooking.getPrice());
                    return repository.save(booking);
                })
                .orElseGet(() -> {
                    newBooking.setId(id);
                    return repository.save(newBooking);
                });
        EntityModel<Booking> entityModel = assembler.toModel(updatedBooking);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable int id) {
        repository.deleteById(id);
    }
}
