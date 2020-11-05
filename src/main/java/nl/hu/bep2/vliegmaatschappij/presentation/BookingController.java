package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.application.BookingService;
import nl.hu.bep2.vliegmaatschappij.application.MailService;
import nl.hu.bep2.vliegmaatschappij.data.SpringBookingRepository;
import nl.hu.bep2.vliegmaatschappij.domein.*;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.BookingDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.BookingModelAssembler;
import nl.hu.bep2.vliegmaatschappij.security.data.SpringPersonRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final SpringPersonRepository personRepository;

    public BookingController(SpringBookingRepository repository, BookingModelAssembler assembler, BookingService service, SpringPersonRepository personRepository){
        this.repository = repository;
        this.assembler = assembler;
		this.service = service;
        this.personRepository = personRepository;
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
    @RolesAllowed("USER")
    @PostMapping
    ResponseEntity<?> newBooking(@RequestBody BookingDTO bookingDTO, @AuthenticationPrincipal Integer id) {
        Person person=personRepository.getOne(id);
    	Booking booking = service.createByDTO(bookingDTO, person);
        Booking savedBooking = repository.save(booking);
        MailService.mailService.sendCreationmail(savedBooking);
    	EntityModel<Booking> entityModel = assembler.toModel(savedBooking);
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
    public EntityModel<Booking> one(@PathVariable String id){
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
    ResponseEntity<?> replaceBooking(@RequestBody Booking newBooking, @PathVariable String id){
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
    public void deleteBooking(@PathVariable String id) {
        repository.deleteById(id);
    }

    @GetMapping("/confirm/{id}")
    public EntityModel<Booking> confirmBooking(@PathVariable String id) {
		Booking booking = repository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));
    	Booking confirmedBooking = service.confirmBooking(booking);
		repository.save(confirmedBooking);
		return assembler.toModel(confirmedBooking);
	}
}
