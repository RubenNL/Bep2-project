package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringCustomerRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Customer;
import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.CustomerModelAssembler;
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
@RequestMapping("/Customer")
public class CustomerController {
    private final SpringCustomerRepository repository;
    private final CustomerModelAssembler assembler;

    public CustomerController(SpringCustomerRepository repository, CustomerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(summary = "Get a Customer by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid code supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content)})
    @RolesAllowed("EMPLOYEE")
    @GetMapping("/{id}")
    public EntityModel<Customer> one(@PathVariable int id) throws NotFoundException {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found!"));
        return assembler.toModel(customer);
    }

    @Operation(summary = "Get all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "customers not found",
                    content = @Content) })
    @RolesAllowed("EMPLOYEE")
    @GetMapping("/all")
    public CollectionModel<EntityModel<Customer>> all() throws NotFoundException {
        List<EntityModel<Customer>> customers = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(customers, linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @Operation(summary = "Create a Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer data saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer data can't be submitted",
                    content = @Content) })
    @RolesAllowed("EMPLOYEE")
    @PostMapping("/create")
    public ResponseEntity<?> newCustomer(@RequestBody Customer customer){
        EntityModel<Customer> entityModel = assembler.toModel(repository.save(customer));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "Update a customer")
    @ApiResponses(value ={
            @ApiResponse(responseCode = "200", description = "Customer has been updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid code",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer could not be updated",
                    content = @Content)})
    @RolesAllowed("EMPLOYEE")
    @PutMapping("/{id}")
    ResponseEntity<?> updateCustomer(@RequestBody Customer newCustomer, @PathVariable int id){
        Customer updatedCustomer = repository.findById(id)
                .map(customer -> {
                    customer.setId(newCustomer.getId());
                    customer.setBookings(newCustomer.getBookings());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });
        EntityModel<Customer> entityModel = assembler.toModel(updatedCustomer);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @Operation(summary = "delete customer by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer is deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Plane.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid code",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Failed to delete customer",
                    content = @Content)
    })
    @RolesAllowed("EMPLOYEE")
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable int id){
        repository.deleteById(id);
    }
}
