package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringCustomerRepository extends JpaRepository<Customer, Integer> {
}