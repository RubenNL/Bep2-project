package nl.hu.bep2.vliegmaatschappij.security.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is a data model.
 *
 * It is similar to a domain model, but is
 * intended for storage purposes. It does not
 * contain a lot of business logic.
 *
 * It implements UserDetails in order to make it usable
 * as login/registration model for Spring.
 */
@Entity
@NoArgsConstructor
@Table(name="users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username;
	private String password;
	private boolean isEmployee = false;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
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

	public void setEmployee(boolean isEmployee) {
		this.isEmployee=isEmployee;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities=new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(isEmployee) authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
		return authorities;
	}
}