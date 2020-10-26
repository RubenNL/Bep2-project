package nl.hu.bep2.vliegmaatschappij.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringPriceRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Planetype;
import nl.hu.bep2.vliegmaatschappij.domein.Price;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.PriceModelAssembler;
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
@RequestMapping("/price")
public class PriceController {
	private final SpringPriceRepository priceRepository;
	private final PriceModelAssembler assembler;

	public PriceController(SpringPriceRepository priceRepository, PriceModelAssembler assembler) {
		this.priceRepository=priceRepository;
		this.assembler = assembler;
	}

	@Operation(summary = "Create a new price")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Price created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Price.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid Information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Price couldn't be created",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PostMapping
	public ResponseEntity<?> newPrice(@RequestBody Price price) {
		EntityModel<Price> entityModel = assembler.toModel(priceRepository.save(price));
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Get a Price by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Price found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Price.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Price couldn't be found",
					content = @Content) })
	@GetMapping("/{id}")
	public EntityModel<Price> one(@PathVariable int id) {
		Price price = priceRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Price not found"));
		return assembler.toModel(price);
	}

	@Operation(summary = "Get all Prices")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Price found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Price.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid Information supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Price couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@GetMapping("/all")
	public CollectionModel<EntityModel<Price>> all() {
		List<EntityModel<Price>> prices = priceRepository.findAll().stream()
				.map(assembler::toModel)
				.collect(Collectors.toList());
		return CollectionModel.of(prices, linkTo(methodOn(PriceController.class).all()).withSelfRel());
	}

	@Operation(summary = "Get a Price by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Price replaced",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Price.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Price couldn't be replaced",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@PutMapping("/{id}")
	public ResponseEntity<?> replacePrice(@RequestBody Price newPrice, @PathVariable int id) {
		Price updatedPrice = priceRepository.findById(id)
				.map(price -> {
					price.setPrice(newPrice.getPrice());
					return priceRepository.save(price);
				})
				.orElseGet(() -> {
					newPrice.setId(id);
					return priceRepository.save(newPrice);
				});
		EntityModel<Price> entityModel = assembler.toModel(updatedPrice);
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}

	@Operation(summary = "Delete a Price by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Price found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Price.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid ID supplied",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Price couldn't be found",
					content = @Content) })
	@RolesAllowed("EMPLOYEE")
	@DeleteMapping("/{id}")
	public void deletePrice(@PathVariable int id) {
		priceRepository.deleteById(id);
	}
}
