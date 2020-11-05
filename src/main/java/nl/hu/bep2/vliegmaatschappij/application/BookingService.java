package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.*;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.BookingDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.PersonDTO;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
	private final SpringTravelClassFlightRepository tcfRepos;
	private final MailService mailService;
	public BookingService(SpringTravelClassFlightRepository tcfRepos, MailService mailService) {
		this.tcfRepos = tcfRepos;
		this.mailService = mailService;
	}

	public Booking createByDTO(BookingDTO bookingDTO, Person currentUser){ 
		TravelClassFlight tcf = tcfRepos.getOne(bookingDTO.travelClassFlightID);
		List<Person> persons = new ArrayList<>();
		if(bookingDTO.personDTOS!=null) {
			for (PersonDTO personDTO : bookingDTO.personDTOS) {
				if(personDTO!=null) persons.add(new Person(personDTO.firstName, personDTO.lastName, personDTO.birthday, personDTO.email, personDTO.phone, personDTO.nationality));
			}
		}
		persons.add(currentUser);
		Booking booking = new Booking();
		booking.setTravelClassFlight(tcf);
		booking.setPersons(persons);
		booking.setCustomer((Customer) Hibernate.unproxy(currentUser));
		return booking;
	}

	public Booking confirmBooking(Booking booking) {
		booking.setConfirmed(true);
		mailService.sendConfirmationmail(booking);
		return booking;
	}
}
