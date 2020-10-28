package nl.hu.bep2.vliegmaatschappij.presentation.DTO;

public class TravelClassDTO {
	public int max_seats;
	public String name;
	public Double price_per_km;

	@Override
	public String toString() {
		return "TravelClassDTO{" +
				"max_seats=" + max_seats +
				", name='" + name + '\'' +
				", price_per_km=" + price_per_km +
				'}';
	}
}
