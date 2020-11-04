package nl.hu.bep2.vliegmaatschappij.security.application;

import nl.hu.bep2.vliegmaatschappij.domein.Customer;
import nl.hu.bep2.vliegmaatschappij.domein.Employee;
import nl.hu.bep2.vliegmaatschappij.domein.Person;
import nl.hu.bep2.vliegmaatschappij.exceptions.DuplicateException;
import nl.hu.bep2.vliegmaatschappij.security.data.SpringPersonRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 *  Implements UserDetailsService in order to make it usable
 *  as login/registration service for Spring.
 *  (see AuthenticationFilter)
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
	private final SpringPersonRepository personRepository;
	private final PasswordEncoder passwordEncoder;


	public UserService(SpringPersonRepository repository, PasswordEncoder passwordEncoder) {
		this.personRepository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public void register(String firstName, String lastName, LocalDate birthday, String email, String password, String phone, String nationality) {
		String encodedPassword = this.passwordEncoder.encode(password);
		Customer user = new Customer(firstName, lastName, birthday, email, encodedPassword, phone, nationality);
		if(this.personRepository.findByEmail(email).isEmpty()){
			this.personRepository.save(user);
		}
		else throw new DuplicateException("duplicate e-mail");
	}

	public void delete(int id) {
		this.personRepository.deleteById(id);
	}

	public void delete(String email) {
		this.personRepository.delete(loadUserByUsername(email));
	}

	@Override
	public Person loadUserByUsername(String email) {
		return this.personRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	public void setEmployee(String email) {
		Person person = loadUserByUsername(email);
		delete(email);
		Employee employee = new Employee(person.getFirstName(), person.getLastName(), person.getBirthday(), person.getEmail(), person.getPassword(), person.getPhone(), person.getNationality());
		this.personRepository.save(employee);
	}
}
