package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassRepository;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import nl.hu.bep2.vliegmaatschappij.exceptions.TravelClassNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TravelClassService {
    private final SpringTravelClassRepository travelClassRepository;

    public TravelClassService(SpringTravelClassRepository travelClassRepository) {
        this.travelClassRepository = travelClassRepository;
    }

    public TravelClass getById(int id) {
        TravelClass travelClass = travelClassRepository.findById(id)
                .orElseThrow(() -> new TravelClassNotFoundException("TravelClass with id: " + id + " not found."));
        return travelClass;
    }
}
