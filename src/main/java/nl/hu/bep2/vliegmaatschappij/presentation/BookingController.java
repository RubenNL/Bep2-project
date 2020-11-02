package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.application.BookingService;
import nl.hu.bep2.vliegmaatschappij.data.SpringBookingRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Airport;
import nl.hu.bep2.vliegmaatschappij.domein.Booking;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.BookingModelAssembler;
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
@RequestMapping("/booking")
public class BookingController {
    private final SpringBookingRepository repository;
    private final BookingModelAssembler assembler;
    private final BookingService service;

    public BookingController(SpringBookingRepository repository, BookingModelAssembler assembler, BookingService service){
        this.repository = repository;
        this.assembler = assembler;
		this.service = service;
	}

    @Operation(summary = "Create a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "You have booked a flight!",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Airport.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking was not created",
                    content = @Content) })
    @RolesAllowed("EMPLOYEE")
    @PostMapping
    ResponseEntity<?> newBooking(@RequestBody Booking booking) {
        EntityModel<Booking> entityModel = assembler.toModel(repository.save(booking));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Get a booking by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the booking",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public EntityModel<Booking> one(@PathVariable int id){
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
        return assembler.toModel(booking);
    }

    @RolesAllowed("EMPLOYEE")
    @Operation(summary = "Get all Bookings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the bookings",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found",
                    content = @Content) })
    @GetMapping("/all")
    public CollectionModel<EntityModel<Booking>> all(){
        List<EntityModel<Booking>> bookings = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(bookings, linkTo(methodOn(BookingController.class).all()).withSelfRel());
    }

    @Operation(summary = "Replace a booking by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking replaced",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking couldn't be replaced",
                    content = @Content) })
    @RolesAllowed("EMPLOYEE")
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

    @Operation(summary = "Delete a booking by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking was deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Booking.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking couldn't be deleted",
                    content = @Content) })
    @RolesAllowed("EMPLOYEE")
    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable int id) {
        repository.deleteById(id);
    }

    @PatchMapping("/confirm/{id}")
    public void confirmBooking(@PathVariable int id) {
		Booking booking = repository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));;
    	Booking confirmedBooking = service.confirmBooking(booking);

		EntityModel<Booking> entityModel = assembler.toModel(confirmedBooking);
	}
}
