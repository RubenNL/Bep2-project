package nl.hu.bep2.vliegmaatschappij.security.presentation.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Registration {
	@NotBlank
	public String firstName;
	@NotBlank
	public String lastName;
	@NotNull
	public LocalDate birthday;
	@NotBlank
	@Email
	public String email;
	@NotBlank
	public String phone;
	@NotBlank
	public String nationality;
	@NotBlank
	@Size(min = 5)
	public String password;
}
