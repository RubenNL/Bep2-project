package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringPlaneRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Plane;
import nl.hu.bep2.vliegmaatschappij.exceptions.PlaneNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlaneService {
	private final SpringPlaneRepository planeRepository;

	public PlaneService(SpringPlaneRepository planeRepository) {
		this.planeRepository = planeRepository;
	}

	public Plane showPlane(String planeid) throws PlaneNotFoundException {
		return planeRepository.findById(planeid)
				.orElseThrow(() -> new PlaneNotFoundException("Plane with id: " + planeid + " not found."));
	}

	public Plane createPlane(String code){
		Plane plane = new Plane(code);
		planeRepository.save(plane);
		return plane;
	}
}
