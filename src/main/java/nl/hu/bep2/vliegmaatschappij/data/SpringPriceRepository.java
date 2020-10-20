package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPriceRepository extends JpaRepository<Price, Integer> {
}