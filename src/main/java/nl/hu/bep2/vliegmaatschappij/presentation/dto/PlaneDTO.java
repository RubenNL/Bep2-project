package nl.hu.bep2.vliegmaatschappij.presentation.dto;

import nl.hu.bep2.vliegmaatschappij.domein.Plane;

public class PlaneDTO {
	public String code;
	public PlaneDTO(Plane plane){
		this.code = plane.getCode();
	}
}
