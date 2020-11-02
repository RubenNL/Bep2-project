package nl.hu.bep2.vliegmaatschappij.application;

import nl.hu.bep2.vliegmaatschappij.data.SpringTravelClassFlightRepository;
import nl.hu.bep2.vliegmaatschappij.domein.Booking;
import nl.hu.bep2.vliegmaatschappij.domein.Flight;
import nl.hu.bep2.vliegmaatschappij.domein.TravelClassFlight;
import nl.hu.bep2.vliegmaatschappij.presentation.DTO.BookingDTO;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
	private SpringTravelClassFlightRepository tcfRepos;
	public BookingService() {
	}

	public Booking createByDTO(BookingDTO bookingDTO){ //todo klant, tcf en personen toevoegen.
		TravelClassFlight tcf = tcfRepos.findByFlightAndClass(bookingDTO.FlightID, bookingDTO.travelClassID).get(0); //Iknow dit kan netter maar boieieeee
		Booking booking = new Booking();
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

		MailService.sendMail(booking.getCustomer().getEmail(), "{V2B Flightservice} Confirmation of booking: " + booking.getId(), confirmationBody);
		return booking;
	}
}
