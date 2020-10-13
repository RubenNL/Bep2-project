package nl.hu.bep2.vliegmaatschappij.presentation.dto;

import nl.hu.bep2.vliegmaatschappij.domein.Plane;

public class PlaneDTO {
	private String code;

	public PlaneDTO(Plane plane){
		this.code = plane.getCode();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
