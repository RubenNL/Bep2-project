package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassRepository;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClass;
import nl.hu.bep2.vliegmaatschappij.exceptions.FlightNotFoundException;
import nl.hu.bep2.vliegmaatschappij.exceptions.TravelClassNotFoundException;
import nl.hu.bep2.vliegmaatschappij.presentation.dto.TravelClassDTO;
import org.springframework.stereotype.Service;

@Service
public class TravelClassService {
    private final SpringTravelClassRepository travelClassRepo;

    public TravelClassService(SpringTravelClassRepository travelClassRepository) {
        this.travelClassRepo = travelClassRepository;
    }

    public TravelClass getById(int id) {
        TravelClass travelClass = travelClassRepo.findById(id)
                .orElseThrow(() -> new TravelClassNotFoundException("TravelClass with id: " + id + " not found."));
        return travelClass;
    }

    public TravelClass newTravelClass(TravelClassDTO travelClassDTO){
        TravelClass newTravelClass = new TravelClass(travelClassDTO.getName(),travelClassDTO.getMaxSeats(),travelClassDTO.getAvailableSeats());
        TravelClass savedTravelClass = travelClassRepo.findById(travelClassRepo.save(newTravelClass).getId())
                .orElseThrow(() -> new TravelClassNotFoundException("TravelClass not found."));
        return savedTravelClass;
    }

//    public TravelClass updateTravelClass(int id){
//
//    }
}
