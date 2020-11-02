package nl.hu.bep2.vliegmaatschappij.data;

import nl.hu.bep2.vliegmaatschappij.domein.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringEmployeeController extends JpaRepository<Employee, Integer> {
}
