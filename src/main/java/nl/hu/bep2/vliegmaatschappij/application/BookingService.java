package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.*;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.BookingDTO;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.PersonDTO;
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
		for(PersonDTO personDTO : bookingDTO.personDTOS){ //if-null exception
			persons.add(new Person(personDTO.firstName, personDTO.lastName, personDTO.birthday, personDTO.email, personDTO.phone, personDTO.nationality));
		}
		persons.add(currentUser);
		Booking booking = new Booking();
		booking.setTravelClassFlight(tcf);
		booking.setPersons(persons);
		booking.setCustomer((Customer) currentUser);
		return booking;
	}

	public Booking confirmBooking(Booking booking) {
		booking.setConfirmed(true);
		Flight flight = booking.getTravelClassFlight().getFlight();
		String confirmationBody = String.format("<h3>Boeking met id %s is zojuist geconfirmt</h3>" +
				"Departure: %s at %s <br> Arrival: %s at %s <br> Have a safe flight! <img src='https://i.ytimg.com/vi/SmHY3xqRmP4/maxresdefault.jpg' width=\"800\" height=\"300\">", booking.getId(),
				flight.getRoute().getDeparture().getName(),
				flight.getDepartureTime(),
				flight.getRoute().getDestination().getName(),
				flight.getArrivalTime());

		mailService.sendMail(booking.getCustomer().getEmail(), "{V2B Flightservice} Confirmation of booking: " + booking.getId(), confirmationBody);
		return booking;
	}
}
