package nl.hu.bep2.vliegmaatschappij.domein;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Person implements UserDetails {
	@Id
	@GeneratedValue
	protected int id;
	protected String firstName;
	protected String lastName;
	protected LocalDate birthday;
	protected String email;
	protected String password;
	protected String phone;
	protected String nationality;
	@ManyToMany(mappedBy="persons",cascade= CascadeType.ALL)
	protected List<Booking> bookingList = new ArrayList<>();

	public Person(String firstName, String lastName, LocalDate birthday, String email, String password, String phone, String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.nationality = nationality;
	}

	public Person(String firstName, String lastName, LocalDate birthday, String email, String phone, String nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.nationality = nationality;
	}



	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities=new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(this instanceof Employee) authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
		return authorities;
	}
}
