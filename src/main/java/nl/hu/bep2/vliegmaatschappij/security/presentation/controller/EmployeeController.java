package nl.hu.bep2.vliegmaatschappij.security.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import nl.hu.bep2.vliegmaatschappij.data.SpringEmployeeRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Employee;
import nl.hu.bep2.vliegmaatschappij.exceptions.NotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.EmployeeDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.assembler.EmployeeModelAssembler;
import nl.hu.bep2.vliegmaatschappij.security.application.UserService;
import nl.hu.bep2.vliegmaatschappij.security.presentation.dto.SetEmployee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private final UserService userService;
	private final SpringEmployeeRepository repository;
	private final EmployeeModelAssembler assembler;

	public EmployeeController(UserService userService, SpringEmployeeRepository repository, EmployeeModelAssembler assembler) {
		this.userService = userService;
		this.repository = repository;
		this.assembler = assembler;
	}

	@PostMapping("/setEmployee")
	@RolesAllowed("EMPLOYEE")
	public void setEmployee(@Validated @RequestBody SetEmployee setEmployee) {
		userService.setEmployee(setEmployee.username,setEmployee.employee);
	}

		@Operation(summary = "Get an Employee by its id")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Employee found",
						content = {@Content(mediaType = "application/json",
								schema = @Schema(implementation = Employee.class))}),
				@ApiResponse(responseCode = "400", description = "Invalid code supplied",
						content = @Content),
				@ApiResponse(responseCode = "404", description = "Employee not found",
						content = @Content)})
		@RolesAllowed("EMPLOYEE")
		@GetMapping("/{id}")
		public EntityModel<Employee> one(@PathVariable int id) throws NotFoundException {
			Employee employee = repository.findById(id)
					.orElseThrow(() -> new NotFoundException("Employee not found!"));
			return assembler.toModel(employee);
		}

		@Operation(summary = "Get all Employees")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Employee found",
						content = { @Content(mediaType = "application/json",
								schema = @Schema(implementation = Employee.class)) }),
				@ApiResponse(responseCode = "400", description = "Invalid information supplied",
						content = @Content),
				@ApiResponse(responseCode = "404", description = "employees not found",
						content = @Content) })
		@RolesAllowed("EMPLOYEE")
		@GetMapping("/all")
		public CollectionModel<EntityModel<Employee>> all() throws NotFoundException {
			List<EntityModel<Employee>> employees = repository.findAll().stream()
					.map(assembler::toModel)
					.collect(Collectors.toList());
			return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
		}

		@Operation(summary = "Create an Employee")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Employee data saved",
						content = { @Content(mediaType = "application/json",
								schema = @Schema(implementation = Employee.class)) }),
				@ApiResponse(responseCode = "400", description = "Invalid information supplied",
						content = @Content),
				@ApiResponse(responseCode = "404", description = "Employee data can't be submitted",
						content = @Content) })
		@RolesAllowed("EMPLOYEE")
		@PostMapping
		public ResponseEntity<?> newEmployee(@RequestBody EmployeeDTO employeeDTO){
			EntityModel<Employee> entityModel = assembler.toModel(repository.save(new Employee(employeeDTO.firstName,
					employeeDTO.lastName, employeeDTO.birthday, employeeDTO.email, employeeDTO.nationality,employeeDTO.phone )));
			return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
		}

		@Operation(summary = "Update an Employee")
		@ApiResponses(value ={
				@ApiResponse(responseCode = "200", description = "Employee has been updated",
						content = { @Content(mediaType = "application/json",
								schema = @Schema(implementation = Employee.class)) }),
				@ApiResponse(responseCode = "400", description = "Invalid ID",
						content = @Content),
				@ApiResponse(responseCode = "404", description = "Employee could not be updated",
						content = @Content)})
		@RolesAllowed("EMPLOYEE")
		@PutMapping("/{id}")
		ResponseEntity<?> updateEmployee(@RequestBody Employee newEmployee, @PathVariable int id){
			Employee updatedEmployee = repository.findById(id)
					.map(employee -> {
						employee.setId(newEmployee.getId());
						return repository.save(employee);
					})
					.orElseGet(() -> {
						newEmployee.setId(id);
						return repository.save(newEmployee);
					});
			EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
			return ResponseEntity
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
		}

		@Operation(summary = "delete Employee by id")
		@ApiResponses(value = {
				@ApiResponse(responseCode = "200", description = "Employee is deleted",
						content = { @Content(mediaType = "application/json",
								schema = @Schema(implementation = Employee.class)) }),
				@ApiResponse(responseCode = "400", description = "Invalid ID",
						content = @Content),
				@ApiResponse(responseCode = "404", description = "Failed to delete Employee",
						content = @Content)
		})
		@RolesAllowed("EMPLOYEE")
		@DeleteMapping("/{id}")
		public void deleteEmployee(@PathVariable int id){
			repository.deleteById(id);
		}

}