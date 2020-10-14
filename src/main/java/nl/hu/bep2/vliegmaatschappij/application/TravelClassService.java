package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassRepository;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelClassService {
    private final SpringTravelClassRepository travelClassRepository;

    public TravelClassService(SpringTravelClassRepository travelClassRepository) {
        this.travelClassRepository = travelClassRepository;
    }

    public List<TravelClass> getAll() {
        return travelClassRepository.findAll();
    }
}
