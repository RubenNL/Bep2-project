package nl.hu.bep2.vliegmaatschappij.security.presentation.dto;

import javax.validation.constraints.NotBlank;

public class SetEmployee {
	@NotBlank
	public String email;
}
